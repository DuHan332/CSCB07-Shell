package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.Exit;
import driver.Tracker;
import exception.SystemErrorException;

/**
 * For this test I would Test Exit Class and Tracker together since Exit would just edit the
 * variable in the Tracker.
 */
public class ExitTest {
  Tracker tracker;
  Exit exit;

  /**
   * initialize the exit and tracker and set the Swc in tracker to True, which means that the Jshell
   * should be running.
   */
  @Before
  public void setUp() {
    tracker = new Tracker();
    tracker.setSwc(true);
    exit = new Exit();
  }

  /**
   * These tests would test exit without parameter (which is correct).
   */
  @Test
  public void testExit() {
    String[] input0 = {};
    try {
      assertEquals("", exit.execute(input0, tracker));
      assertEquals(false, tracker.getSwc());
    } catch (SystemErrorException e) {
      fail();
    }
  }

  /**
   * These tests would test exit with parameter(s)(incorrect).Each input from input0-1 is a case.
   */
  @Test
  public void testExitWithError() {
    String[] input0 = {"JShell"};
    try {
      assertEquals("", exit.execute(input0, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals(true, tracker.getSwc());
      assertEquals("This command should not have parameter", e.getMessage());
    }

    String[] input1 = {"JShell", "FileSystem"};
    try {
      assertEquals("", exit.execute(input1, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals(true, tracker.getSwc());
      assertEquals("This command should not have parameter", e.getMessage());
    }

  }
}
