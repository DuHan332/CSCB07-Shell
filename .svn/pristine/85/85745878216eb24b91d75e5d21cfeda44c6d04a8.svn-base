package command;

import driver.Tracker;
import exception.SystemErrorException;
import nodetype.JNode;
import java.net.*;
import java.io.*;
import driver.GiveOutput;

/**
 * This class is implemented from interface Commands and it would download the file from the url and
 * save it in the current directory
 * 
 * @author Dezhi Ren
 *
 */

public class CopyURL implements Commands {

  /**
   * The function would first check input and throw error if the input is invalid. Then the function
   * would connect to the URL and copy the content from the file with redirection.
   * 
   * @param parameter the input from user interpreted by driver.InputInterpreter
   * @param tracker a tracker that store the root directory and current directory
   * @return an empty string if the path is changed successfully
   * @exception SystemErrorException this exception with an error message is thrown if the input is
   *            invalid
   * @exception MalformedURLException this exception with an error message is thrown if the URL is
   *            not correct
   * @exception IOException this exception with an error message is thrown if the function meet an
   *            connection error
   */
  @Override
  public String execute(String[] input, Tracker tracker) throws SystemErrorException {
    if (input.length > 1 || input.length == 0) {
      throw new SystemErrorException(
          "The number of input is wrong because there is " + input.length + " input");
    } else if (input[0].startsWith("\"") || input[0].endsWith("/")) {
      throw new SystemErrorException(input[0] + " is not a valid URL");
    }
    try {
      GiveOutput downloadedFile = new GiveOutput();
      URL inputURL = new URL(input[0]);
      URLConnection connection = inputURL.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputFileLine;
      int startOfFileName = input[0].lastIndexOf("/");
      String fileName = input[0].substring(startOfFileName + 1, input[0].length());
      for (JNode i : tracker.getCurrentDirectory().getSub())
        if (i.getName().equals(fileName) && !i.getIsDirectory())
          throw new SystemErrorException(
              "There is already a file named: " + fileName + " in the current directory");
      while ((inputFileLine = in.readLine()) != null) {
        downloadedFile.redirectOutput(inputFileLine + "\n", fileName, true, tracker);
      }
      in.close();
    } catch (MalformedURLException e) {
      throw new SystemErrorException(input[0] + " is not a valid URL");
    } catch (IOException e) {
      throw new SystemErrorException("Cannot connect to " + input[0]);
    }
    return "";
  }

  /**
   * The function would return the manual for CopyURL command
   */
  @Override
  public String getManual() {
    return "curl URL:\nURL is a web address. Retrieve the file at that URL"
        + "and add it to the current\nworking directory.";
  }

}
