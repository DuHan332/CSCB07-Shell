package test;

import static org.junit.Assert.*;
import org.junit.Test;
import nodetype.Directory;
import nodetype.File;

/**
 * This class is used for testing the class of File, it will test the method and constructor in the
 * class of File.
 * 
 * @author Yuanqian Fang
 *
 */
public class FileTest {
  /**
   * It would test the constructor.
   */
  @Test
  public void testConstructor() {
    Directory directory = new Directory(null, "FolderA");
    File file = new File("I am a file", directory, "FileA");
    assertEquals(file.getContent(), "I am a file");
    assertEquals(file.getRoot(), directory);
    assertEquals(file.getName(), "FileA");
    assertEquals(file.getIsDirectory(), false);
  }

  /**
   * It would test the getContent method.
   */
  @Test
  public void testGetContent() {
    File file = new File("I am a file", null, "FileA");
    assertEquals(file.getContent(), "I am a file");
  }

  /**
   * It would test the setContent method.
   */
  @Test
  public void testSetContent() {
    File file = new File("I am a file", null, "FileA");
    assertEquals(file.getContent(), "I am a file");
    file.setContent("I am not a directory");
    assertEquals(file.getContent(), "I am not a directory");

  }
}
