package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.History;
import driver.Tracker;
import exception.SystemErrorException;

/**
 * These tests would test if History command work properly, tracker class is used since it would get
 * history data from it
 */
public class HistoryTest {
  Tracker tracker;
  History history;

  /**
   * it would initialize the tracker object with several history datas and history object
   */
  @Before
  public void setUp() {
    history = new History();
    tracker = new Tracker();
    tracker.getHistory().add("history1");
    tracker.getHistory().add("history2");
    tracker.getHistory().add("history3");
    tracker.getHistory().add("history4");
    tracker.getHistory().add("history5");
  }

  /**
   * These tests would test the history command in several cases: no parameter, one correct
   * parameter. Each input from input0-1 is a case.
   */
  @Test
  public void testHistory() {
    String[] input0 = {};
    String expected0 =
        "1. history1\n" + "2. history2\n" + "3. history3\n" + "4. history4\n" + "5. history5";
    try {
      assertEquals(expected0, history.execute(input0, tracker));
    } catch (SystemErrorException e) {
      fail();
    }

    String[] input1 = {"3"};
    String expected1 = "3. history3\n" + "4. history4\n" + "5. history5";
    try {
      assertEquals(expected1, history.execute(input1, tracker));
    } catch (SystemErrorException e) {
      fail();
    }
  }

  /**
   * These tests would test the history command in several cases: more than one parameter, one
   * incorrect parameter(not a number, too big and parameter is zero). Which are all invalid inputs.
   * Each input from input0-3 is a case.
   */
  @Test
  public void testHistoryWithError() {
    String[] input0 = {"3", "4"};
    try {
      assertEquals("", history.execute(input0, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals("too many parameters", e.getMessage());
    }

    String[] input1 = {"all"};
    try {
      assertEquals("", history.execute(input1, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals("parameter should be a number", e.getMessage());
    }

    String[] input2 = {"99999999"};
    try {
      assertEquals("", history.execute(input2, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals("parameter is too big", e.getMessage());
    }

    String[] input3 = {"0"};
    try {
      assertEquals("", history.execute(input3, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals("the parameter should be bigger than 0", e.getMessage());
    }
  }
}
