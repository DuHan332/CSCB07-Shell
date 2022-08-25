package helper;

import exception.SystemErrorException;
import java.util.ArrayList;

/**
 * This class modify user's input into valid parameter that can be recognized for each command.
 * 
 * @author Dezhi Ren
 *
 */

public class ModifyInput {

  /**
   * The function would first count the number of quotation mark in the input and report error if
   * the input is invalid. Then the function would change the input into a list of string and return
   * the list.
   * 
   * @param input the input from user
   * @return inputList the list of string that can be recognized as valid parameter for each command
   * @exception SystemErrorException this exception with an error message is thrown if the input is
   *            invalid
   */
  public static String[] modifyInput(String input) throws SystemErrorException {
    ArrayList<String> listOfInput;
    int numberOfQuotationMark = countQuotationMark(input);
    String[] inputList;

    if (numberOfQuotationMark == 1 || numberOfQuotationMark > 2) {
      throw new SystemErrorException(
          "The input is invalid because there is " + numberOfQuotationMark + " quotation mark.");
    } else if (numberOfQuotationMark == 0) {
      inputList = input.split(" +");
      return inputList;
    } else {
      listOfInput = getArrayListForInput(input);
    }
    inputList = listOfInput.toArray(new String[listOfInput.size()]);
    return inputList;
  }

  /**
   * The function would count the number of quotation mark in the input.
   * 
   * @param input the input from user
   * @return numberOfQuotationMark the number of quotation mark in the input.
   */
  private static int countQuotationMark(String input) {
    int numberOfQuotationMark = 0;
    for (int i = 0; i < input.length(); i++) {
      if (input.substring(i, i + 1).equals("\"")) {
        numberOfQuotationMark++;
      }
    }

    return numberOfQuotationMark;
  }

  /**
   * Then the function would change the input that has two quotation marks into an ArrayList of
   * string. The quotation string would be preserved.
   * 
   * @param input the input from user
   * @return listOfInput the ArrayList that contain the command statement and the quotation from
   *         user.
   */
  private static ArrayList<String> getArrayListForInput(String input) {
    ArrayList<String> listOfInput = new ArrayList<String>();
    String str = "";
    String commandStatement = "";
    boolean startQuotation = false;
    for (int i = 0; i < input.length(); i++) {
      if (startQuotation && input.substring(i, i + 1).equals("\"")) {
        commandStatement += " ";
        str += input.substring(i, i + 1);
        startQuotation = false;
      } else if (!startQuotation && input.substring(i, i + 1).equals("\"")) {
        commandStatement += " ";
        str += input.substring(i, i + 1);
        startQuotation = true;
      } else if (startQuotation) {
        commandStatement += " ";
        str += input.substring(i, i + 1);
      } else
        commandStatement += input.substring(i, i + 1);
    }
    String[] commandParameter = commandStatement.split(" +");
    listOfInput.add(commandParameter[0]);
    listOfInput.add(str);
    if (commandParameter.length > 1)
      for (int i = 1; i < commandParameter.length; i++)
        listOfInput.add(commandParameter[i]);

    return listOfInput;
  }
}
