import java.util.ArrayList;

/**
 * A Genotype is a collection of Item objects, along with methods for evaluating Chromsomes (which are subsets of the Genotypes Items).
 * TODO: implement the addFromFile() CSV methods
 */
public class Genotype {
  // A Genotype is mainly an arraylist of Items. Items must be stored in an arraylist because index lookup performance is critical
  public ArrayList<Item> genotype;
  
  // Instance variables storing information required for evaluating Chromosomes. See the constructor comments for details.
  public int idealTotalCost;
  public double valueScoreWeight;
  public double costScoreWeight;
  public int genotypeTotalCost;
  
  /**
   * Constructor for the Genotype object when weights for value and cost are provided
   * @param idealTotalCost
   *   the ideal total cost of the solution. For example, if the unit of cost is "minutes" and you want solutions to be as close to "30 minutes" as possible you would set this value to 30.
   * @param valueScoreWeight
   *   (double) the weighting (percent as decimal) of how much the Items values should impact the fitness score.
   *   lower values (such as 0.1) will result in value having a smaller impact on the fitness scores.
   *   higher values (such as 0.9) will result in value having a larger impact on the fitness scores
   * @param costScoreWeight
   *   (double) the weighting (percent as decimal) of how much the Items costs should impact the fitness score.
   *   lower values (such as 0.1) will result in cost having a smaller impact on the fitness scores.
   *   higher values (such as 0.9) will result in cost having a larger impact on the fitness scores
   */
  public Genotype(int idealTotalCost, double valueScoreWeight, double costScoreWeight) {
    // Create an arraylist for storing Items
    genotype = new ArrayList<Item>();
    
    // Set idealTotalCost to the value provided
    this.idealTotalCost = idealTotalCost;
    
    // Set the valueScoreWeight and costScoreWeight to the values provided
    this.valueScoreWeight = valueScoreWeight;
    this.costScoreWeight = costScoreWeight;
    
    // Set genotypeTotalCost = 0, since we don't have any Items yet
    this.genotypeTotalCost = 0;
  }
  
  /**
   * Constructor for the Genotype object when weights for value and cost are not provided.
   * Default values can be adjusted by changing the double values assigned to defaultValueScoreWeight and defaultCostScoreWeight
   * @param idealTotalCost
   *   (int) the ideal total cost of the solution. For example, if the unit of cost is "minutes" and you want solutions to be as close to "30 minutes" as possible you would set this value to 30. idealTotalCost can be changed later with the setidealTotalCost method
   */
  public Genotype(int idealTotalCost) {
    // Specify default values for valueScoreWeight and costScoreWeight below
    double defaultValueScoreWeight = 0.9;
    double defaultCostScoreWeight = 0.1;
    
    // Create an arraylist for the items
    genotype = new ArrayList<Item>();
    
    // Set idealTotalCost to the value provided
    this.idealTotalCost = idealTotalCost;
    
    // Set valueScoreWeight and costScoreWeight to the default values specified above
    this.valueScoreWeight = defaultValueScoreWeight;
    this.costScoreWeight = defaultCostScoreWeight;
    
    // Set genotypeTotalCost = 0, since we don't have any Items yet
    this.genotypeTotalCost = 0;
  }
  
  /**
   * Creates an Item object with specified values and adds it to the genotype
   * @param itemNum
   *   (int) a unique identifier for the Item
   * @param value
   *   (int) how much benefit the Item provides
   * @param cost
   *   (int) how much capacity the Item takes
   */
  public void add(int itemNum, int value, int cost) {
    // Use the parameters to create a new Item object
    Item item = new Item(itemNum, value, cost);
    
    // Append the new Item object to the end of the genotype arraylist
    genotype.add(item);
    
    // Update the Genotype's genotypeTotalCost variable. This variable is currently used by calculateFitness
    setIdealTotalCost(genotypeTotalCost + cost);
  }
  
  /**
   * Setter for idealTotalCost
   * @param idealCost
   *   (int) the ideal total cost of the solution. For example, if the unit of cost is "minutes" and you want solutions to be as close to "30 minutes" as possible you would set this value to 30.
   */
  public void setIdealTotalCost(int idealTotalCost) {
    this.idealTotalCost = idealTotalCost;
  }
  
  /**
   * Iterates through the Items in the genotype and calculates the sum of the costs. Updates the value of genotypeTotalCost to the calculated value
   */
  public void updateGenotypeTotalCost() {
    // Iterate through each of the items in the genotype arraylist and calculate a running total of cost
    int cost = 0;
    for (int i=0; i<genotype.size(); i++)
      cost += genotype.get(i).getCost();
    
    // Update the value of genotypeTotalCost to the calculated value
    genotypeTotalCost = cost;
  }
  
  /**
   * The genotypeTotalCost is the sum of all costs of all Items in the genotype
   * @return
   *   (int) the sum of the costs of all items in the genotype
   */
  public int getGenotypeTotalCost() {
    return genotypeTotalCost;
  }
  
  
  /**
   * Getter for idealTotalCost
   * @return
   *  (int) the ideal total cost of the solution. For example, if the unit of cost is "minutes" and you want solutions to be as close to "30 minutes" as possible you would set this value to 30.
   */
  public int getIdealTotalCost() {
    return idealTotalCost;
  }
  
  /**
   * Getter for count of Items
   * @return
   *   (int) the number of Items in the genotype
   */
  public int getCountItems() {
    return genotype.size();
  }
  
  /**
   * Getter for count of genes in each Chromosome
   * @return
   *   (int) the number of genes in each Chromosome
   */
  public int genesCount() {
    // The number of genes in each chromosome is equal to the number of items in the genotype arraylist
    return genotype.size();
  }
  
  /**
   * Getter for the weight of the value calculation of chromosomes in the fitness calculation. Weights for how important value sum is compared to the cost sum, and also balances the difference in scale between the value/cost measurements
   * @return
   *   (double) the weight of the value sum in the fitness calculation. For example 0.9
   */
  public double getValueScoreWeight() {
    return this.valueScoreWeight;
  }
  
  /**
   * Setter for the weight of the value calculation of chromosomes in the fitness calculation. Weights for how important value sum is compared to the cost sum, and also balances the difference in scale between the value/cost measurements
   * @param
   *   (double) the weight of the value sum in the fitness calculation. For example 0.9
   */
  public void setValueScoreWeight(double weight) {
    this.valueScoreWeight = weight;
  }
  
  /**
   * Getter for the weight of the cost calculation of chromosomes in the fitness calculation. Weights for how important cost sum is compared to the value sum, and also balances the difference in scale between the value/cost measurements
   * @return
   *   (double) the weight of the cost sum in the fitness calculation. For example 0.1
   */
  public double getCostScoreWeight() {
    return this.valueScoreWeight;
  }
  
  /**
   * Setter for the weight of the cost calculation of chromosomes in the fitness calculation. Weights for how important cost sum is compared to the value sum, and also balances the difference in scale between the value/cost measurements
   * @param
   *   (double) the weight of the cost sum in the fitness calculation. For example 0.1
   */
  public void setCostScoreWeight(double weight) {
    this.costScoreWeight = weight;
  }
  
  /**
   * Calculates the fitness of a Chromosome, how well the Chromosome's Items solve our problem.
   * Note: we implement the fitness function in Genotype so that all the customizations are in this class. We could have implemented it in the Chromosome class and accessed the Genotype information from there but we chose to keep the Chromosome class generic.
   * Note: It does not update Chromosome's fitnessScore instance variable, since that is done by the Population class (which is responsible for maintaining collections of Chromosomes) 
   * @param chromosome
   *   The Chromosome object to be evaluated
   * @return
   *   (int) returns a calculated score of how well the chromosome solves our problem. The better the solution the higher the fitness value.
   */
  public int calculateFitness(Chromosome chromosome) {
    // Iterate through each of genes (Items) contained in the chromosome and maintain a running sum of their values and costs
    // Running sum of value
    int chromosomeValue = 0;
    // Running sum of cost
    int chromosomeCost = 0;
    // Iterate through each gene in the chromosome
    for (int i=0; i<chromosome.geneCount(); i++)
      // If the gene value is 1 the related Item from the genotype is included in the solution
      if (chromosome.getGene(i) == 1) {
        // If the Item is in the solution add its value and cost to the running sum. Each index of the chromosome relates to the same index of the genotype arraylist containing the Items
        chromosomeValue += genotype.get(i).getValue();
        chromosomeCost += genotype.get(i).getCost();
      }
    
    // We use the sums of values and costs to calculate the fitness of the chromosome (how well the chromosome's Items solve our problem)
    // For the value score we are simply using the running sum of values
    double valueScore = chromosomeValue;
    // To measure how well the cost of the chromosome solves our problem, we subtract its cost sum from what the ideal cost would be. Since this alone would result in better solutions having a lower value we square the sum of the cost of all items in the genotype and subtract the ideal cost/ chromosome cost difference from that so that better solutions result in higher scores (this is required for some selection methods)
    double costScore = (genotypeTotalCost*genotypeTotalCost) - (Math.pow(getIdealTotalCost()-chromosomeCost,2));
    // We have double(decimal) weights for how important value is and how important cost is, and also to balance the difference in scale between the measurements. We multiply the values calculated above by the weights, cast them to an int and return
    return (int) (getValueScoreWeight()*valueScore + getCostScoreWeight()*costScore);
  }
  
  /**
   * Adds Items to the Genotype from a CSV file. The structure is itemNum,value,cost. If a header column exists it will be ignored. All numbers will be cast to int, but text in rows below the header will result in an illegal argument exception
   * @param path
   *   The path to the CSV file. 
   */
  public void addFromFile(String path) {
    throw new UnsupportedOperationException("addFromFile(path) has not yet been implemented.");
  }
  
  /**
   * Adds Items to the Genotype from a CSV file. For when the path to the file is not provided, it attempts to use the data.csv file if one is in the current directory. If there is no data.csv file in the directory it attempts to use any .csv file in the directory if only one exists. The structure is itemNum,value,cost. If a header column exists it will be ignored. All numbers will be cast to int, but text in rows below the header will result in an illegal argument exception
   * @param path
   *   The path to the CSV file. 
   */
  public void addFromFile() {
    throw new UnsupportedOperationException("addFromFile() has not yet been implemented.");
  }
  
  /**
   * Prints and returns a String with all of the genotype information of a Chromosome object. It lists the instance values of the Genotype, the details of its Items, and the fitness score. The details are listed one Item per line.
   * @param chromosome
   *   The Chromosome object whose details should be printed
   * @return
   *   A String containing all of the information about the Chromosome. It also prints it to System.out
   */
  public String printChromosomeGenotype(Chromosome chromosome) {
    // We will add all information to this string then print and return it
    String returnString = "";
    
    // Genotype level information
    returnString += "idealTotalCost: " + getIdealTotalCost() + System.lineSeparator();
    returnString += "genotypeTotalCost: " + getGenotypeTotalCost() + System.lineSeparator();
    returnString += "valueScoreWeight: " + getValueScoreWeight() + System.lineSeparator();
    returnString += "costScoreWeight: " + getCostScoreWeight() + System.lineSeparator();
    
    // Iterate through the Items contained in the chromosome and add their details to the String
    for (int i=0; i<chromosome.geneCount(); i++)
      // If the gene value is 1 the related Item from the genotype is included in the solution
      if (chromosome.getGene(i) == 1) {
        // If the Item is in the solution add its details to the String. Each index of the chromosome relates to the same index of the genotype arraylist containing the Items
        returnString += "Item: " + genotype.get(i).getItemNum();
        returnString += " Value: " + genotype.get(i).getValue();
        returnString += " Cost: " + genotype.get(i).getCost();
        returnString += System.lineSeparator();
      }
    
    // Calculate the fitness of the chromosome and add append it to the String
    returnString += " Fitness Score: " + calculateFitness(chromosome);
    
    // Print the String to System.out and return it
    System.out.println(returnString);
    return returnString;
  }
  
  /**
   * Returns a String with all of the information about a Genotype object. The details are listed one Item per line. It includes a "## GENOTYPE ##" header, the idealTotalCost, the genotypeTotalCost, the valueScoreWeight, the costScoreWeight, and each of the Items (one per line) listing the itemNumber, value, and cost.
   * @return
   *   A String containing all of the information about the Genotype.
   */
  @Override
  public String toString() {
    // We will add all information to this string then print and return it
    String returnString = "";
    
    // Genotype level information
    returnString += "## GENOTYPE ##" + System.lineSeparator();
    returnString += "idealTotalCost: " + getIdealTotalCost() + System.lineSeparator();
    returnString += "genotypeTotalCost: " + getGenotypeTotalCost() + System.lineSeparator();
    returnString += "valueScoreWeight: " + getValueScoreWeight() + System.lineSeparator();
    returnString += "costScoreWeight: " + getCostScoreWeight() + System.lineSeparator();
    
    // List the details of all Items in the Genotype
    for (int i = 0; i < genotype.size(); i++) {
      // Used a temp Item object to access a row's details, but executing get(i) for each element would be fine since it's also O(1)
      Item temp =  genotype.get(i);
      returnString += "Item number: " + temp.getItemNum() + " Value: " + temp.getValue() + " Cost: " + temp.getCost();
    }
    
    // Return the string
    return returnString;
  }
  
  
  /**
   * Print all of the information about a Genotype object to System.out. The details are listed one Item per line.
   */
  public void print() {    
    // Call toString() and print the result to System.out
    System.out.println(toString());
  }
}

