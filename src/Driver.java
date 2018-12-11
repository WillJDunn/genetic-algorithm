

public class Driver {
  public static void main(String[] args) {
    Driver me = new Driver();
    me.doIt();
  }
  
  
  public void doIt() {
    algorithm1AverageGenerations();
  }
  
  public void runAlgorithm1Printing() {
    Genotype Chapter4 = makeChapter4();
    
    int perfectScore = getPerfectScore();
    System.out.println("Perfect score: " + perfectScore);
    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
    geneticAlgorithm.algorithm1(Chapter4, 500, 300, 0.05, 3, perfectScore-10, 3);
  }
  
  public void algorithm1AverageGenerations() {
    Genotype Chapter4 = makeChapter4();
    
    int perfectScore = getPerfectScore();
    System.out.println("Perfect score: " + perfectScore);
    
    
    int countIterations = 1000;
    int countGenerations = 0;
    int worstCase = 0;
    for (int i=0; i<countIterations; i++) {
      GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
      int generations = geneticAlgorithm.algorithm1(Chapter4, 500, 300, 0.001, 3, perfectScore-10, 0);
      countGenerations += generations;
      if (generations>worstCase)
        worstCase = generations;
    }      
    
    System.out.println ("Average number of generations required: " + (countGenerations/countIterations));
    System.out.println ("Number of generations in worst case: " + (worstCase));
  }
  
  public void printPerfectScore() {
    System.out.println(getPerfectScore());
  }
  
  
  // O(2^n)
  // 1,048,576 operations
  public int getPerfectScore() {
    Genotype genotype = makeChapter4();
    
    int maxScore = 0;
    
    for (int i = 0; i < 2; i++)
      for (int j = 0; j < 2; j++)
        for (int k = 0; k < 2; k++)
          for (int l = 0; l < 2; l++)
            for (int m = 0; m < 2; m++)
              for (int n = 0; n < 2; n++)
                for (int o = 0; o < 2; o++)
                  for (int p = 0; p < 2; p++)
                    for (int q = 0; q < 2; q++)
                      for (int r = 0; r < 2; r++)
                        for (int s = 0; s < 2; s++)
                          for (int t = 0; t < 2; t++)
                            for (int u = 0; u < 2; u++)
                              for (int v = 0; v < 2; v++)
                                for (int w = 0; w < 2; w++)
                                  for (int x = 0; x < 2; x++)
                                    for (int y = 0; y < 2; y++)
                                      for (int z = 0; z < 2; z++)
                                        for (int a = 0; a < 2; a++)
                                          for (int b = 0; b < 2; b++) {
                                            int[] bitstring = new int[] {i, j,  k,  l,  m,  n,  o,  p,  q,  r,  s,  t,  u,  v,  w,  x,  y,  z,  a,  b};
                                            Chromosome c = new Chromosome(bitstring);
                                            int score = genotype.calculateFitness(c);
                                            if (score > maxScore)
                                              maxScore = score;
                                          }
    return maxScore;
  }
  
  
  public Genotype makeChapter4() {
    Genotype Chapter4 = new Genotype(0.5, 0.5);

    Chapter4.setIdealAssignmentTime(120);
    
    Chapter4.add(1,1,12);
    Chapter4.add(2,1,11);
    Chapter4.add(3,1,23);
    Chapter4.add(4,1,9);
    Chapter4.add(5,1,14);
    Chapter4.add(6,2,5);
    Chapter4.add(7,2,14);
    Chapter4.add(8,2,21);
    Chapter4.add(9,3,12);
    Chapter4.add(10,3,5);
    Chapter4.add(11,3,23);
    Chapter4.add(12,3,27);
    Chapter4.add(13,3,8);
    Chapter4.add(14,4,19);
    Chapter4.add(15,4,7);
    Chapter4.add(16,4,27);
    Chapter4.add(17,4,11);
    Chapter4.add(18,4,22);
    Chapter4.add(19,4,21);
    Chapter4.add(20,4,22);
    
    return Chapter4;
  }
}
