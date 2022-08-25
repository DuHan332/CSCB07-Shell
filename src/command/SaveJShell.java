package command;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import driver.Tracker;
import exception.SystemErrorException;

/**
 * This class is implemented from from interface Commands and it is able to save the JShell by
 * making the current JShell's status written to a file in the computer's real file system.
 * 
 * @author Yuanqian Fang
 *
 */
public class SaveJShell implements Commands {
  /**
   * This function would return the manual of this command.
   * 
   * @return a String that describes how to use this command.
   */
  public String getManual() {
    String Manual = "saveJShell FileName: \nWhen the above command is "
        + "typed, the entire state of the program would be\nwritten to the file "
        + "FileName. The file FileName is some file that is stored\non the actual "
        + "filesystem of computer.This command we enable users to save the\nsession"
        + " of the JShell before it is closed down. If file FileName does not\nexist,"
        + " it would create a file save.txt on computer that will save the session "
        + "\nof the JShell. If the above file exists on computer, this file would "
        + "be\noverwritten compleletly";
    return Manual;
  }

  /**
   * This function would save the JShell by making the current Tracker's status written to a file in
   * the computer's real file system.
   * 
   * @param fileName a path represents the location that user wants to put in the file containing
   *        the status of JShell
   * @param tracker a Tracker that track the root directory, current directory and history of
   *        commands that users used
   * @return a String to show save the JShell successfully if saving in a correct way
   * @throws SystemErrorException
   * @exception Exception this exception is thrown if finding some error during saving and
   *            interacting with real file system on the computer
   */
  public String execute(String[] fileName, Tracker tracker) throws SystemErrorException {
    if (fileName.length != 1) {
      throw new SystemErrorException("This command must take one argument");
    }
    try {
      FileOutputStream fileOut = new FileOutputStream(fileName[0]);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(tracker);
      out.close();
      fileOut.close();
      return "JShell has been saved";
    } catch (Exception e) {
      throw new SystemErrorException("The path is not valid");
    }
  }
}
