
public class Chromosome implements Comparable<Chromosome> {
  
  public int[] genes;
  int fitnessScore;
  
  public Chromosome(int numberOfGenes) {
    genes = new int[numberOfGenes];
    fitnessScore = 0;
  }
  
  public Chromosome(int[] values) {
    genes = new int[values.length];
    fitnessScore = 0;
    for (int i=0; i<values.length; i++)
      genes[i] = values[i];
  }
  
  
  public void singlePointCrossover(Chromosome parent1, Chromosome parent2) {
    if (parent1.geneCount() != parent2.geneCount())
      throw new IllegalArgumentException("Parents should have equal geneCount.");
    
    int chromosomeSize = parent1.geneCount();
    int midPoint = chromosomeSize / 2;
    
    for (int i = 0; i<midPoint; i++)
      setGene(i,parent1.getGene(i));
    
    for (int i = midPoint; i<chromosomeSize; i++)
      setGene(i,parent2.getGene(i));
  }
  
  
  public void twoPointCrossover(Chromosome parent1, Chromosome parent2) {
    if (parent1.geneCount() != parent2.geneCount())
      throw new IllegalArgumentException("Parents should have equal geneCount.");
    
    int chromosomeSize = parent1.geneCount();
    int firstPoint = chromosomeSize / 3;
    int secondPoint = chromosomeSize / 3 * 2;
    
    for (int i = 0; i<firstPoint; i++)
      setGene(i,parent1.getGene(i));
    
    for (int i = firstPoint; i<secondPoint; i++)
      setGene(i,parent2.getGene(i));
    
    for (int i = secondPoint; i<chromosomeSize; i++)
      setGene(i,parent1.getGene(i));
  }
  
  public void threePointCrossover(Chromosome parent1, Chromosome parent2) {
    if (parent1.geneCount() != parent2.geneCount())
      throw new IllegalArgumentException("Parents should have equal geneCount.");
    
    int chromosomeSize = parent1.geneCount();
    int firstPoint = chromosomeSize / 4;
    int secondPoint = chromosomeSize / 4 * 2;
    int thirdPoint = chromosomeSize / 4 * 3;
    
    for (int i=0; i<firstPoint; i++)
      setGene(i,parent1.getGene(i));
    
    for (int i=firstPoint; i<secondPoint; i++)
      setGene(i,parent2.getGene(i));
    
    for (int i=secondPoint; i<thirdPoint; i++)
      setGene(i,parent1.getGene(i));
    
    for (int i=thirdPoint; i<chromosomeSize; i++)
      setGene(i,parent2.getGene(i));
  }
  
  public void randomCrossover(Chromosome parent1, Chromosome parent2) {
    if (parent1.geneCount() != parent2.geneCount())
      throw new IllegalArgumentException("Parents should have equal geneCount.");
    
    for (int i = 0; i<parent1.geneCount(); i++) {
      if (Math.random() < 0.5)
        setGene(i,parent1.getGene(i));
      else
        setGene(i,parent2.getGene(i));
    }
  }
  
  // Bit flip mutation
  public void mutate(double mutationRate) {
    for (int i=0; i<this.geneCount(); i++) {
      if (Math.random() <= mutationRate) {
        if (genes[i] == 0)
          genes[i] = 1;
        else
          genes[i] = 0;
      }
    }
  }
  
  public void setRandomGenes() {
    for (int i=0; i<geneCount(); i++) {
      genes[i] = (Math.random() < 0.5 ? 1 : 0);
    }
  }
  
  public int getGene(int index) {
    return genes[index];
  }
  
  public void setGene(int index, int value) {
    genes[index] = value;
  }
  
  public int getFitnessScore() {
    return fitnessScore;
  }
  
  public void setFitnessScore(int score) {
    fitnessScore = score;
  }
  
  public int geneCount() {
    return genes.length;
  }
  
  @Override
  public int compareTo(Chromosome other) {
    return Integer.compare(this.fitnessScore, other.fitnessScore);
  }
  
  public void print() {
    System.out.print("Fitness score: " + fitnessScore + " Genes: ");
    for (int i=0; i<this.geneCount(); i++) {
      System.out.print(genes[i] + " ");
    }
    System.out.println();
  }
}
