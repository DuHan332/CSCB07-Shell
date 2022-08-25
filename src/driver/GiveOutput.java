package driver;

import exception.SystemErrorException;
import helper.RedirectAppend;
import helper.RedirectOverwrite;

/**
 * This class would format the output. Then it would print the output or call the redirectOverwrite
 * or redirectAppend if users use redirection.
 * 
 * @author Dezhi Ren
 *
 */

public class GiveOutput {
  /**
   * The function would format the output and print it.
   * 
   * @param content the returned string from the command.
   */
  public void printOutput(String content) {
    if (content.equals("")) {
      return;
    }
    String[] outputList = content.split("\r");
    if (!outputList[0].equals(""))
      System.out.println(outputList[0]);
    if (outputList.length == 2) {
      System.err.println(outputList[1]);
    }
  }

  /**
   * The function would format the output and call redirectOverwrite or redirectAppend based on
   * user's input.
   * 
   * @param content the returned string from the command.
   * @param path the path that used for redirection
   * @param isAppend the boolean that decide whether the redirection is append or overwrite
   * @param tracker the tracker that store the root directory and current directory
   */
  public void redirectOutput(String content, String path, boolean isAppend, Tracker tracker)
      throws SystemErrorException {
    if (content.equals("")) {
      return;
    }
    String[] outputList = content.split("\r");
    if (outputList.length > 1) {
      System.err.println(outputList[1]);
    }
    if (isAppend && !outputList[0].equals("")) {
      RedirectAppend.execute(outputList[0], path, tracker);
    } else if (!isAppend && !outputList[0].equals("")) {
      RedirectOverwrite.execute(outputList[0], path, tracker);
    }
  }

}
