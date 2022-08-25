package test;

import static org.junit.Assert.*;
import org.junit.Test;
import helper.IsValidName;

/**
 * This class is used for testing the helper function named IsValidName.
 * 
 * @author Yuanqian Fang
 *
 */
public class IsValidNameTest {
  /**
   * It would test whether the helper function could return the correct result when a string
   * contains invalid characters.
   */
  @Test
  public void testInvalidCharacter() {
    assertEquals(IsValidName.isValidName("Name"), false);
    assertEquals(IsValidName.isValidName("Name123"), false);
    assertEquals(IsValidName.isValidName("Na me"), true);
    assertEquals(IsValidName.isValidName("Na/me"), true);
    assertEquals(IsValidName.isValidName("Name!"), true);
    assertEquals(IsValidName.isValidName("Name@"), true);
    assertEquals(IsValidName.isValidName("#Name"), true);
    assertEquals(IsValidName.isValidName("$Name"), true);
    assertEquals(IsValidName.isValidName("Name%"), true);
    assertEquals(IsValidName.isValidName("Name^"), true);
    assertEquals(IsValidName.isValidName("Name&"), true);
    assertEquals(IsValidName.isValidName("Name*"), true);
    assertEquals(IsValidName.isValidName("Name("), true);
    assertEquals(IsValidName.isValidName("Name)"), true);
    assertEquals(IsValidName.isValidName("Name{"), true);
    assertEquals(IsValidName.isValidName("Name}"), true);
    assertEquals(IsValidName.isValidName("Na~me"), true);
    assertEquals(IsValidName.isValidName("Na|me"), true);
    assertEquals(IsValidName.isValidName("<Name"), true);
    assertEquals(IsValidName.isValidName(">Name"), true);
    assertEquals(IsValidName.isValidName("Name?"), true);
    assertEquals(IsValidName.isValidName("?N%a(m!e*"), true);
    assertEquals(IsValidName.isValidName("*"), true);
  }
}
