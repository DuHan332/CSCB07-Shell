package command;

import java.util.ArrayList;
import driver.Tracker;
import exception.SystemErrorException;
import helper.FindPath;
import nodetype.*;

/**
 * This class is implemented from interface Commands and it would move the item from old path into
 * new path
 * 
 * @author Dezhi Ren
 *
 */

public class MoveItem implements Commands {

  /**
   * The function would first check input and throw error if the input is invalid. Then the function
   * would call the CopyItem to copy the item. Next the function would call the Remove to remove the
   * original item
   * 
   * @param parameter the input from user interpreted by driver.InputInterpreter
   * @param tracker a tracker that store the root directory and current directory
   * @return an empty string if the item is copied successfully
   * @exception SystemErrorException this exception with an error message is thrown if the input is
   *            invalid
   */
  @Override
  public String execute(String[] input, Tracker tracker) throws SystemErrorException {
    CopyItem cp = new CopyItem();
    cp.execute(input, tracker);

    String[] oldPath = new String[1];
    oldPath[0] = input[0];

    if (cp.isCopyDirectory()) {
      RemoveDirectory rm = new RemoveDirectory();
      return rm.execute(oldPath, tracker);
    } else {
      removeFile(oldPath, tracker);
    }
    return "";
  }

  /**
   * The function would move the file into the new path
   * 
   * @param oldPath the item in the old path
   * @param tracker a tracker that store the root directory and current directory
   */
  private static void removeFile(String[] oldPath, Tracker tracker) {
    JNode target;
    Directory root;
    String[] oldPathList = oldPath[0].split("/");
    ArrayList<String> oldPathArrayList = new ArrayList<String>();
    for (String i : oldPathList)
      oldPathArrayList.add(i);
    if (oldPathList[0].equals("")) {
      oldPathArrayList.remove(0);
      target = FindPath.findpath(oldPathArrayList, tracker.getRootDirectory());
    } else {
      target = FindPath.findpath(oldPathArrayList, tracker.getCurrentDirectory());
    }
    root = target.getRoot();
    for (int i = 0; i < root.getSub().size(); i++)
      if (root.getSub().get(i).getName().equals(target.getName())) {
        root.getSub().remove(i);
        break;
      }
  }

  /**
   * The function would return the manual for MoveItem command
   */
  @Override
  public String getManual() {
    return "mv OLDPATH NEWPATH:\nMove item OLDPATH to NEWPATH. Both OLD-"
        + "PATH and NEWPATH may be relative to\nthe current directory or may be "
        + "fullpaths. If NEWPATH is a directory, move\nthe item into the directory.";
  }

}
