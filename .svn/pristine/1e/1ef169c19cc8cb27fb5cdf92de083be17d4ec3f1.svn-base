package helper;

import java.util.ArrayList;
import driver.Tracker;
import exception.SystemErrorException;
import nodetype.Directory;
import nodetype.File;
import nodetype.JNode;

/**
 * This class is able to overwrite the content of specific file or create a new file and add string
 * to the new file.
 * 
 * @author Yuanqian Fang
 *
 */

public class RedirectOverwrite {
  /**
   * This function would first transform the string of path to an ArrayList by calling the function
   * named pathToArrayList and the create a new ArrayList that removes the last element of the
   * previous ArrayList. Then the function would judge whether the path is absolute path or relative
   * path and call the function named redirectOverwrite to perform a series of operations.
   * 
   * @param inputContent a String need to overwrite a file
   * @param path a String contains information of path
   * @param tracker a Tracker that track the root directory and current directory
   * @throws SystemErrorException
   */
  static public void execute(String inputContent, String path, Tracker tracker)
      throws SystemErrorException {
    ArrayList<String> fullPath = pathToArrayList(path);
    ArrayList<String> subPath = new ArrayList<String>();
    for (String s : fullPath) {
      subPath.add(s);
    }
    String fileName = fullPath.get(fullPath.size() - 1);
    subPath.remove(subPath.size() - 1);
    if (path.substring(0, 1).equals("/") == false) {
      redirectOverwrite(inputContent, fileName, fullPath, subPath, tracker.getCurrentDirectory());
    } else {
      redirectOverwrite(inputContent, fileName, fullPath, subPath, tracker.getRootDirectory());
    }
  }

  /**
   * This function would overwrite the content of specific file or create a new file and add string
   * to the new file. If it finds a error, it will throw error message.
   * 
   * @param content a String that user wants to overwrite
   * @param filename a String represents the name of file that user wants to overwrite
   * @param fullPath an ArrayList of String contains full path
   * @param subPath an ArrayList of String contains a path the removes the last one of the full path
   *        a directory that stands for root or current directory
   * @param directory a directory that stands for root or current directory
   * @throws SystemErrorException
   */
  private static void redirectOverwrite(String content, String fileName, ArrayList<String> fullPath,
      ArrayList<String> subPath, Directory directory) throws SystemErrorException {
    JNode full = FindPath.findpath(fullPath, directory);
    JNode sub = FindPath.findpath(subPath, directory);
    if (full != null) {
      if (full.getIsDirectory() == false) {
        File returnOutFile = (File) full;
        returnOutFile.setContent(content);
      } else {
        throw new SystemErrorException("This is not a file");
      }
    } else {
      if (sub != null) {
        if (sub.getIsDirectory() == true) {
          if (IsValidName.isValidName(fileName) == true)
            throw new SystemErrorException("The filename contains invalid character");
          add((Directory) sub, fileName, content);
        } else
          throw new SystemErrorException("Not allowed to create a file in a file");
      } else
        throw new SystemErrorException("The path doesn't exist");
    }
  }

  /**
   * This function transform a String that contains the information of path to an ArrayList of
   * String.
   * 
   * @param path a String that contains the information of path
   * @return an ArrayList of String that stores name of every directory appears in the path
   */
  private static ArrayList<String> pathToArrayList(String path) {
    String[] pathList = path.split("/");
    ArrayList<String> result = new ArrayList<>();
    for (String s : pathList) {
      result.add(s);
    }
    if (result.get(0).equals(""))
      result.remove(0);
    return result;
  }

  /**
   * This function would add a file to its root directory's sub.
   * 
   * @param toAdd a directory needs to be added a new file under it
   * @param filename a String that represents the name of new file
   * @param content a String that the new file should contain
   */
  private static void add(Directory toAdd, String fileName, String content) {
    File file = new File(content, toAdd, fileName);
    toAdd.getSub().add(file);
  }

}
