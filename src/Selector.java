import java.util.Iterator;
import java.util.Random;

public class Selector {
  
  
  public Population rolluletteWheelSelection(Population population, int count) {
    Population selectedPopulation = new Population(population.getGenotype());
    
    Random random = new Random();
    
    // Random value from min score to total sum of scores in population
    int rouletteValue = random.nextInt((population.getSumScores() - population.getMinScore()) + 1) + population.getMinScore();
    
    int countSelected = 0;
    int rouletteFitnessSum = 0;
    
    Iterator<Chromosome> iterator = population.iterator();
    
    while (countSelected<count) {
      Chromosome temp = iterator.next();
      rouletteFitnessSum += temp.fitnessScore;
      
      if (rouletteFitnessSum>=rouletteValue) {
        selectedPopulation.add(temp);
        // Get new random value from min score to total sum of scores in population
        rouletteValue = random.nextInt((population.getSumScores() - population.getMinScore()) + 1) + population.getMinScore();
        countSelected++;
      }
      
      // If we reached the end of the list, re-initialize iterator to start back at the first
      if (!iterator.hasNext())
        iterator = population.population.iterator();
    }
    
    return selectedPopulation;
  }
  
  public Population tournamentSelection(Population population, int kWays, int count) {
    Population selectedPopulation = new Population(population.getGenotype());
    
    int countSelected = 0;
    Random random = new Random();
    int randomIndex = 0;
    
    while (countSelected<count) {
      Population kWaysPopulation = new Population(population.getGenotype());
      
      for (int i=0; i<kWays; i++) {
        // Get random index 
        randomIndex = random.nextInt((population.size() - 1 - 0) + 1) + 0;
        kWaysPopulation.add(population.get(randomIndex));
      }
      kWaysPopulation.sortDescending();
      selectedPopulation.add(kWaysPopulation.get(0));
      countSelected++;
    }
    return selectedPopulation;
  }
  
  public Population tournamentSelection(Population population, int count) {
    int kWays = 3;
    return tournamentSelection(population, kWays, count);
  }
  
  public Population rankSelection(Population population, int count) {
    // For simplicity, limit count to population size. We could remove this requirement
    // by implementing while loop countSelected<count loop from rouletteWheelSelection
    if (count>population.size())
      throw new IllegalArgumentException("count should be smaller than population size.");
    
    Population selectedPopulation = new Population(population.getGenotype());
    
    Population temp = population;
    temp.sortDescending();
    
    for (int i=0; i<count; i++)
      selectedPopulation.add(temp.get(i));
    
    return selectedPopulation;
  }
}
