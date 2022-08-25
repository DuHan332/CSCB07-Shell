package driver;

import command.*;
import exception.*;
import helper.ModifyInput;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class interprets user's input and call the corresponding command. This class would call the
 * GiveOutput the command returns a string to be printed or catch exception from the command.
 * 
 * @author Dezhi Ren
 *
 */

public class InputInterpreter {
  public Tracker tracker;
  private String commandName;
  private GiveOutput outputfile = new GiveOutput();
  private HashMap<String, String> commandHashMap = new HashMap<String, String>();

  /**
   * The function would first separate user's input and check which command user calls. Next the
   * function would send the input to the corresponding command. Then the function would call the
   * GiveOutput for that command or print error message.
   * 
   * @param input the input from user
   * @exception SystemErrorException this exception with an error message is thrown if the input is
   *            invalid
   * @exception InstantiationException this exception is thrown if command is not in the HashMap
   * @exception IllegalAccessException this exception is thrown if command is not in the HashMap
   * @exception ClassNotFoundException this exception is thrown if command is not in the HashMap
   */
  public void interprete(String input) {
    tracker.getHistory().add(input);
    input = input.replace("\t", " ").trim();
    try {
      String[] inputlist = ModifyInput.modifyInput(input);
      setCommandName(inputlist[0]);
      String[] redirection = determineRedirection(inputlist);
      String[] parameter = redirection == null ? new String[inputlist.length - 1]
          : new String[inputlist.length - redirection.length - 1];
      if (inputlist.length != 1)
        System.arraycopy(inputlist, 1, parameter, 0, parameter.length);
      if (this.commandName.equals("loadJShell")) {
        setTracker(command.LoadJShell.execute(parameter, tracker));
        System.out.println("The JShell Status is successfully loaded");
      } else if (commandHashMap.containsKey(this.commandName)) {
        Commands command =
            (Commands) Class.forName(commandHashMap.get(this.commandName)).newInstance();
        if (redirection == null) {
          outputfile.printOutput(command.execute(parameter, tracker));
        } else
          outputfile.redirectOutput(command.execute(parameter, tracker), redirection[1],
              redirection[0].equals(">>"), tracker);
      } else
        throw new SystemErrorException(
            "Cannot recognize \"" + this.commandName + "\" as a valid command");
    } catch (SystemErrorException e) {
      System.err.println(e.getMessage());
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.getStackTrace();
    }
  }

  /**
   * The function would initialize the command HashMap
   */
  public void initializeCommandHashMap() {
    this.commandHashMap.put("cd", "command.ChangeDirectory");
    this.commandHashMap.put("exit", "command.Exit");
    this.commandHashMap.put("ls", "command.List");
    this.commandHashMap.put("mkdir", "command.MakeDirectory");
    this.commandHashMap.put("pwd", "command.PrintWorkingDirectory");
    this.commandHashMap.put("history", "command.History");
    this.commandHashMap.put("pushd", "command.PushDirectory");
    this.commandHashMap.put("popd", "command.PopDirectory");
    this.commandHashMap.put("cat", "command.Concatenate");
    this.commandHashMap.put("echo", "command.Echo");
    this.commandHashMap.put("man", "command.Manual");
    this.commandHashMap.put("search", "command.Search");
    this.commandHashMap.put("rm", "command.RemoveDirectory");
    this.commandHashMap.put("cp", "command.CopyItem");
    this.commandHashMap.put("mv", "command.MoveItem");
    this.commandHashMap.put("curl", "command.CopyURL");
    this.commandHashMap.put("saveJShell", "command.SaveJShell");
    this.commandHashMap.put("loadJShell", "command.LoadJShell");
    this.commandHashMap.put("tree", "command.Tree");

  }

  /**
   * The function would check whether user uses redirection. Then it would return what kind of
   * redirection is and the path of the redirection.
   * 
   * @param inputlist the input modified by ModifyInput method;
   * @exception SystemErrorException this exception with an error message is thrown if user use
   *            redirection incorrectly
   */

  private static String[] determineRedirection(String[] inputlist) throws SystemErrorException {
    String[] redirection = new String[2];
    if (inputlist.length < 3) {
      return null;
    } else {
      boolean beginRedirecting = false;
      int numberOfRedirection = 0;
      for (int i = 0; i < inputlist.length; i++) {
        if (beginRedirecting) {
          numberOfRedirection++;
          if (numberOfRedirection > 2)
            throw new SystemErrorException("There are more than one redirection file.");
          redirection[1] = inputlist[i];
        } else if (inputlist[i].startsWith(">")) {
          if (inputlist[i].length() > 2
              || !inputlist[i].equals(">>") && !inputlist[i].equals(">")) {
            throw new SystemErrorException(
                "The redirection \"" + inputlist[i] + "\" mark is wrong");
          } else {
            beginRedirecting = true;
            redirection[0] = inputlist[i];
            numberOfRedirection++;
          }
        }
      }
      if (numberOfRedirection == 1)
        throw new SystemErrorException("Lossing redirection file.");
    }

    if (redirection[0] == null || redirection[1] == null)
      return null;
    return redirection;
  }

  /**
   * The setter function for tracker
   */
  private void setTracker(Tracker tracker) {
    this.tracker = tracker;
  }

  /**
   * The getter function for commandName
   * 
   * @return CommandName the name of the command
   */
  public String getCommandName() {
    return commandName;
  }

  /**
   * The setter function for commandName
   */
  public void setCommandName(String commandName) {
    this.commandName = commandName;
  }

}
