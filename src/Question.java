
public class Question {
  int questionNum;
  int section;
  int time;
  
  public Question(int questionNum, int section, int time) {
    this.questionNum = questionNum;
    this.section = section;
    this.time = time;    
  }
  
  public Question() {
    // Nothing
  }
  
  public int getQuestionNum() {
    return this.questionNum;
  }
  
  public int getSection() {
    return this.section;
  }
  
  public int getTime() {
    return this.time;
  } 
}
