package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.LoadJShell;
import command.SaveJShell;
import driver.Tracker;
import exception.SystemErrorException;
import nodetype.Directory;
import nodetype.File;

/**
 * This class is used for testing the class of SaveJShell, it will test whether the command can save
 * the JShell by making the current JShell's status written to a file in the computer's real file
 * system. And it will also test whether the command could throw the correct exception.
 * 
 * @author Yuanqian Fang
 *
 */
public class SaveJShellTest {
  private Tracker tracker;
  private Directory root;
  private LoadJShell loadJShell;
  private SaveJShell saveJShell;

  /**
   * It would initialize the file system used for testing.
   */

  @Before
  public void setUp() {
    loadJShell = new LoadJShell();
    saveJShell = new SaveJShell();
    root = new Directory(null, "/");
    Directory directoryA = new Directory(root, "FolderA");
    root.getSub().add(directoryA);

    File fileA = new File("I am A", directoryA, "FileA");
    directoryA.getSub().add(fileA);

    Directory directoryB = new Directory(root, "FolderB");
    root.getSub().add(directoryB);

    Directory directoryC = new Directory(directoryB, "FolderC");
    directoryB.getSub().add(directoryC);

    tracker = new Tracker();
    tracker.setCurrentDirectory(root);
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
    assertEquals(((Directory) root.getSub().get(1)).getSub().get(0).getName(), "FolderC");
  }

  /**
   * It would test if the tracker is correct
   */
  @Test
  public void testTracker() {
    assertEquals(tracker.getRootDirectory().getName(), "/");
    assertEquals(tracker.getCurrentDirectory().getName(), "/");
    assertEquals(tracker.getRootDirectory().getSub().get(0).getName(), "FolderA");
    assertEquals(tracker.getRootDirectory().getSub().get(1).getName(), "FolderB");
  }

  /**
   * It would test the getManual method.
   */
  @Test
  public void testGetManual() {
    assertEquals(saveJShell.getManual(),
        "saveJShell FileName: \nWhen the above command is "
            + "typed, the entire state of the program would be\nwritten to the file "
            + "FileName. The file FileName is some file that is stored\non the actual "
            + "filesystem of computer.This command we enable users to save the\nsession"
            + " of the JShell before it is closed down. If file FileName does not\nexist,"
            + " it would create a file save.txt on computer that will save the session "
            + "\nof the JShell. If the above file exists on computer, this file would "
            + "be\noverwritten compleletly");
  }


  /**
   * It would test whether the command throws correct exception if the input is empty.
   */
  @Test
  public void testInputIsEmpty() {
    try {
      String[] input = {};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "This command must take one argument");
    }
  }

  /**
   * It would test whether the command throws correct exception if the input is more than one.
   */
  @Test
  public void testInputIsMoreThanOne() {
    try {
      String[] input = {"D:\\save.txt", "D:\\saveJShell.txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "This command must take one argument");
    }
  }

  /**
   * It would test whether the command throws correct exception if the users wants to save to a
   * directory.
   */
  @Test
  public void testSaveToDirectory() {
    try {
      String[] input = {"C:\\Users"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
  }

  /**
   * It would test the case that need to create a new file to store the JShell's status.
   */
  @Test
  public void testCreateNewFileAndSave() {
    try {
      String[] input = {"D:\\saveJShellForTest1.txt"};
      saveJShell.execute(input, tracker);
      Tracker newTracker = new Tracker();
      newTracker = loadJShell.execute(input, newTracker);
      assertEquals(tracker.getCurrentDirectory().getName(),
          newTracker.getCurrentDirectory().getName());
      assertEquals(tracker.getRootDirectory().getName(), newTracker.getRootDirectory().getName());
      assertEquals(tracker.getRootDirectory().getSub().get(0).getName(),
          newTracker.getRootDirectory().getSub().get(0).getName());
      assertEquals(tracker.getRootDirectory().getSub().get(1).getName(),
          newTracker.getRootDirectory().getSub().get(1).getName());
      assertEquals(
          ((Directory) tracker.getRootDirectory().getSub().get(0)).getSub().get(0).getName(),
          ((Directory) newTracker.getRootDirectory().getSub().get(0)).getSub().get(0).getName());
      assertEquals(
          ((Directory) tracker.getRootDirectory().getSub().get(1)).getSub().get(0).getName(),
          ((Directory) newTracker.getRootDirectory().getSub().get(1)).getSub().get(0).getName());
      assertEquals(tracker.getHistory(), newTracker.getHistory());
    } catch (SystemErrorException e) {
      fail("No exception should be thrown");
    }
  }

  /**
   * It would test if the commmand can overwrite a file with the JShell's status.
   */
  @Test
  public void testSaveToFileByOverwriting() {
    try {
      String[] input = {"D:\\saveJShellForTest2.txt"};
      saveJShell.execute(input, tracker);
      Directory directoryD = new Directory(root, "FolderD");
      root.getSub().add(directoryD);
      saveJShell.execute(input, tracker);
      Tracker newTracker = new Tracker();
      newTracker = loadJShell.execute(input, newTracker);
      assertEquals(tracker.getCurrentDirectory().getName(),
          newTracker.getCurrentDirectory().getName());
      assertEquals(tracker.getRootDirectory().getName(), newTracker.getRootDirectory().getName());
      assertEquals(tracker.getRootDirectory().getSub().get(0).getName(),
          newTracker.getRootDirectory().getSub().get(0).getName());
      assertEquals(tracker.getRootDirectory().getSub().get(1).getName(),
          newTracker.getRootDirectory().getSub().get(1).getName());
      assertEquals(tracker.getRootDirectory().getSub().get(2).getName(),
          newTracker.getRootDirectory().getSub().get(2).getName());
      assertEquals(
          ((Directory) tracker.getRootDirectory().getSub().get(0)).getSub().get(0).getName(),
          ((Directory) newTracker.getRootDirectory().getSub().get(0)).getSub().get(0).getName());
      assertEquals(
          ((Directory) tracker.getRootDirectory().getSub().get(1)).getSub().get(0).getName(),
          ((Directory) newTracker.getRootDirectory().getSub().get(1)).getSub().get(0).getName());
      assertEquals(tracker.getHistory(), newTracker.getHistory());
    } catch (SystemErrorException e) {
      fail("No exception should not be thrown");
    }
  }

  /**
   * It would test whether the command throws the correct exception if the file need to be created
   * contains invalid character(Windows rules for naming a file).
   */
  @Test
  public void testFilenameContainsInvalidCharacterPart1() {
    try {
      String[] input = {"D:\\save*.txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
    try {
      String[] input = {"D:\\save/.txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
    try {
      String[] input = {"D:\\save<.txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
  }

  /**
   * It would test whether the command throws the correct exception if the file need to be created
   * contains invalid character(Windows rules for naming a file).
   */
  @Test
  public void testFilenameContainsInvalidCharacterPart2() {
    try {
      String[] input = {"D:\\save<.txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
    try {
      String[] input = {"D:\\save?.txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
    try {
      String[] input = {"D:\\save\".txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
  }

  /**
   * It would test whether the command throws the correct exception if the file need to be created
   * contains invalid character(Windows rules for naming a file).
   */
  @Test
  public void testFilenameContainsInvalidCharacterPart3() {
    try {
      String[] input = {"D:\\save|.txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
    try {
      String[] input = {"D:\\save\\.txt"};
      saveJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not valid");
    }
  }

}
