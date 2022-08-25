package test;

import static org.junit.Assert.*;
import org.junit.Test;
import exception.SystemErrorException;
import helper.ModifyInput;

public class ModifyInputTest {

  /**
   * It would test whether the ChangeDirectory return the exception as wanted for wrong qutation
   */
  @Test
  public void testModifyInputWithWrongQutation() {
    String input = "echo \"Hello World > file1";
    try {
      ModifyInput.modifyInput(input);
    } catch (SystemErrorException e) {
      assertEquals(e.getMessage(), "The input is invalid because there is 1 quotation mark.");
    }
  }

  /**
   * It would test if ModifyInput modify the input correctly
   */
  @Test
  public void testModifyInputWithCorrectQutation() throws SystemErrorException {
    String input = "echo \"Hello World\" > file1";
    String[] result = {"echo", "\"Hello World\"", ">", "file1"};
    String[] result1 = ModifyInput.modifyInput(input);
    assertEquals(result[0], result1[0]);
    assertEquals(result[1], result1[1]);
    assertEquals(result[2], result1[2]);
    assertEquals(result[3], result1[3]);
  }

}
