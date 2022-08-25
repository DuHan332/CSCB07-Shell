package test;

import static org.junit.Assert.*;
import nodetype.*;
import org.junit.Before;
import org.junit.Test;
import command.Concatenate;
import driver.Tracker;
import exception.SystemErrorException;

/**
 * For my tests, I choose to use integration testing, since this command is just show the contents
 * of files which is a part of a file system so i choose to test them together to ensure no error
 * occurs.
 * 
 * @author Du Han
 */
public class ConcatenateTest {
  Directory root;
  Concatenate cat;
  Tracker tracker;

  /**
   * It would set up a tree and a tracker that track the file system to test
   */
  @Before
  public void setUp() {
    cat = new Concatenate();
    root = new Directory(null, "/");
    Directory directory = new Directory(root, "FolderA");
    root.getSub().add(directory);

    File file1 = new File("I am A", root, "FileA");
    root.getSub().add(file1);

    File file2 = new File("I am B", directory, "FileB");
    directory.getSub().add(file2);

    File file3 = new File("I am C", directory, "FileC");
    directory.getSub().add(file3);

    tracker = new Tracker();
    tracker.setCurrentDirectory(root);
    tracker.setRootDirectory(root);
  }

  /**
   * It would test if the file system is correct
   */
  @Test
  public void testFileSystem() {
    assertEquals("/", root.getName());
    assertEquals("FolderA", root.getSub().get(0).getName());
    assertEquals("FileA", root.getSub().get(01).getName());
    assertEquals("FileB", ((Directory) root.getSub().get(0)).getSub().get(0).getName());
    assertEquals("FileC", ((Directory) root.getSub().get(0)).getSub().get(1).getName());
  }

  /**
   * It would test if the tracker is correct
   */
  @Test
  public void testTracker() {
    assertEquals("/", tracker.getRootDirectory().getName());
    assertEquals("/", tracker.getCurrentDirectory().getName());
    assertEquals("FolderA", tracker.getRootDirectory().getSub().get(0).getName());
  }

  /**
   * These tests would test command for several conditions: correct parameter with full path,
   * correct parameter with relative path, more than one correct parameters, Each input from
   * input0-2 is a case.
   */
  @Test
  public void testConcatenate() {


    String[] input0 = {"FileA"};
    try {
      assertEquals("I am A", cat.execute(input0, tracker));
    } catch (SystemErrorException e) {
      fail();
    }

    String[] input1 = {"/FileA"};
    try {
      assertEquals("I am A", cat.execute(input1, tracker));
    } catch (SystemErrorException e) {
      fail();
    }

    String[] input2 = {"FileA", "FolderA/FileB"};
    try {
      assertEquals("I am A\n\n\n\nI am B", cat.execute(input2, tracker));
    } catch (SystemErrorException e) {
      fail();
    }
  }

  /**
   * These tests would test cat command with several cases: no parameter, an incorrect parameter(the
   * path does not exists and the path is not a file) a series of parameters including incorrect
   * parameter(the path does not exists).Which all contain invalid inputs. Each input from input0-3
   * is a case.
   */
  @Test
  public void testConcatenateError() {
    String[] input0 = {};
    try {
      assertEquals("", cat.execute(input0, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals("must have at least one parameter", e.getMessage());
    }

    String[] input1 = {"FileDNE"};
    try {
      assertEquals("\rFileDNE does not exist", cat.execute(input1, tracker));
    } catch (SystemErrorException e) {
      fail();
    }

    String[] input2 = {"FileA", "FileDNE", "FileA"};
    try {
      assertEquals("I am A\rFileDNE does not exist", cat.execute(input2, tracker));
    } catch (SystemErrorException e) {
      fail();
    }

    String[] input3 = {"FolderA"};
    try {
      assertEquals("\rFolderA is not a file", cat.execute(input3, tracker));
    } catch (SystemErrorException e) {
      fail();
    }
  }

}
