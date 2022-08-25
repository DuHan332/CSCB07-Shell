package command;

import java.util.ArrayList;
import driver.Tracker;
import exception.SystemErrorException;
import helper.FindPath;
import nodetype.*;

/**
 * This class is implemented from interface Commands and it would search whether the item is in the
 * paths
 * 
 * @author Dezhi Ren
 *
 */

public class Search implements Commands {

  /**
   * The function would first check the validity of input and get the type and name parameter. Then
   * it will call the search for each path
   * 
   * @param parameter the input from user interpreted by driver.InputInterpreter
   * @param tracker a tracker that store the root directory and current directory
   * @return result the search result from the execute
   * @exception SystemErrorException this exception with an error message is thrown if the input is
   *            invalid or the item is not found in the paths
   */
  public String execute(String[] input, Tracker tracker) throws SystemErrorException {
    ArrayList<String> pathList = new ArrayList<String>();
    StringBuilder result = new StringBuilder();
    String[] typeAndName;
    pathList = chechValidityForInput(input);
    typeAndName = getTypeAndName(pathList);
    for (String path : pathList) {
      String newResult = executeOnce(path, tracker, typeAndName);
      if (newResult.endsWith(" is not a valid path")) {
        if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
          result.deleteCharAt(result.length() - 1);
        }
        result.append("\r" + newResult);
        break;
      } else if (newResult.endsWith("\n")) {
        result.append(newResult);
      }
    }
    if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
      result.deleteCharAt(result.length() - 1);
    }
    return result.toString();
  }

  /**
   * The function would call the search for the path
   * 
   * @param parameter the input from user interpreted by driver.InputInterpreter
   * @param tracker a tracker that store the root directory and current directory
   * @param typeAndName a string array with length of two that contains type and name
   * @return result the search result from the executeOnce
   * @exception SystemErrorException this exception with an error message is thrown if the input is
   *            invalid or the item is not found in the paths
   */
  private String executeOnce(String path, Tracker tracker, String[] typeAndName)
      throws SystemErrorException {
    String result = "";
    ArrayList<String> routeList = new ArrayList<String>();
    String[] route = path.split("/");
    for (String r : route)
      routeList.add(r);

    if (route.length == 0) {
      result = searchForWholeRoute(routeList, typeAndName, path, tracker);
    } else if (route[0].equals("")) {
      routeList.remove(0);
      result = searchForWholeRoute(routeList, typeAndName, path, tracker);
    } else
      result = searchForRelativeRoute(routeList, typeAndName, path, tracker);
    return result;
  }

  /**
   * The function would check the validity of input.
   * 
   * @param parameter the input from user interpreted by driver.InputInterpreter
   * @return pathList paths that user types
   * @exception SystemErrorException this exception with an error message is thrown if the input is
   *            invalid
   */
  private static ArrayList<String> chechValidityForInput(String[] input)
      throws SystemErrorException {
    ArrayList<String> pathList = new ArrayList<String>();
    boolean recordType = false;
    int numberOfName = 0, numberOfType = 0;
    for (String i : input) {
      if (i.equals("-type")) {
        recordType = true;
      } else if (i.equals("-name")) {
        recordType = false;
      } else if (recordType) {
        numberOfType++;
      } else if (i.charAt(0) == '\"') {
        numberOfName++;
      }
      pathList.add(i);
    }
    if (numberOfType != 1)
      throw new SystemErrorException(
          "The type parameter is wrong because there is " + numberOfType + " type parameter");
    else if (numberOfName != 1)
      throw new SystemErrorException(
          "The name parameter is wrong because there is " + numberOfName + " name parameter");
    else if (pathList.size() < 5)
      throw new SystemErrorException("Lossing path parameter");
    return pathList;
  }

  /**
   * The function would search the whole path for the item
   * 
   * @param routelist a list of the strings that store the name of every directory in the path from
   *        current directory to target
   * @param path the path given by user
   * @param typeAndName a string array with length of two that contains type and name
   * @param tracker a tracker that store the root directory and current directory
   * @return result the search result from the Search
   */
  private static String searchForWholeRoute(ArrayList<String> routeList, String[] typeAndName,
      String path, Tracker tracker) {
    String result = "", type = "";
    JNode target = null;
    if (routeList.size() == 0) {
      target = tracker.getRootDirectory();
    } else
      target = FindPath.findpath(routeList, tracker.getRootDirectory());
    if (typeAndName[0].equals("d"))
      type = "directory";
    else
      type = "file";

    if (target == null || !target.getIsDirectory()) {
      result = path + " is not a valid path";
    } else {
      Directory d = (Directory) target;
      for (JNode sub : d.getSub())
        if (sub.getName().equals(typeAndName[1])
            && sub.getIsDirectory() == type.equals("directory")) {
          String property = sub.getIsDirectory() ? "directory" : "file";
          result =
              "The " + property + " \"" + typeAndName[1] + "\" is found in the path:" + path + "\n";
          break;
        }
    }
    return result;
  }

  /**
   * The function would search the relative path for the item
   * 
   * @param routelist a list of the strings that store the name of every directory in the path from
   *        current directory to target
   * @param path the path given by user
   * @param typeAndName a string array with length of two that contains type and name
   * @param tracker a tracker that store the root directory and current directory
   * @return result the search result from the Search
   */
  private static String searchForRelativeRoute(ArrayList<String> routeList, String[] typeAndName,
      String path, Tracker tracker) {
    String result = "", type = "";
    JNode target;
    target = FindPath.findpath(routeList, tracker.getCurrentDirectory());
    if (typeAndName[0].equals("d"))
      type = "directory";
    else
      type = "file";
    result = typeAndName[0];

    if (target == null || !target.getIsDirectory()) {
      result = path + " is not a valid path";
    } else {
      Directory d = (Directory) target;
      for (JNode sub : d.getSub())
        if (sub.getName().equals(typeAndName[1])
            && sub.getIsDirectory() == type.equals("directory")) {
          String property = sub.getIsDirectory() ? "directory" : "file";
          result =
              "The " + property + " \"" + typeAndName[1] + "\" is found in the path:" + path + "\n";
          break;
        }
    }
    return result;
  }

  /**
   * The function would get the type and name parameter
   * 
   * @param pathList the input from user interpreted by driver.InputInterpreter
   * @return typeAndName a string array with length of two that contains type and name
   * @exception SystemErrorException this exception with an error message is thrown if the input is
   *            invalid
   */
  private static String[] getTypeAndName(ArrayList<String> pathList) throws SystemErrorException {
    String[] typeAndName = new String[2];
    typeAndName[1] = pathList.remove(0);
    if (typeAndName[1].length() < 2 || typeAndName[1].charAt(0) != '\"'
        || typeAndName[1].charAt(typeAndName[1].length() - 1) != '\"') {
      throw new SystemErrorException("The name parameter is " + typeAndName[1] + " invalid");
    } else
      typeAndName[1] = typeAndName[1].substring(1, typeAndName[1].length() - 1);

    pathList.remove(pathList.size() - 1);
    typeAndName[0] = pathList.remove(pathList.size() - 1);
    if (!typeAndName[0].equals("d") && !typeAndName[0].equals("f"))
      throw new SystemErrorException("The type parameter is " + typeAndName[0] + " invalid");
    pathList.remove(pathList.size() - 1);

    return typeAndName;
  }

  /**
   * The function would return the manual for Search command
   */
  @Override
  public String getManual() {
    return "search PATH ... -type [f|d] -name EXPRESSION:\nThis "
        + "command would search the files or directories (type indicated by [f|d])"
        + "\nwhose names are exactly EXPRESSION in the PATHes, which could be relative";
  }

}
