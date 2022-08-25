package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.Manual;
import driver.Tracker;
import exception.SystemErrorException;

/**
 * These tests would test if Manual would return String which is the manual of a existed command.
 * The tracker would not be used in Manual class so tracker here does not contain any contents.
 */
public class ManualTest {
  Manual manual;
  Tracker tracker;

  /**
   * initialize the tracker and manual object
   */
  @Before
  public void setUp() {
    tracker = new Tracker();
    manual = new Manual();
  }

  /**
   * These tests would test with one correct parameter.Each input from input0-1 is a case.
   */
  @Test
  public void testManual() {

    String[] input0 = {"man"};
    String expected0 = "man CMD [CMD2 бн]:\nPrint documentation for CMD(s)";
    try {
      assertEquals(expected0, manual.execute(input0, tracker));
    } catch (SystemErrorException e) {
      fail();
    }

    String[] input1 = {"history"};
    String expected1 = "history [n]:\nThis command will print out "
        + "recent commands, one command per line.We can\ntruncate the "
        + "output by specifying a number n after the command to print\nthe "
        + "last n commands typed";
    try {
      assertEquals(expected1, manual.execute(input1, tracker));
    } catch (SystemErrorException e) {
      fail();
    }
  }

  /**
   * These tests would test several cases for the man command: no parameter, more than one
   * parameters and incorrect parameter(the manual does not exist). Which are all invalid inputs.
   * Each input from input0-4 is a case.
   */
  @Test
  public void testManualWithError() {

    String[] input0 = {};
    try {
      assertEquals("", manual.execute(input0, tracker));
    } catch (SystemErrorException e) {
      assertEquals("must has at least one parameter", e.getMessage());
    }


    String[] input1 = {"man", "history"};
    try {
      assertEquals("", manual.execute(input1, tracker));
    } catch (SystemErrorException e) {
      assertEquals("too many parameters", e.getMessage());
    }

    String[] input2 = {"manDNE"};
    try {
      assertEquals("", manual.execute(input2, tracker));
    } catch (SystemErrorException e) {
      assertEquals("manDNE does not exist", e.getMessage());
    }

  }
}
