package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import nodetype.Directory;
import nodetype.JNode;

/**
 * This class is used for testing the class of JNode, it will test the methods in the class of
 * JNode.
 * 
 * @author Yuanqian Fang
 *
 */
public class JNodeTest {
  JNode node;

  /**
   * It would create an object that is JNode type used for testing.
   */
  @Before
  public void setUp() {
    node = new JNode();
  }

  /**
   * It would test the getIsDirectory and setIsDirectory methods.
   */
  @Test
  public void testGetAndSetIsDirectory() {
    node.setIsDirectory(true);
    assertEquals(node.getIsDirectory(), true);
    node.setIsDirectory(false);
    assertEquals(node.getIsDirectory(), false);
  }

  /**
   * It would test the getName and setName methods.
   */
  @Test
  public void testGetAndSetName() {
    assertEquals(node.getName(), null);
    node.setName("I am a JNode");
    assertEquals(node.getName(), "I am a JNode");
    node.setName("I am a good JNode");
    assertEquals(node.getName(), "I am a good JNode");
  }

  /**
   * It would test the getRoot and setRoot methods.
   */
  @Test
  public void testGetAndSetRoot() {
    Directory directory1 = new Directory(null, "FolderA");
    Directory directory2 = new Directory(null, "FolderB");
    assertEquals(node.getRoot(), null);
    node.setRoot(directory1);
    assertEquals(node.getRoot(), directory1);
    node.setRoot(directory2);
    assertEquals(node.getRoot(), directory2);
  }
}
