package nodetype;

/**
 * This class is used to be parent class of Directory or File
 * 
 * @author Yuanqian Fang
 *
 */
public class JNode implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * a String represents the name of JNode
   */
  private String name;
  /**
   * a Directory represents the root of the JNode
   */
  private Directory root;
  /**
   * a Boolean represents whether the JNode is a directory
   */
  private Boolean IsDirectory;

  /**
   * This function can get the a boolean that represents whether the JNode is a directory.
   * 
   * @return true or false to represent whether the JNode is a directory or not
   */
  public Boolean getIsDirectory() {
    return IsDirectory;
  }

  /**
   * This function can set the is_directory.
   * 
   * @param is_directory a boolean that used to set the is_directory
   */
  public void setIsDirectory(Boolean IsDirectory) {
    this.IsDirectory = IsDirectory;
  }

  /**
   * This function can get the name of the JNode.
   * 
   * @return a string represents the JNode's name
   */
  public String getName() {
    return name;
  }

  /**
   * This function can set the name of the JNode.
   * 
   * @param name a string used to set the name of the JNode
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * This function can get the JNode's root
   * 
   * @return a directory represents the JNode's root
   */
  public Directory getRoot() {
    return this.root;
  }

  /**
   * This function can set the JNode's root.
   * 
   * @param root a directory used to set the JNode's root
   */
  public void setRoot(Directory root) {
    this.root = root;
  }
}
