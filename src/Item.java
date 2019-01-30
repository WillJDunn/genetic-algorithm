/**
 * An Item is an element of the dataset.
 * it contains the following elements:
 *   itemNum: (int) a unique identifier for the Item
 *   value: (int) represents how much benefit the Item provides
 *   cost: (int) represents how much capacity the Item takes
 *   
 * @author willj
 *
 */
public class Item {
  int itemNum;
  int value;
  int cost;
  
  /**
   * Constructor for the Item object when details about the Item are provided
   * @param itemNum
   *   (int) a unique identifier for the Item
   * @param value
   *   (int) how much benefit the Item provides
   * @param cost
   *   (int) how much capacity the Item takes
   */
  public Item(int itemNum, int value, int cost) {
    this.itemNum = itemNum;
    this.value = value;
    this.cost = cost;    
  }
  
  /**
   * Constructor for the Item object when details about the Item have not been provided. Creates
   * the object without filling itemNum, value, or cost
   */
  public Item() {
    // Do nothing
  }
  
  /**
   * Getter for itemNum
   * @return
   *   (int) Item unique identifier
   */
  public int getItemNum() {
    return this.itemNum;
  }
  
  /**
   * Getter for value
   * @return
   *   (int) Item value - how much benefit the Item provides
   */
  public int getValue() {
    return this.value;
  }
  
  /**
   * Getter for cost
   * @return
   *   (int) Item cost - how much capacity the Item takes
   */
  public int getCost() {
    return this.cost;
  }
  
  /**
   * Setter for itemNum
   *   itemNum: (int) Item unique identifier
   */
  public void setItemNum(int itemNum) {
    this.itemNum = itemNum;
  }
  
  /**
   * Setter for value
   *   value: (int) Item value - how much benefit the Item provides
   */
  public void setValue(int value) {
    this.value = value;
  }
  
  /**
   * Setter for cost
   *   cost: (int) Item cost - how much capacity the Item takes
   */
  public void setCost(int cost) {
    this.cost = cost;
  } 
}
