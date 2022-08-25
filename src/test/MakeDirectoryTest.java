package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.List;
import command.MakeDirectory;
import driver.Tracker;
import exception.SystemErrorException;
import nodetype.Directory;
import nodetype.File;

/**
 * This class is used for testing the class of MakeDirectory, it will test whether the command make
 * directories in a correct way and it will test whether the command throws correct exception.Notice
 * that we need to guarantee the class of List passes its test, since we use the command list to
 * test.
 * 
 * @author Yuanqian Fang
 *
 */
public class MakeDirectoryTest {
  Directory root;
  MakeDirectory mkdir;
  List ls;
  Tracker tracker;

  /**
   * It would initialize the file system used for testing.
   */
  @Before
  public void setUp() throws SystemErrorException {
    mkdir = new MakeDirectory();
    ls = new List();
    root = new Directory(null, "/");
    Directory directory1 = new Directory(root, "FolderA");
    root.getSub().add(directory1);

    Directory directory2 = new Directory(root, "FolderB");
    root.getSub().add(directory2);

    File file1 = new File("My name is FileA", directory2, "FileA");
    directory2.getSub().add(file1);

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
    assertEquals(((Directory) root.getSub().get(1)).getSub().get(0).getName(), "FileA");
  }

  /**
   * It would test if the tracker is correct
   */
  @Test
  public void testTracker() {
    assertEquals(tracker.getRootDirectory().getName(), "/");
    assertEquals(tracker.getCurrentDirectory().getName(), "/");
    assertEquals(tracker.getRootDirectory().getSub().get(0).getName(), "FolderA");
  }

  /**
   * It would test the getManual method.
   */
  @Test
  public void testGetManual() {
    assertEquals(mkdir.getManual(), "mkdir DIR1 DIR2:\n" + "This command takes in two"
        + " arguments only. Create directories, each of which\nmay be "
        + "relative to the current directory or may be a full path. If "
        + "creating\nDIR1 results in any kind of error, do not proceed with"
        + " creating DIR 2.\nHowever, if DIR1 was created successfully, "
        + "and DIR2 creation results in an\nerror, then give back an error " + "specfic to DIR2.");
  }

  /**
   * It would test the case that the input is full path.
   */
  @Test
  public void testInputIsFullPath() {
    try {
      String[] input1 = {"/FolderC", "/FolderA/FolderD", "/FolderB/FolderE"};
      mkdir.execute(input1, tracker);
      String[] input2 = {"/", "/FolderA", "FolderB"};
      assertEquals(ls.execute(input2, tracker), "/:FolderA FolderB FolderC\n" + "\n"
          + "FolderA:FolderD\n" + "\n" + "FolderB:FileA FolderE");
    } catch (SystemErrorException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * It would test the case that the input is relative path.
   */
  @Test
  public void testInputIsRelativePath() {
    try {
      String[] input1 = {"FolderC", "FolderC/FolderF"};
      mkdir.execute(input1, tracker);
      String[] input2 = {"/", "/FolderC"};
      assertEquals(ls.execute(input2, tracker),
          "/:FolderA FolderB FolderC\n" + "\n" + "FolderC:FolderF");
    } catch (SystemErrorException e) {
      fail("No exception should be thrown");
    }
  }

  /**
   * It would test whether the command throws correct exception if the user wants to create a
   * directory in a path that doesn't exist in the file system.
   */
  @Test
  public void testInvalidPath1() {
    try {
      String[] input1 = {"FolderC", "/FolderA/FolderD/FolderE", "FolderB/FolderF"};
      mkdir.execute(input1, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number2 path is invalid");
    }
    try {
      String[] input2 = {"/", "/FolderA", "/FolderB"};
      assertEquals(ls.execute(input2, tracker),
          "/:FolderA FolderB FolderC\n" + "\n" + "FolderA:\n" + "\n" + "FolderB:FileA");
    } catch (SystemErrorException e) {
      fail("No exception should be thrown");
    }
  }

  /**
   * It would test whether the command throws correct exception if the user wants to create a
   * directory under a file.
   */
  @Test
  public void testInvalidPath2() {
    try {
      String[] input1 = {"FolderC", "FolderB/FileA/FolderD", "FolderA/FolderE"};
      mkdir.execute(input1, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number2 path is invalid");
    }
    try {
      String[] input2 = {"/", "/FolderB/FileA", "/FolderA"};
      assertEquals(ls.execute(input2, tracker),
          "/:FolderA FolderB FolderC\n" + "\n" + "FileA\n" + "\n" + "FolderA:");
    } catch (SystemErrorException e) {
      fail("No exception should be thrown");
    }
  }

  /**
   * It would test whether the command throws correct exception if the input is empty.
   */
  @Test
  public void testInputIsEmpty() {
    try {
      String[] input = {};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "This command must at least take one argument");
    }
  }

  /**
   * It would test whether the command throws correct exception if the directory that user wants to
   * create has already existed.
   */
  @Test
  public void testCreateExitsDirectory() {
    try {
      String[] input = {"FolderC", "FolderC/FolderD", "FolderC/FolderD"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number3 path has already existed");
    }
  }

  /**
   * It would test whether the command throws correct exception if the name of directory has invalid
   * characters.
   */
  @Test
  public void testDirectoryNameWithInvaildCharacterPart1() {
    try {
      String[] input = {"Fol der"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder?"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder!"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
  }

  /**
   * It would test whether the command throws correct exception if the name of directory has invalid
   * characters.
   */
  @Test
  public void testDirectoryNameWithInvaildCharacterPart2() {
    try {
      String[] input = {"Folder@"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder#"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder$"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder%"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
  }

  /**
   * It would test whether the command throws correct exception if the name of directory has invalid
   * characters.
   */
  @Test
  public void testDirectoryNameWithInvaildCharacterPart3() {
    try {
      String[] input = {"Folder^"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder&"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder*"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder("};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
  }

  /**
   * It would test whether the command throws correct exception if the name of directory has invalid
   * characters.
   */
  @Test
  public void testDirectoryNameWithInvaildCharacterPart4() {
    try {
      String[] input = {"Folder)"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder{"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder}"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder~"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
  }

  /**
   * It would test whether the command throws correct exception if the name of directory has invalid
   * characters.
   */

  @Test
  public void testDirectoryNameWithInvaildCharacterPart5() {
    try {
      String[] input = {"Folder|"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder<"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
    try {
      String[] input = {"Folder>"};
      mkdir.execute(input, tracker);
      fail("No exception thrown");
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The number1 directory's name contains invalid character");
    }
  }
}
