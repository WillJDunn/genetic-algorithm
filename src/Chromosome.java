
/**
 * Represents a subset of the Item objects contained in a Genotype. Collections of Chromosomes are maintained and stored within a Population object. Since a "solution" for a Genetic Algorithm is a collection of Items, a Chromosome can be thought of as a candidate solution.
 * Note: we did not add pointers to the Genotype or Population for performance of Population iteration. Since Chromosomes are only accessed from its Population and Genotype such pointers are not required nor advised.
 * 
 * @author willj
 */
public class Chromosome implements Comparable<Chromosome> {
  
  // The genes of a Chromosome are an array of length n where n is the number of Items in the Genotype. Array index n of genes relates to the nth Item in the Genotype arraylist. Array index values are either 0 or 1, with 0 meaning the related Item of the Genotype is included in the Chromosome, and 1 meaning the related Item of the Genotype is included in the Chromosome. 
  public int[] genes;
  
  // The Chromosome's fitnessScore is an int rating of how well the Chromosome (candidate solution) solves our problem. The fitnessScore is calculated by the Genotype. The Chromosome's fitnessScore is maintained by its Population - the Population will use the Genotype to calculate the fitnessScore and set it here.
  int fitnessScore;
  
  /**
   * Constructor
   * @param numberOfGenes
   *   (int) the number of genes of the Chromosome, which should be equal to the number of Items in the Genotype. Must be greater than 4, as a Genetic Algorithm should not be used for such a small set and some methods cannot produce valid output from so few Items.
   */
  public Chromosome(int numberOfGenes) {
    // If the number of genes is very few (less than 4) a Genetic Algorithm should not be used. Additionally, selection methods such as singlePointCrossover, twoPointCrossover, and threePointCrossover will not output the desired result since for example 2 items cannot be divided into thirds 
    if (numberOfGenes>4)
      throw new IllegalArgumentException("There must be at least 4 Items in the Genotype.");
    
    // Initialize genes as an array of length numberOfGenes
    genes = new int[numberOfGenes];
    
    // Initialize fitnessScore as 0. We cannot get the fitnessScore because we don't know what our Genotype is. We also don't know what our Items will be
    fitnessScore = 0;
  }
  
  /**
   * Constructor for when array of genes is provided
   * @param values
   *   (int[]) array with the values that the genes should be
   */
  public Chromosome(int[] values) {
    // If the number of genes is very few (less than 4) a Genetic Algorithm should not be used. Additionally, selection methods such as singlePointCrossover, twoPointCrossover, and threePointCrossover will not output the desired result since for example 2 items cannot be divided into thirds 
    if (values.length>4)
      throw new IllegalArgumentException("There must be at least 4 Items in the Genotype.");
    
    // Initialize genes as an array of length numberOfGenes
    genes = new int[values.length];
    
    // Initialize fitnessScore as 0. We cannot get the fitnessScore because we don't know what our Genotype is
    fitnessScore = 0;
    
    // Copy the provided values to gene array
    for (int i=0; i<values.length; i++)
      genes[i] = values[i];
  }
  
  /**
   * The genes of this Chromosome (child) are the first half gene array indexes of one parent and the second half gene array indexes of the other.
   * Note: an argument could be made regarding whether the crossover methods should be here or in the Population class. Since these methods involve more than one Chromosome, both of which are included in a Population, moving these methods to Population could make sense. For now we will keep them in this Chromosome class.
   * @param parent1
   *   (Chromosome) the parent Chromosome whose genes will supply the first half of this Chromosome's genes. The first half of parent1's genes will be copied to the first half of this Chromosome's genes
   * @param parent2
   *   (Chromosome) the parent Chromosome whose genes will supply the second half of this Chromosome's genes. The second half of parent2's genes will be copied to the second half of this Chromosome's genes
   */
  public void singlePointCrossover(Chromosome parent1, Chromosome parent2) {
    // We get and store the Chromosome length since we use it multiple times
    int chromosomeSize = this.geneCount();
    
    // If either parent does not have the same number of Chromosomes as this Chromosome throw illegal argument exception
    if (parent1.geneCount() != chromosomeSize || parent2.geneCount() != chromosomeSize)
      throw new IllegalArgumentException("Parents should have equal geneCount.");
    
    // Establish a mid point for where we will start and stop copying from each parent. Since this is an int fractions will be rounded
    int midPoint = chromosomeSize / 2;
    
    // Copy gene[0] to gene[midPoint-1] from parent1 to this Chromosome
    for (int i = 0; i<midPoint; i++)
      setGene(i,parent1.getGene(i));
    
    // Copy gene[midPoint] to the end of the gene array from parent2 to this Chromosome
    for (int i = midPoint; i<this.geneCount(); i++)
      setGene(i,parent2.getGene(i));
  }
  
  /**
   * The genes of this Chromosome (child) are the first third of the gene array indexes of parent1, the second third of the gene array indexes of parent2, and the third third of the gene array indexes of parent1
   * Typically 2 parent Chromsomes are used to produce 2 child Chromosomes with parent order reversed, this being called twice as in twoPointCrossover(p,q) twoPointCrossover(q,p)
   * Note: an argument could be made regarding whether the crossover methods should be here or in the Population class. Since these methods involve more than one Chromosome, both of which are included in a Population, moving these methods to Population could make sense. For now we will keep them in this Chromosome class.
   * @param parent1
   *   (Chromosome) the parent Chromosome whose genes will supply the first third and third third of this Chromosome's genes. The first third of parent1's genes will be copied to the first third of this Chromosome's genes and the second third of parent1's genes will be copied to the third third of this Chromosome's genes
   * @param parent2
   *   (Chromosome) the parent Chromosome whose genes will supply the second third of this Chromosome's genes. The second third of parent2's genes will be copied to the second third of this Chromosome's genes
   */
  public void twoPointCrossover(Chromosome parent1, Chromosome parent2) {
    // We get and store the Chromosome length since we use it multiple times
    int chromosomeSize = this.geneCount();
    
    // If either parent does not have the same number of Chromosomes as this Chromosome throw illegal argument exception
    if (parent1.geneCount() != chromosomeSize || parent2.geneCount() != chromosomeSize)
      throw new IllegalArgumentException("Parents should have equal geneCount.");
    
    // Establish mid points for where we will start and stop copying from each parent. Since these are ints fractions are rounded
    int firstPoint = chromosomeSize / 3;
    int secondPoint = chromosomeSize / 3 * 2;
    
    // Copy gene[0] to gene[firstPoint-1] from parent1 to this Chromosome
    for (int i = 0; i<firstPoint; i++)
      setGene(i,parent1.getGene(i));
    
    // Copy gene[firstPoint] to gene[secondPoint-1] from parent2 to this Chromosome
    for (int i = firstPoint; i<secondPoint; i++)
      setGene(i,parent2.getGene(i));
    
    // Copy gene[secondPoint] to the end of the gene array from parent1 to this Chromosome
    for (int i = secondPoint; i<chromosomeSize; i++)
      setGene(i,parent1.getGene(i));
  }  
  
  /**
   * The genes of this Chromosome (child) are the first fourth of the gene array indexes of parent1, the second fourth of the gene array indexes of parent2, the third fourth of the gene array indexes of parent1, and the fourth fourth of the gene array indexes of parent2
   * Typically 2 parent Chromsomes are used to produce 2 child Chromosomes with parent order reversed, this being called twice as in threePointCrossover(p,q) threePointCrossover(q,p)
   * Note: an argument could be made regarding whether the crossover methods should be here or in the Population class. Since these methods involve more than one Chromosome, both of which are included in a Population, moving these methods to Population could make sense. For now we will keep them in this Chromosome class.
   * @param parent1
   *   (Chromosome) the parent Chromosome whose genes will supply the first fourth and second fourth of this Chromosome's genes. The first fourth of parent1's genes will be copied to the first fourth of this Chromosome's genes and the third fourth of parent1's genes will be copied to the third fourth of this Chromosome's genes
   * @param parent2
   *   (Chromosome) the parent Chromosome whose genes will supply the second fourth and fourth fourth of this Chromosome's genes. The second fourth of parent2's genes will be copied to the second fourth of this Chromosome's genes and the fourth fourth of parent2's genes will be copied to the fourth fourth of this Chromosome's genes
   */
  public void threePointCrossover(Chromosome parent1, Chromosome parent2) {
    // We get and store the Chromosome length since we use it multiple times
    int chromosomeSize = this.geneCount();
    
    // If either parent does not have the same number of Chromosomes as this Chromosome throw illegal argument exception
    if (parent1.geneCount() != chromosomeSize || parent2.geneCount() != chromosomeSize)
      throw new IllegalArgumentException("Parents should have equal geneCount.");
    
    // Establish mid points for where we will start and stop copying from each parent. Since these are ints fractions are rounded
    int firstPoint = chromosomeSize / 4;
    int secondPoint = chromosomeSize / 4 * 2;
    int thirdPoint = chromosomeSize / 4 * 3;
    
    // Copy gene[0] to gene[firstPoint-1] from parent1 to this Chromosome
    for (int i=0; i<firstPoint; i++)
      setGene(i,parent1.getGene(i));
    
    // Copy gene[firstPoint] to gene[secondPoint-1] from parent2 to this Chromosome
    for (int i=firstPoint; i<secondPoint; i++)
      setGene(i,parent2.getGene(i));
    
    // Copy gene[secondPoint] to gene[thirdPoint-1] from parent1 to this Chromosome
    for (int i=secondPoint; i<thirdPoint; i++)
      setGene(i,parent1.getGene(i));
    
    // Copy gene[thirdPoint] to the end of the gene array from parent2 to this Chromosome
    for (int i=thirdPoint; i<chromosomeSize; i++)
      setGene(i,parent2.getGene(i));
  }
  
  /**
   * The genes of this Chromosome (child) are randomly selected from the genes of the 2 parents. For each gene index of this Chromosome one of the parents will be chosen at random and the gene index value will be copied from that parent.
   * Note: an argument could be made regarding whether the crossover methods should be here or in the Population class. Since these methods involve more than one Chromosome, both of which are included in a Population, moving these methods to Population could make sense. For now we will keep them in this Chromosome class.
   * @param parent1
   *   (Chromosome) the parent Chromosome whose genes will supply some of this Chromosome's genes
   * @param parent2
   *   (Chromosome) the parent Chromosome whose genes will supply some of this Chromosome's genes
   */
  public void randomCrossover(Chromosome parent1, Chromosome parent2) {
    // We get and store the Chromosome length since we use it multiple times
    int chromosomeSize = this.geneCount();
    
    // If either parent does not have the same number of Chromosomes as this Chromosome throw illegal argument exception
    if (parent1.geneCount() != chromosomeSize || parent2.geneCount() != chromosomeSize)
      throw new IllegalArgumentException("Parents should have equal geneCount.");
    
    // Iterate through each gene of this Chromosome, choose a parent at random and the value at that gene's index from that parent to this Chromosome
    for (int i = 0; i<chromosomeSize; i++) {
      // Randomly choose a parent with 50% chance each
      if (Math.random() < 0.5)
        // parent1 is chosen, copy gene(i) from the parent to gene(i) of this Chromosome
        setGene(i,parent1.getGene(i));
      else
        // parent2 is chosen, copy gene(i) from the parent to gene(i) of this Chromosome
        setGene(i,parent2.getGene(i));
    }
  }
  
  /**
   * We iterate through every gene of this Chromosome and flip its value (from 1 (included) to 0 (not included) or vice versa) with a chance mutationRate. It performs bit flip mutation.
   * This helps us escape from a local maxima. If our Chromosomes have converged and become very similar but a better solution could be achieved, making some changes to some Chromosomes may result in a better solution. 
   * @param mutationRate
   *   (double) the chance with which a gene's value will flip. Values between 0.001 – 0.05 are recommended
   */
  public void mutate(double mutationRate) {
    // Iterate through each gene of the Chromosome and bit flip with a chance mutationRate
    for (int i=0; i<this.geneCount(); i++) {
      // Generate a random double between 0 and 1 and compare it to mutationRate. If the randomly generated value is 
      if (Math.random() <= mutationRate) {
        if (genes[i] == 0)
          genes[i] = 1;
        else
          genes[i] = 0;
      }
    }
  }
  
  /**
   * Sets the values of genes randomly. For each gene there is a 50% chance that it will be set to 1 (chosen) and a 50% chance it will be set to 0 (not chosen)
   */
  public void setRandomGenes() {
    // For each gene generate a random double between 0 and 1. If the randomly generated value is < 0.5 we set the bit value to 1 (included). If the randomly generated value is >.5 we set the bit value to 0 (not included)
    for (int i=0; i<geneCount(); i++) {
      genes[i] = (Math.random() < 0.5? 1 : 0);
    }
  }
  
  /**
   * Returns the gene value (0 or 1) at a specified gene array index
   * @param index
   *   (int) the gene array index to get the value of
   * @return
   *   (int) the gene value (0 or 1) at the specified index
   */
  public int getGene(int index) {
    return genes[index];
  }
  
  /**
   * Sets the gene value at a specified gene array index
   * @param index
   *   (int) the gene index that will be modified
   * @param value
   *   (int) the gene value that will be set
   */
  public void setGene(int index, int value) {
    genes[index] = value;
  }
  
  /**
   * Getter for the fitnessScore.
   * The Chromosome's fitnessScore is an int rating of how well the Chromosome (candidate solution) solves our problem. The fitnessScore is calculated by the Genotype. The Chromosome's fitnessScore is maintained by its Population - the Population will use the Genotype to calculate the fitnessScore and set it here.
   * @return
   *   (int) the Chromosome's fitnessScore
   */
  public int getFitnessScore() {
    return fitnessScore;
  }
  
  /**
   * Setter for the fitnessScore
   * The Chromosome's fitnessScore is an int rating of how well the Chromosome (candidate solution) solves our problem. The fitnessScore is calculated by the Genotype. The Chromosome's fitnessScore is maintained by its Population - the Population will use the Genotype to calculate the fitnessScore and set it here.
   * @param score
   *   (int) the value that fitnessScore will be set to
   */
  public void setFitnessScore(int score) {
    fitnessScore = score;
  }
  
  /**
   * Getter for the number of genes in the Chromosome (genes.length)
   * @return
   *   (int) the number of genes that are in the Chromosome (genes.length)
   */
  public int geneCount() {
    return genes.length;
  }
  
  /**
   * compareTo is overrided so that Chromosomes are compared to each other based on their fitnessScore.
   * @param
   *   (Chromosome) the Chromosome that this Chromosome's fitnessScore will be compared to
   * @return
   *   (int) if this Chromosome's fitnessScore is larger than the other (parameter supplied) Chromosome's this will return a positive number. If this Chromosome's fitnessScore is smaller than the other Chromosome's this will return a negative number. If the fitnessScores of the two Chromosomes are equal this will return 0
   */
  @Override
  public int compareTo(Chromosome other) {
    // Compare the fitnessScores of this Chromosome and the other Chromosome
    return Integer.compare(this.fitnessScore, other.fitnessScore);
  }
  
  /**
   * Returns a String with all of the information about a Chromosome object. It lists the fitnessScore, followed by a space delimited list of the gene values
   * @return
   *   A String containing all of the information about the Chromosome.
   */
  @Override
  public String toString() {
    // We will add all information to this string then print and return it
    String returnString = "";
    
    // Add the fitnessScore to the returnString. Also add a header for the list of genes values
    returnString += "Fitness score: " + fitnessScore + " Genes: ";
    
    // Iterate through each gene and add it to the returnString
    for (int i=0; i<this.geneCount(); i++) {
      returnString += genes[i] + " ";
    }
    
    // Return the string
    return returnString;
  }
  
  /**
   * Prints all of the information about a Chromosome object to System.out. It lists the fitnessScore, followed by a space delimited list of the gene values
   */
  public void print() {
    // Call toString() and print the result to System.out
    System.out.println(toString());
  }
}
