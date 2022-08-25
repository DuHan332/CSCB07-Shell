package test;

import static org.junit.Assert.*;
import java.io.FileOutputStream;
import org.junit.Before;
import org.junit.Test;
import command.LoadJShell;
import command.SaveJShell;
import driver.Tracker;
import exception.SystemErrorException;
import nodetype.Directory;
import nodetype.File;

/**
 * This class is used for testing the class of LoadJShell, it will test whether the command can load
 * the contents of the specific file in the real file system and reinitialize everything that was
 * saved previously into the file. And it will also test whether the command could throw the correct
 * exception.
 * 
 * @author Yuanqian Fang
 *
 */
public class LoadJShellTest {
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
    assertEquals(loadJShell.getManual(),
        "loadJShell FileName:\nWhen the "
            + "above command is typed,  JShell would load the contents of the FileName\n"
            + "and reinitialize everything that was saved previously into the FileName."
            + " This\nallows the user to start a new JShell session, type in load FileName "
            + "and resume\nactivity from where it was left off previously. If it is not the "
            + "first command\nthat is typed, this command would be disabled.");
  }

  /**
   * It would test whether the command throw the correct exception if the input is empty.
   */
  @Test
  public void testInputIsEmpty() {
    try {
      String[] input = {};
      loadJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "This command must take one argument");
    }
  }

  /**
   * It would test whether the command throw the correct exception if the input is more than one.
   */
  @Test
  public void testInputMoreThanOne() {
    try {
      String[] input = {"C:\\Users\\save.txt", "C:\\Users\\Admin\\save.txt"};
      loadJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "This command must take one argument");
    }
  }

  /**
   * It would test whether the command throw the correct exception if the user use this command
   * after using other commands.
   */
  @Test
  public void testUseLoadJShellAfterUsingOtherCommands() {
    try {
      tracker.getHistory().add("mkdir FolderD");
      tracker.getHistory().add("loadJShell C:\\Users\\save.txt");
      String[] input = {"C:\\Users\\save.txt"};
      loadJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "You can use this command after using other commands already");
    }
  }

  /**
   * It would test whether the command throw the correct exception if the file can not found in the
   * real file system.
   */
  @Test
  public void testFileDoesNotExistInTheFileSystem() {
    try {
      String[] input = {"C:\\DNEFile"};
      loadJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not found or invalid");
    }
  }

  /**
   * It would test whether the command throw the correct exception if the user wants to load from a
   * directory.
   */
  @Test
  public void testLoadDirectory() {
    try {
      String[] input = {"C:\\Users"};
      loadJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not found or invalid");
    }
  }

  /**
   * It would test whether the command throw the correct exception if the contents of the file can
   * not not read.
   */
  @Test
  public void testNotRecognizable() {
    try {
      FileOutputStream fos = new FileOutputStream("D:\\save.txt");
      fos.write("Invalid String".getBytes(), 0, "Invalid String".getBytes().length);
      fos.close();
    } catch (Exception e) {
      fail("No exception should be thrown");
    }
    try {
      String[] input = {"D:\\save.txt"};
      loadJShell.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path is not found or invalid");

    }
  }

  /**
   * It would test if the process of loading is successful.
   */
  @Test
  public void testLoadJShell() {
    try {
      String[] inputForSave = {"D:\\saveJShell.txt"};
      saveJShell.execute(inputForSave, tracker);
      Tracker newTracker = new Tracker();
      String[] inputForLoad = {"D:\\saveJShell.txt"};
      newTracker = loadJShell.execute(inputForLoad, newTracker);
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
}
