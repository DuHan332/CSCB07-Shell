package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import command.MoveItem;
import driver.Tracker;
import exception.SystemErrorException;
import nodetype.Directory;
import nodetype.File;

public class MoveItemTest {

  private MoveItem mv;
  private Directory root;
  private Tracker tracker;

  @Before
  public void setUp() {
    mv = new MoveItem();
    root = new Directory(null, "/");
    Directory directoryA = new Directory(root, "FolderA");
    root.getSub().add(directoryA);

    File fileA = new File("I am A", directoryA, "FileA");
    directoryA.getSub().add(fileA);

    Directory directoryB = new Directory(root, "FolderB");
    root.getSub().add(directoryB);

    Directory directoryC = new Directory(directoryB, "FolderC");
    directoryB.getSub().add(directoryC);

    File fileB = new File("I am B", directoryB, "FileB");
    directoryB.getSub().add(fileB);

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
    assertEquals(((Directory) root.getSub().get(1)).getSub().get(1).getName(), "FileB");
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
   * It would test whether the CopyItem command return the exception as wanted for no parameter
   */
  @Test
  public void testParameterWithNoPath() {
    String[] parameter = new String[0];
    try {
      mv.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "Lossing OLDPATH and NEWPATH!");
    }
  }

  /**
   * It would test whether the MoveItem command return the exception as wanted for one parameter
   */
  @Test
  public void testParameterWithOnePath() {
    String[] parameter = new String[1];
    try {
      mv.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "Lossing NEWPATH!");
    }
  }

  /**
   * It would test whether the MoveItem command return the exception as wanted for more than two
   * parameter
   */
  @Test
  public void testParameterWithMoreThanTwoPath() {
    String[] parameter = new String[3];
    try {
      mv.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "There are more than two path!");
    }
  }

  /**
   * It would test whether the MoveItem command return the exception as wanted for wrong old path
   */
  @Test
  public void testWrongOldPath() {
    String[] parameter = new String[2];
    parameter[0] = "/FolderA/FolderV";
    parameter[1] = "/FolderB/FolderC";
    try {
      mv.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The OLDPATH is wrong");
    }
  }

  /**
   * It would test whether the MoveItem command return the exception as wanted if the new path is
   * wrong directory
   */
  @Test
  public void testWrongNewPathOfDirectory() {
    String[] parameter = new String[2];
    parameter[0] = "/FolderA";
    parameter[1] = "/FolderB/FolderC/FolderF";
    try {
      mv.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The NEWPATH is wrong");
    }
  }

  /**
   * It would test whether the MoveItem command return the exception as wanted if the new path is a
   * file
   */
  @Test
  public void testWrongNewPathOfFile() {
    String[] parameter = new String[2];
    parameter[0] = "/FolderA";
    parameter[1] = "/FolderB/FileB";
    try {
      mv.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The NEWPATH is a file");
    }
  }

  /**
   * It would test whether the MoveItem command return the exception as wanted if there already
   * exists a file with the same name in the new path
   */
  @Test
  public void testMoveFileWithExistingName() {
    String[] parameter = new String[2];
    parameter[0] = "/FolderA/FileA";
    parameter[1] = "/FolderA";
    try {
      mv.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "There already exists a file named: FileA");
    }
  }

  /**
   * It would test whether the MoveItem command return the exception as wanted if there already
   * exists a directory with the same name in the new path
   */
  @Test
  public void testMoveDirectoryWithExistingName() {
    String[] parameter = new String[2];
    parameter[0] = "/FolderB/FolderC";
    parameter[1] = "/FolderB";
    try {
      mv.execute(parameter, tracker);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "There already exists a directory named: FolderC");
    }
  }

  /**
   * It would test if the MoveItem command copy the file into the new path correctly
   */
  @Test
  public void testCopyFile() throws SystemErrorException {
    String[] parameter = new String[2];
    parameter[0] = "/FolderA/FileA";
    parameter[1] = "FolderB";
    mv.execute(parameter, tracker);
    assertEquals(((Directory) root.getSub().get(1)).getSub().get(2).getName(), "FileA");
  }

  /**
   * It would test if the MoveItem command copy the empty directory into the new path correctly
   */
  @Test
  public void testCopyEmptyDirectory() throws SystemErrorException {
    String[] parameter = new String[2];
    parameter[0] = "/FolderB/FolderC";
    parameter[1] = "FolderA";
    mv.execute(parameter, tracker);
    assertEquals(((Directory) root.getSub().get(0)).getSub().get(1).getName(), "FolderC");
  }

  /**
   * It would test if the MoveItem command copy the non-empty directory into the new path correctly
   */
  @Test
  public void testCopyNonEmptyDirectory() throws SystemErrorException {
    String[] parameter = new String[2];
    parameter[0] = "FolderB";
    parameter[1] = "FolderA";
    mv.execute(parameter, tracker);
    assertEquals(((Directory) root.getSub().get(0)).getSub().get(1).getName(), "FolderB");
    assertEquals(
        ((Directory) ((Directory) root.getSub().get(0)).getSub().get(1)).getSub().get(0).getName(),
        "FolderC");
    assertEquals(
        ((Directory) ((Directory) root.getSub().get(0)).getSub().get(1)).getSub().get(1).getName(),
        "FileB");
  }

  /**
   * It would test if the MoveItem return the manual of itself
   */
  @Test
  public void testManual() {
    assertEquals(mv.getManual(),
        "mv OLDPATH NEWPATH:\nMove item OLDPATH to NEWPATH. Both OLD-"
            + "PATH and NEWPATH may be relative to\nthe current directory or may be "
            + "fullpaths. If NEWPATH is a directory, move\nthe item into the directory.");
  }

}
