package app;

color TEXTBOX_BOX = color(250);
color TEXTBOX_EXPRESSION = color(50);
color TEXTBOX_ANSWER = color(20);

int maxLong = 10;

class TextBox{
  final int textSizeExpression, textSizeAnswer;
  PVector pos, size;
  color c, cExpression, cAnswer;
  String labelExpression;
  String labelAnswer;
  boolean isAnswered; 

  TextBox(float xpos, float ypos, float xsize, float ysize){
    this.pos = new PVector(xpos, ypos);
    this.size = new PVector(xsize, ysize);
    this.c = TEXTBOX_BOX;
    this.labelExpression = "";
    this.labelAnswer = "0";
    this.textSizeExpression = 20;
    this.textSizeAnswer = 70;
    this.cExpression = TEXTBOX_EXPRESSION;
    this.cAnswer = TEXTBOX_ANSWER;
    this.isAnswered = true;
  }

  public void display(){
    fill(this.c);
    rect(this.pos.x, this.pos.y, this.size.x, this.size.y);
    textAlign(RIGHT, CENTER);
    fill(this.cExpression);
    textSize(this.textSizeExpression);
    text(this.labelExpression, this.pos.x + this.size.x - 10, this.pos.y + this.size.y/3);

    fill(this.cAnswer);
    textSize(this.textSizeAnswer);
    text(this.labelAnswer, this.pos.x + this.size.x - 10, this.pos.y + this.size.y - this.size.y/3);
  }

  public void add(String s){
    if(this.isAnswered){
      this.labelAnswer = s;
      this.isAnswered = false;
    } else {
      if (this.labelAnswer.length + s.length <= maxLong) { // tambahin s.length gara-gara ada ans
        if (this.labelAnswer == "0")
          this.labelAnswer = s;
        else
          this.labelAnswer += s;
      }
    }
  }

  public void clear(){
    this.labelExpression = ""; 
    this.labelAnswer = "0";
  }

  public void backspace(){
    if(this.labelAnswer.length()==1){
      this.labelAnswer = "0";
    }else{
      if (this.labelAnswer.charAt(this.labelAnswer.length - 1) == "s"){
        if(this.labelAnswer.length()==3){
          this.labelAnswer = "0";
        }else{
          this.labelAnswer = this.labelAnswer.substring(0,this.labelAnswer.length() - 3);
        }
      }else {
        this.labelAnswer = this.labelAnswer.substring(0,this.labelAnswer.length() - 1);
      }
    }
  }

  public void eval(){
    this.labelExpression = this.labelAnswer;
    // this.labelAnswer = eval(this.labelAnswer);
    // try {
    //   Expression expr = new ExpressionBuilder(this.labelAnswer);
    //   return expr.eval();
    // } catch (ConversionException e) {
    //   throw e
    // }
    this.isAnswered = true;
  }
}