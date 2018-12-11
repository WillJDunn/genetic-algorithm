
// Time compexity: O(g(pg + pg + p)) with g the number of generations, p the population size and g the size of the individuals (number of genes)
// Space complexity: O(p + 2pc + p-pc ) with p population size, c crossover rate, p - pc is survivors
public class GeneticAlgorithm {
  
  public GeneticAlgorithm() {
    // Initialize
  }
  
  public int algorithm1(Genotype genotype, int populationSize, double crossoverRate, double mutationRate, int selectBest, int bestScoreTarget, int printLevel) {
    
    // Generate first population (source population, completely random genes)
    Population population = new Population(genotype);
    population.generateRandom(populationSize);
    
    if (printLevel>0) {
      System.out.println("Source population:");
      System.out.println("Average score:" + population.getAverageScore()
                       + " Best score: " + population.getBestScore());
      System.out.println("Size: " + population.size());
    }
    
    Selector selector = new Selector();
    
    int generation = 1;
    int numberOfChildren = (int) (populationSize*crossoverRate);
    
    while (population.getBestScore()<bestScoreTarget) {
      
      if (printLevel>0)
        System.out.println("GENERATION " + generation + " best: " + population.getBestScore());
      
      // Parents 1 (roulette)
      Population parents1 = new Population(genotype);
      
      parents1 = selector.rolluletteWheelSelection(population, (numberOfChildren/2));
      
      if (printLevel>0) {
        System.out.println("  Parents1 (roulette):");
        System.out.println("    Average score:" + parents1.getAverageScore()
                        + " Best score: " + parents1.getBestScore()
                        + " Size: " + parents1.size());
      }
      
      // Parents 2 (Roulette)
      Population parents2 = new Population(genotype);
      parents2 = selector.rolluletteWheelSelection(population, numberOfChildren/2);
      
      if (printLevel>0) {
        System.out.println("  Parents2 (roulette):");
        System.out.println("    Average score:" + parents2.getAverageScore()
                        + " Best score: " + parents2.getBestScore()
                        + " Size: " + parents2.size());
      }
      
      // Children 1 (singlePointCrossover)
      Population children1 = new Population(genotype);
      children1 = parents1.generateChildren("singlePointCrossover");
      
      if (printLevel>0) {
        System.out.println("  Child population1 (single point crossover):");
        System.out.println("    Average score:" + children1.getAverageScore()
        + " Best score: " + children1.getBestScore()
        + " Size: " + children1.size());
      }
      
      
      // Children 2 (randomCrossover)
      Population children2 = new Population(genotype);
      children2 = parents2.generateChildren("randomCrossover");
      
      if (printLevel>0) {
        System.out.println("  Child population2 (random crossover):");
        System.out.println("    Average score:" + children2.getAverageScore()
                         + " Best score: " + children2.getBestScore()
                         + " Size: " + children2.size());
      }
      
      
      // Survivors: Best (rankedSelection)
      Population best = new Population(genotype);
      best = selector.rankSelection(population, selectBest);
      
      if (printLevel>0) {
        System.out.println("  Survivors " + selectBest + " best (ranked):");
        System.out.println("    Average score:" + best.getAverageScore()
                         + " Best score: " + best.getBestScore()
                         + " Size: " + best.size());
      }
      
      
      // Survivors: (roulette selection)
      Population survivors1 = new Population(genotype);
      survivors1 = selector.rolluletteWheelSelection(population, (populationSize - numberOfChildren - selectBest)/2);
      
      if (printLevel>0) {
        System.out.println("  Survivors (rouletteWheel):");
        System.out.println("    Average score:" + survivors1.getAverageScore()
                         + " Best score: " + survivors1.getBestScore()
                         + " Size: " + survivors1.size());
      }
      
      // Survivors: (tournamentSelection)
      Population survivors2 = new Population(genotype);
      survivors2 = selector.tournamentSelection(population, (populationSize - numberOfChildren - selectBest)/2);
      
      if (printLevel>0) {
        System.out.println("  Survivors (tournament):");
        System.out.println("    Average score:" + survivors2.getAverageScore()
                       + " Best score: " + survivors2.getBestScore()
                       + " Size: " + survivors2.size());
      }
      
      // New generation
      Population newGeneration = new Population(genotype);
      newGeneration.add(survivors1);
      newGeneration.add(survivors2);
      newGeneration.add(children1);
      newGeneration.add(children2);
      newGeneration.mutate(mutationRate);
      newGeneration.add(best);
      
      if (printLevel>0) {
        System.out.println("  New generation:");
        System.out.println("    Average score:" + newGeneration.getAverageScore()
                         + " Best score: " + newGeneration.getBestScore()
                         + " New generation size: " + newGeneration.size());
      }
      
      population = newGeneration;
      
      generation++;
    }
    
    if (printLevel>0) {
      System.out.println("Best solution:");
      genotype.printChromosomeGenotype(population.getBest());
    }
    return (generation-1);
  }
}
