package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import nodetype.Directory;
import nodetype.File;
import nodetype.JNode;

/**
 * This class is used for testing the class of Directory, it will test the method and constructor in
 * the class of Directory.
 * 
 * @author Yuanqian Fang
 *
 */
public class DirectoryTest {
  /**
   * It would test the constructor.
   */

  @Test
  public void testConstructor() {
    Directory directory1 = new Directory(null, "FolderA");
    Directory directory2 = new Directory(directory1, "FolderB");
    ArrayList<JNode> sub = new ArrayList<JNode>();
    assertEquals(directory2.getRoot(), directory1);
    assertEquals(directory2.getName(), "FolderB");
    assertEquals(directory2.getIsDirectory(), true);
    assertEquals(directory2.getSub(), sub);
  }

  /**
   * It would test getSub and setSub
   */
  @Test
  public void testGetSubAndSetSub() {
    Directory directory1 = new Directory(null, "FolderA");
    Directory directory2 = new Directory(directory1, "FolderB");
    File file = new File("I am a file", directory1, "FileA");
    ArrayList<JNode> sub = new ArrayList<JNode>();
    assertEquals(directory1.getSub(), sub);
    sub.add(directory2);
    sub.add(file);
    directory1.setSub(sub);
    assertEquals(directory1.getSub(), sub);
  }

}
