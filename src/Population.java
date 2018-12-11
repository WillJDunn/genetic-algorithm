import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Population {
  // We keep the ArrayList public so we can easily use iterator
  public ArrayList<Chromosome> population;
  public Genotype genotype;
  
  // Used for roulette selection
  public int minScore;
  public int sumScores;
  
  public Population(Genotype genotype){
    population = new ArrayList<Chromosome>();
    this.genotype = genotype;
    minScore = 0;
    sumScores = 0;
  }
  
  // If you update this, update add(Population population)
  public void add(Chromosome chromosome) {
    chromosome.setFitnessScore(genotype.calculateFitness(chromosome));
    population.add(chromosome);
    compareUpdateMinScore(chromosome.fitnessScore);
    addSumScores(chromosome.fitnessScore);
  }
  
  public void add(Population population) {
    // Since a population will already have fitness scores calculated we
    // can skip setFitnessScore. This is why we don't simply use add(Chromosome)
    for (int i=0; i<population.size(); i++) {
      this.population.add(population.get(i));
      compareUpdateMinScore(population.get(i).getFitnessScore());
      addSumScores(population.get(i).getFitnessScore());
    }
  }
  
  public Iterator<Chromosome> iterator() {
    Iterator<Chromosome> iterator = population.iterator();
    return iterator;
  }
  
  public void compareUpdateMinScore(int fitnessScore) {
    // If we have not had any chromosomes with fitnessScores minScore will be 0
    // In this case we should set minScore to this score
    if (this.minScore == 0 || fitnessScore > 0)
      setMinScore(fitnessScore);
    
    if (fitnessScore<this.minScore)
      setMinScore(fitnessScore);
  }
  
  public void setMinScore(int fitnessScore) {
    this.minScore = fitnessScore;
  }
  
  public int getMinScore() {
    return minScore;
  }
  
  public int getSumScores() {
    return this.sumScores;
  }
  
  public void addSumScores(int fitnessScore) {
    sumScores += fitnessScore;
  }
  
  public int size() {
    return this.population.size();
  }
  
  public void remove(int index) {
    boolean wasMin = (population.get(index).fitnessScore == minScore);
    population.remove(index);
    if (wasMin)
      calculateSetMinScore();
  }
  
  public void calculateSetMinScore() {
    setMinScore(0);
    
    for (Chromosome c : this.population)
      compareUpdateMinScore(c.getFitnessScore());
  }
  
  public void calculateSetSumScores() {
    this.sumScores = 0;
    
    for (Chromosome c : this.population)
      this.sumScores += c.getFitnessScore();
  }
  
  public Genotype getGenotype() {
    return this.genotype;
  }
  
  public Chromosome get(int index) {
    return population.get(index);
  }
  
  public void setFitnessScores() {
    for (Chromosome c : population)
      c.setFitnessScore(genotype.calculateFitness(c));      
  }
  
  public void sort() {
    Collections.sort(this.population);
  }
  
  public void sortDescending() {
    this.sort();
    Collections.reverse(this.population);
  }
  
  public void print() {
    for (Chromosome c : population)
      c.print();
  }
  
  public int chromosomeNumberOfGenes() {
    return this.getGenotype().genesCount();
  }
  
  public Population generateChildren(String crossoverMethod) {
    
    Population childPopulation = new Population(this.getGenotype());
    
    for (int i=0; i<this.size(); i+=2) {
      Chromosome child = new Chromosome(this.chromosomeNumberOfGenes());
      if (crossoverMethod == "singlePointCrossover")
        child.singlePointCrossover(this.get(i), this.get(i+1));
      else if (crossoverMethod == "twoPointCrossover")
        child.twoPointCrossover(this.get(i), this.get(i+1));
      else if (crossoverMethod == "threePointCrossover")
        child.threePointCrossover(this.get(i), this.get(i+1));
      else if (crossoverMethod == "randomCrossover")
        child.randomCrossover(this.get(i), this.get(i+1));
      else
        throw new IllegalArgumentException("Valid crossoverMethods are: singlePointCrossover, threePointCrossover, randomCrossover");
      
      childPopulation.add(child);
      
      Chromosome child2 = new Chromosome(this.chromosomeNumberOfGenes());
      if (crossoverMethod == "singlePointCrossover")
        child2.singlePointCrossover(this.get(i+1), this.get(i));
      else if (crossoverMethod == "twoPointCrossover")
        child.twoPointCrossover(this.get(i), this.get(i+1));
      else if (crossoverMethod == "threePointCrossover")
        child2.threePointCrossover(this.get(i+1), this.get(i));
      else if (crossoverMethod == "randomCrossover")
        child2.randomCrossover(this.get(i+1), this.get(i));
      else
        throw new IllegalArgumentException("Valid crossoverMethods are: singlePointCrossover, threePointCrossover, randomCrossover");
      
      childPopulation.add(child2);
    }
    return childPopulation;
  }
  
  public void generateRandom(int count) {
    for (int i=0; i<count; i++) {
      Chromosome chromosome = new Chromosome(this.chromosomeNumberOfGenes());
      chromosome.setRandomGenes();
      this.add(chromosome);
    }
  }
  
  public void mutate(double mutationRate) {
    for (Chromosome c : this.population) {
      c.mutate(mutationRate);
    }
  }
  
  public void mutate() {
    this.mutate(0.001);
  }
  
  public double getAverageScore() {
    return this.getSumScores()/this.size();
  }
  
  public int getBestScore() {
    return getBest().getFitnessScore();
  }
  
  public Chromosome getBest() {
    Population temp = this;
    temp.sortDescending();
    return temp.get(0);
  }
  
  public String listScores() {
    String listScores = new String();
    for (int i=0; i<this.size(); i++)
      listScores += this.get(i).getFitnessScore() + ",";
    
    // Remove trailing "," and return
    return listScores.replaceAll(",$", "");
  }
}