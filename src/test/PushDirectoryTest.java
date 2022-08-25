package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.PushDirectory;
import driver.Tracker;
import exception.SystemErrorException;
import nodetype.*;

public class PushDirectoryTest {

  private PushDirectory pushd;
  private Directory root;
  private Tracker tracker;

  @Before
  public void setUp() {

    pushd = new PushDirectory();
    root = new Directory(null, "/");
    Directory directoryA = new Directory(root, "FolderA");
    root.getSub().add(directoryA);

    File fileA = new File("I am A", directoryA, "FileA");
    directoryA.getSub().add(fileA);

    Directory directoryB = new Directory(root, "FolderB");
    root.getSub().add(directoryB);

    tracker = new Tracker();
    tracker.setCurrentDirectory(directoryA);
    tracker.setRootDirectory(root);
  }

  /**
   * It would test if the file system is correct
   */
  @Test
  public void testFileSystem() {
    assertEquals(root.getName(), "/");
    assertEquals(root.getSub().get(0).getName(), "FolderA");
    assertEquals(root.getSub().get(1).getName(), "FolderB");
    assertEquals(((Directory) root.getSub().get(0)).getSub().get(0).getName(), "FileA");
  }

  /**
   * It would test if the tracker is correct
   */
  @Test
  public void testTracker() {
    assertEquals(tracker.getRootDirectory().getName(), "/");
    assertEquals(tracker.getCurrentDirectory().getName(), "FolderA");
    assertTrue(tracker.getStack().size() == 0);
  }

  /**
   * It would test whether the PushDirectory return the exception as wanted for no path
   */
  @Test
  public void testPushDirectoryForNoParameter() {
    String[] parameter = new String[0];
    try {
      pushd.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "Losing the target directory");
    }
  }

  /**
   * It would test whether the PushDirectory return the exception as wanted for more than one
   */
  @Test
  public void testPushDirectoryForMoreThanOneParameter() {
    String[] parameter = new String[2];
    try {
      pushd.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "There is more than one parameter");
    }
  }

  /**
   * It would test whether the PushDirectory return the exception as wanted for wrong directory path
   */
  @Test
  public void testPushDirectoryForWrongDirectory() {
    String[] parameter = new String[1];
    parameter[0] = "/A/B";
    try {
      pushd.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "Cannot find the path \"/A/B\".");
      assertTrue(tracker.getStack().size() == 0);
    }
  }

  /**
   * It would test whether the PushDirectory return the exception as wanted for file path
   */
  @Test
  public void testPushDirectoryForWrongFilePath() {
    String[] parameter = new String[1];
    parameter[0] = "/A/B";
    try {
      pushd.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "Cannot find the path \"/A/B\".");
      assertTrue(tracker.getStack().size() == 0);
    }
  }

  /**
   * It would test if the PushDirectory pushes the directory and change it to new path correctly
   */
  @Test
  public void testPushDirectoryWithCorrectPath() throws SystemErrorException {
    String[] parameter = new String[1];
    parameter[0] = "/FolderB";
    pushd.execute(parameter, tracker);
    assertTrue(tracker.getStack().size() == 1);
    assertEquals(tracker.getStack().get(0).getName(), "FolderA");
    assertEquals(tracker.getCurrentDirectory().getName(), "FolderB");
  }

  /**
   * It would test if the PopDirectory return the manual of itself
   */
  @Test
  public void testManual() {
    assertEquals(pushd.getManual(),
        "pushd DIR:\n" + "Saves the current working "
            + "directory by pushing onto directory stack and then\nchanges the "
            + "new current working directory to " + "DIR.");
  }

}
