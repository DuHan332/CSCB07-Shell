package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.List;
import command.RemoveDirectory;
import driver.Tracker;
import exception.SystemErrorException;
import nodetype.Directory;
import nodetype.File;

/**
 * This class is used for testing the class of RemoveDirectory, it will test the whether the command
 * remove the directory or throw the exception in the correct way.Notice that we need to guarantee
 * the class of List passes its test, since we use the command list for testing.
 * 
 * @author Yuanqian Fang
 *
 */
public class RemoveDirectoryTest {
  private RemoveDirectory rm;
  private List ls;
  private Directory root;
  private Tracker tracker;

  /**
   * It would initialize the file system used for testing.
   */

  @Before
  public void setUp() {
    rm = new RemoveDirectory();
    ls = new List();
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
   * It would test whether the command throws the correct exception if there is not only one
   * parameter.
   */
  @Test
  public void testTheLengthOfInputIsNotOne() {
    String[] emptyInput = {};
    String[] manyInput = {"FolderA", "FolderB"};
    try {
      rm.execute(emptyInput, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "This command can only take one agrument");
    }
    try {
      rm.execute(manyInput, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "This command can only take one agrument");
    }
  }

  /**
   * It would test whether the command throws the correct exception if user wants to delete the
   * root.
   */
  @Test
  public void testDeleteRoot() {
    String[] input = {"/"};
    try {
      rm.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "You are not able to delete the root directory");
    }
  }

  /**
   * It would test whether the command throws the correct exception if the current directory is the
   * sub directory that user wants to delete.
   */
  @Test
  public void testCurrentDirectoryIsSubDirectoryOfRemoveDirectory() {
    Directory directoryD = new Directory(root, "FolderD");
    root.getSub().add(directoryD);
    Directory directoryE = new Directory(directoryD, "FolderE");
    directoryD.getSub().add(directoryE);
    tracker.setCurrentDirectory(directoryE);
    String[] input = {"/FolderD"};
    try {
      rm.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(),
          "You can't delete a directory that current directory is its subdirectory");
    }
  }

  /**
   * It would test whether the command throws the correct exception if the user wants to delete a
   * file rather than a directory.
   */
  @Test
  public void testRemoveFile() {
    String[] input = {"/FolderA/FileA"};
    try {
      rm.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "It's not a directory");
    }
  }

  /**
   * It would test whether the command throws the correct exception if the path doesn't exist in the
   * file system.
   */
  @Test
  public void testPathDoesNotExist() {
    String[] input = {"/FolderA/FolderC"};
    try {
      rm.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The path doesn't exist");
    }
  }

  /**
   * It would test if the path is a full path.
   */
  @Test
  public void testInputIsFullPath() {
    String[] inputForRemove = {"/FolderB/FolderC"};
    String[] inputForList = {"/FolderB"};
    try {
      rm.execute(inputForRemove, tracker);
      assertEquals(ls.execute(inputForList, tracker), "FolderB:");
    } catch (SystemErrorException e) {
      fail("No exception should be thrown");
    }
  }

  /**
   * It would test if the path is a relative path.
   */
  @Test
  public void testInputIsRelativePath() {
    String[] inputForRemove = {"FolderB/FolderC"};
    String[] inputForList = {"/FolderB"};
    try {
      rm.execute(inputForRemove, tracker);
      assertEquals(ls.execute(inputForList, tracker), "FolderB:");
    } catch (SystemErrorException e) {
      fail("No exception should be thrown");
    }

  }
}
