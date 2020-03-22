color TEXTBOX_BOX = color(250);
color TEXTBOX_EXPRESSION = color(50);
color TEXTBOX_ANSWER = color(20);
color TEXTBOX_OFF = color(190);

int maxLong = 12;

class TextBox{
  float textSizeExpression, textSizeAnswer;
  PVector pos, size;
  color c, cExpression, cAnswer;
  String labelExpression;
  String labelAnswer;
  boolean isAnswered;
  boolean isError;
  boolean isOff;

  TextBox(float xpos, float ypos, float xsize, float ysize){
    this.pos = new PVector(xpos, ypos);
    this.size = new PVector(xsize, ysize);
    this.c = TEXTBOX_BOX;
    this.labelExpression = "";
    this.labelAnswer = "0";
    this.textSizeExpression = this.size.y / 9;
    this.textSizeAnswer = this.size.y / 2.5;
    this.cExpression = TEXTBOX_EXPRESSION;
    this.cAnswer = TEXTBOX_ANSWER;
    this.isAnswered = false;
    this.isError = false;
    this.isOff = false;
  }

  public void display(){
    if(this.isOff){
      this.c = TEXTBOX_OFF;
    }else{
      this.c = TEXTBOX_BOX;
    }
    fill(this.c);
    rect(this.pos.x, this.pos.y, this.size.x, this.size.y);
    textAlign(RIGHT, CENTER);

    if(this.isError){
      this.cExpression = RED;
    } else {
      this.cExpression = TEXTBOX_EXPRESSION;
    }
    fill(this.cExpression);
    textSize(this.textSizeExpression);
    text(this.labelExpression, this.pos.x + this.size.x - 10, this.pos.y + this.size.y/3);

    fill(this.cAnswer);
    textSize(this.textSizeAnswer);
    if(this.labelAnswer.length() > 12){
      this.labelAnswer = this.labelAnswer.substring(0,12);
    }
    text(this.labelAnswer, this.pos.x + this.size.x - 10, this.pos.y + this.size.y - this.size.y/3);
  }

  public void add(String s){
    if(!this.isOff){
      if (this.labelAnswer.length() + s.length() <= maxLong) { // tambahin s.length gara-gara ada ans
        if (this.labelAnswer == "0" && s != ".")
          this.labelAnswer = s;
        else
          this.labelAnswer += s;
        this.isAnswered = false;
      }
    }
  }

  public void clear(){
    this.labelExpression = ""; 
    this.labelAnswer = "0";
    this.isError = false;
    this.isOff = false;
    this.clearMemory();
  }

  public void backspace(){
    if(this.labelAnswer.length()==1){
      this.labelAnswer = "0";
    }else{
      if (this.labelAnswer.charAt(this.labelAnswer.length() - 1) == 's'){
        if(this.labelAnswer.length() == 3){
          this.labelAnswer = "0";
        }else{
          this.labelAnswer = this.labelAnswer.substring(0,this.labelAnswer.length() - 3);
        }
      }else {
        this.labelAnswer = this.labelAnswer.substring(0,this.labelAnswer.length() - 1);
      }
    }
  } 

  public void pushMemory(){
    try {
        if (this.isAnswered) {
          memory.add(Float.parseFloat(this.labelAnswer));
          this.labelAnswer = "0";
          this.labelExpression = "";
        } else /* !this.isAnswered */ {
          throw new MemoryException("Not a Number");
        }
    } catch (Exception e) {
      this.labelExpression = e.getMessage();
      this.isError = true;
    }
  }

  public void pullMemory(){
    try{
      if (memory.size() != 0) {
        float f = memory.remove();
        if(f == int(f)){
          this.add(str(int(f)));
        } else {
          this.add(str(f));
        }
      } else {
        throw new MemoryException("Memory is empty"); 
      }
    } catch (MemoryException e){
      this.labelExpression = e.getMessage();
      this.isError = true;
    }
  }
  
  public void clearMemory() {
     while (memory.size() > 0) {
        memory.remove(); 
     }  
  }  

  public void off() {
      this.isOff = true;
      this.labelExpression = "";
      this.labelAnswer = "";
      lastAns = "";
      this.clearMemory();
  }

  public void eval() {
    if(!this.isOff){  
      this.isError = false;
      try{
        this.labelExpression = this.labelAnswer;
        // this.labelExpression.substring(0, maxLong);
        this.labelAnswer = String.valueOf(new ExpressionBuilder(this.labelAnswer).parse().solve());
        this.labelExpression += " = " + this.labelAnswer;
        lastAns = this.labelAnswer;
        if(this.labelAnswer.contains(".")&&((this.labelAnswer.charAt(0) >= '1' && this.labelAnswer.charAt(0)<='9')||(this.labelAnswer.charAt(0)=='-' && this.labelAnswer.charAt(1)!='0'))){
          int index = this.labelAnswer.indexOf(".");
          this.labelAnswer = this.labelAnswer.substring(0,min(this.labelAnswer.length(), index+5));
          float f = Float.parseFloat(this.labelAnswer);
          if (f == int(f)) { //mengecek apakah bisa menjadi integer atau tidak
            this.labelAnswer = str(int(f));
          }
        }
        this.isAnswered = true; 
      } catch (Exception e) {
        this.labelExpression = e.getMessage();
        this.isError = true;
      } 
    }
  }
}
