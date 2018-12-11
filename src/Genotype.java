import java.util.ArrayList;
import java.util.HashSet;

/**
 * Note: let's implement the fitness function here, and leave
 * the Chromosome as a generic class not interested in the
 * specific problem
 *
 */
public class Genotype {
  // Table of Genotype metadata
  // Table of questions, chapter section, estimated time
  public ArrayList<Question> genotype;
  
  public int countSections;
  public int idealAssignmentTime;
  public int totalTime;
  public double sectionTimeWeight;
  public double assignmentTimeWeight;
  
  public Genotype(double sectionTimeWeight, double assignmentTimeWeight) {
    genotype = new ArrayList<Question>();
    this.sectionTimeWeight = sectionTimeWeight;
    this.assignmentTimeWeight = assignmentTimeWeight;
  }
  
  public void add(int questionNum, int section, int time) {
    Question question = new Question(questionNum, section, time);
    genotype.add(question);
    setCountSections();
    setTotalTime();
  }
  
  public void setCountSections() {
    HashSet<Integer> set = new HashSet<>();
    
    for (int i=0; i<genotype.size(); i++)
      set.add(genotype.get(i).getSection());
    
    this.countSections = set.size();
  }
  
  public void setIdealAssignmentTime(int idealTime) {
    this.idealAssignmentTime = idealTime;
  }
  
  public void setTotalTime() {
    int time = 0;
    for (int i=0; i<genotype.size(); i++)
      time += genotype.get(i).getTime();
    
    totalTime = time;
  }
  
  public int getIdealSectionTime() {
    return idealAssignmentTime/countSections;
  }
  
  public int getIdealAssignmentTime() {
    return idealAssignmentTime;
  }
  
  public int getCountQuestions() {
    return genotype.size();
  }
  
  public int genesCount() {
    return genotype.size();
  }
  
  public int getCountSections() {
    return countSections;
  }
  
  public int getMaxScore() {
    return totalTime*totalTime;
  }
  
  // IF YOU CHANGE THIS, CHANGE printChromosome AS WELL
  public int calculateFitness(Chromosome chromosome) {
    int[] sections = new int[getCountSections()];
    int chromosomeTime = 0;
    
    for (int i=0; i<chromosome.geneCount(); i++)
      if (chromosome.getGene(i) == 1) {
        int geneTime = genotype.get(i).getTime();
        sections[genotype.get(i).getSection()-1] += geneTime;
        chromosomeTime += geneTime;
      }
    
    int sectionDeviation = 0;
    for (int i = 0; i<sections.length; i++)
      sectionDeviation += (Math.pow((getIdealSectionTime()-sections[i]),2));
    
    return (int) (getMaxScore()-(sectionTimeWeight*sectionDeviation + assignmentTimeWeight*(Math.pow(getIdealAssignmentTime()-chromosomeTime,2))));
  }
  
  public void printChromosomeGenotype(Chromosome chromosome) {      
    int[] sections = new int[getCountSections()];
    int chromosomeTime = 0;
    
    for (int i=0; i<chromosome.geneCount(); i++)
      if (chromosome.getGene(i) == 1) {
        // Specific stuff
        System.out.println("Question: " + genotype.get(i).getQuestionNum()
            + " Section: " + genotype.get(i).getSection()
            + " Time: " + genotype.get(i).getTime());
        // End specific stuff
        int geneTime = genotype.get(i).getTime();
        sections[genotype.get(i).getSection()-1] += geneTime;
        chromosomeTime += geneTime;
      }
        
    System.out.println("Time per section: ");
    for (int i = 0; i<sections.length; i++)
      System.out.println("Section " + (i+1) + ": " + sections[i]);
    
    System.out.println("Total time: " + chromosomeTime);
    
    System.out.println("Score: " + this.calculateFitness(chromosome));
  }
  
  public void print() {
    System.out.println("GENOTYPE:");
    System.out.println("Ideal Time: " + getIdealAssignmentTime() + " Ideal Time Per Section: " + getIdealSectionTime() + " Count Questions: " + getCountQuestions() + " Count Sections: " + getCountSections());
    Question temp = new Question();
    for (int i = 0; i < genotype.size(); i++) {
      temp =  genotype.get(i);
      System.out.println("Question number: " + temp.getQuestionNum() + " Section: " + temp.getSection() + " Time: " + temp.getTime());
    }
  }
}

