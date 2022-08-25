package helper;

import java.util.ArrayList;

/**
 * The class contains a helper function which is able to check the whether a string contains invalid
 * character
 * 
 * @author Yuanqian Fang
 *
 */
public class IsValidName {
  /**
   * This function would check the whether a string contains invalid character.
   * 
   * @param name a String that used to name a directory
   * @return true or false to represent whether the string contains invalid character
   */
  static public boolean isValidName(String name) {
    boolean containInvalid = false;
    ArrayList<String> charTable = new ArrayList<String>();
    charTable.add(" ");
    charTable.add("/");
    charTable.add("!");
    charTable.add("@");
    charTable.add("#");
    charTable.add("$");
    charTable.add("%");
    charTable.add("^");
    charTable.add("&");
    charTable.add("*");
    charTable.add("(");
    charTable.add(")");
    charTable.add("{");
    charTable.add("}");
    charTable.add("~");
    charTable.add("|");
    charTable.add("<");
    charTable.add(">");
    charTable.add("?");
    for (String s : charTable) {
      if (name.contains(s))
        containInvalid = true;
    }
    return containInvalid;
  }
}
