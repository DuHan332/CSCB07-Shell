package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import nodetype.*;
import org.junit.Before;
import org.junit.Test;
import helper.FindPath;
import driver.Tracker;

/**
 * For my tests, I choose to use integration testing, since this function would just find a file or
 * directory which is a part of a file system so i choose to test them together to ensure no error
 * occurs.
 * 
 * @author Du Han
 */
public class FindPathTest {
  Directory root;
  Tracker tracker;
  ArrayList<String> input;

  /**
   * It would set up a tree for file system and a tracker that track the file system to test
   */
  @Before
  public void setUp() {
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
    input = new ArrayList<String>();
    input.add("FolderA");
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
   * This test would test the FindPath to find a existed directory.
   */
  @Test
  public void testFindPathWithDirectory() {
    assertEquals("FolderA", FindPath.findpath(input, root).getName());
  }

  /**
   * This test would test the FindPath to find a existed file
   */
  @Test
  public void testFindPathWithFile() {
    input.add("FileA");
    assertEquals("FileA", FindPath.findpath(input, root).getName());
  }

  /**
   * This test would test the FindPath to find a file or directory that does not exist
   */
  @Test
  public void testFindPathWithError() {
    input.add("FileDNE");
    assertEquals(null, FindPath.findpath(input, root));
  }
}
