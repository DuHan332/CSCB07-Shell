package test;

import static org.junit.Assert.*;
import nodetype.*;
import org.junit.Before;
import org.junit.Test;
import command.List;
import driver.Tracker;
import exception.SystemErrorException;

/**
 * For my tests, I choose to use integration testing, since this command is just show the contents
 * of directories which is a part of a file system so i choose to test them together to ensure no
 * error occurs.
 * 
 * @author Du Han
 */
public class ListTest {
  Directory root;
  List list;
  Tracker tracker;

  /**
   * It would set up a tree for file system and a tracker that track the file system to test
   */
  @Before
  public void setUp() {
    list = new List();
    root = new Directory(null, "/");
    Directory directory = new Directory(root, "FolderA");
    root.getSub().add(directory);

    File file1 = new File("I am A", directory, "FileA");
    directory.getSub().add(file1);

    File file2 = new File("I am B", directory, "FileB");
    directory.getSub().add(file2);

    Directory directory2 = new Directory(directory, "FolderB");
    directory.getSub().add(directory2);

    File file3 = new File("I am C", directory2, "FileC");
    directory2.getSub().add(file3);

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
    assertEquals("FileA", ((Directory) root.getSub().get(0)).getSub().get(0).getName());
    assertEquals("FileB", ((Directory) root.getSub().get(0)).getSub().get(1).getName());
    assertEquals("FolderB", ((Directory) root.getSub().get(0)).getSub().get(2).getName());
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
   * These tests would test command for several cases: no parameter, correct parameter with full
   * path, correct parameter with relative path, more than one correct parameters.Each input from
   * input0-4 is a case.
   */
  @Test
  public void testList() {
    try {
      String[] input0 = {};
      assertEquals("/:FolderA", list.execute(input0, tracker));

      String[] input1 = {"FolderA"};
      assertEquals("FolderA:FileA FileB FolderB", list.execute(input1, tracker));

      String[] input2 = {"FolderA/FileB"};
      assertEquals("FileB", list.execute(input2, tracker));

      String[] input3 = {"/FolderA"};
      assertEquals("FolderA:FileA FileB FolderB", list.execute(input3, tracker));

      String[] input4 = {"FolderA", "/FolderA/FolderB"};
      assertEquals("FolderA:FileA FileB FolderB\n" + "\n" + "FolderB:FileC",
          list.execute(input4, tracker));
    } catch (SystemErrorException e) {
      fail();
    }
  }

  /**
   * These tests would test command for several cases: a series of parameters including incorrect
   * parameter(the path does not exists) and single incorrect parameter. Which all contain invalid
   * inputs. Each input from input0-2 is a case.
   */
  @Test
  public void testListWithError() {
    try {
      String[] input0 = {"FolderA", "FolderDNE"};
      assertEquals("FolderA:FileA FileB FolderB\r" + "FolderDNE directory does not exist",
          list.execute(input0, tracker));

      String[] input1 = {"FolderA", "FolderDNE", "FolderA/FolderB"};
      assertEquals("FolderA:FileA FileB FolderB\r" + "FolderDNE directory does not exist",
          list.execute(input1, tracker));

      String[] input2 = {"FolderDNE"};
      assertEquals("\rFolderDNE directory does not exist", list.execute(input2, tracker));
    } catch (SystemErrorException e) {
      fail();
    }

  }

  /**
   * These tests would test list command in recursive case for several conditions: no parameter,
   * correct parameter with full path, correct parameter with relative path, more than one correct
   * parameters. Each input from input0-4 is a case.
   */
  @Test
  public void testListRe() {
    try {
      String[] input0 = {"-R"};
      assertEquals("/:FolderA\n\nFolderA:FileA FileB FolderB\n" + "\nFolderB:FileC",
          list.execute(input0, tracker));

      String[] input1 = {"-R", "FolderA"};
      assertEquals("FolderA:FileA FileB FolderB\n" + "\nFolderB:FileC",
          list.execute(input1, tracker));

      String[] input2 = {"-R", "FolderA/FileB"};
      assertEquals("FileB", list.execute(input2, tracker));

      String[] input3 = {"-R", "/FolderA"};
      assertEquals("FolderA:FileA FileB FolderB\n" + "\nFolderB:FileC",
          list.execute(input3, tracker));

      String[] input4 = {"-R", "FolderA", "/FolderA/FolderB"};
      assertEquals("FolderA:FileA FileB FolderB\n\nFolderB:FileC\n\nFolderB:FileC",
          list.execute(input4, tracker));
    } catch (SystemErrorException e) {
      fail();
    }
  }

  /**
   * These tests would test list command in recursive case for several conditions: a series of
   * parameters including incorrect parameter(the path does not exists) and single incorrect
   * parameter. Which all contain invalid inputs. Each input from input0-2 is a case.
   */
  @Test
  public void testListReWithError() {
    try {
      String[] input0 = {"-R", "FolderA", "FolderDNE"};
      assertEquals("FolderA:FileA FileB FolderB\n" + "\nFolderB:FileC\r"
          + "FolderDNE directory does not exist", list.execute(input0, tracker));

      String[] input1 = {"-R", "FolderA", "FolderDNE", "FolderA/FolderB"};
      assertEquals("FolderA:FileA FileB FolderB\n" + "\nFolderB:FileC\r"
          + "FolderDNE directory does not exist", list.execute(input1, tracker));

      String[] input2 = {"-R", "FolderDNE"};
      assertEquals("\rFolderDNE directory does not exist", list.execute(input2, tracker));
    } catch (SystemErrorException e) {
      fail();
    }


  }
}
