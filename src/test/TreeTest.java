package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.Tree;
import driver.Tracker;
import exception.SystemErrorException;
import nodetype.Directory;
import nodetype.File;

/**
 * For my tests, I choose to use integration testing, since this command is just show the structure
 * of the whole file system so i choose to test them together to ensure no error occurs.
 * 
 * @author Du Han
 */
public class TreeTest {
  Directory root;
  Tree tree;
  Tracker tracker;

  /**
   * It would set up a tree for file system and a tracker that track the file system to test
   */
  @Before
  public void setUp() {
    tree = new Tree();
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
   * These tests would test tree command in several cases: no parameter, then add a new directory
   * into file system and get the new result.
   */
  @Test
  public void testTree() {
    String[] input0 = {};
    String expected0 =
        "/\n" + "  FolderA\n" + "    FileA\n" + "    FileB\n" + "    FolderB\n" + "      FileC";
    try {
      assertEquals(expected0, tree.execute(input0, tracker));
    } catch (SystemErrorException e) {
      fail();
    }

    Directory newDirectory = new Directory(root, "FolderD");
    root.getSub().add(newDirectory);
    String[] input1 = {};
    String expected1 = "/\n" + "  FolderA\n" + "    FileA\n" + "    FileB\n" + "    FolderB\n"
        + "      FileC\n" + "  FolderD";
    try {
      assertEquals(expected1, tree.execute(input1, tracker));
    } catch (SystemErrorException e) {
      fail();
    }
  }

  /**
   * This test would test tree command with any parameter(s), which are all invalid inputs.
   */
  @Test
  public void testTreeWithError() {
    String[] input = {"FileSystem"};
    try {
      assertEquals("", tree.execute(input, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals("This command can't take input", e.getMessage());
    }

    String[] input1 = {"FileSystem", "System"};
    try {
      assertEquals("", tree.execute(input1, tracker));
      fail();
    } catch (SystemErrorException e) {
      assertEquals("This command can't take input", e.getMessage());
    }
  }
}
