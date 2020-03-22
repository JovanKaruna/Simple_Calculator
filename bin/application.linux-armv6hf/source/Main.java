import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.LinkedList; 
import java.util.Queue; 
import java.util.HashMap; 
import java.util.HashMap; 
import java.util.LinkedList; 
import java.util.Queue; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Main extends PApplet {




//final PVector aspectRatio = new PVector(9, 16);
//final float screenSize = 48;
View view;
String lastAns = "";
Queue<Float> memory;

public void setup(){
  // WEB:
  //size(aspectRatio.x*screenSize, aspectRatio.y*screenSize);
  
  // LOCAL:
   
  
  // ANDROID:
  //surface.setSize((int)Math.floor(aspectRatio.x*screenSize), (int)Math.floor(aspectRatio.y*screenSize));
  //surface.setLocation(200,50);
  //surface.setResizable(true);
  
  textAlign(CENTER, CENTER);
  noStroke();
  
  view = new View();
  memory = new LinkedList<Float>();
  // memory.add(1);
  // System.out.println(memory.peek());
}

public void draw() {
  view.display();
}
class AC implements Function{
  public void go(){
    view.textBox.clear();
  }
}
class AddExpression extends BinaryExpression{
  AddExpression(Expression front, Expression end){
    super(front, end);
  }

  public float solve() throws Exception{
    try{
      float ans = this.front.solve() + this.end.solve();
      if(ans > pow(10,12)-1 || ans < -pow(10,11)+1){
        throw new OverflowException();
      } else {
        return ans;
      }
    } catch (Exception e){
      throw e;
    }
  }
}
class ArithmeticException extends Exception {
  ArithmeticException(String msg){
    super(msg);
  }
}
abstract class BinaryExpression implements Expression{
  Expression front, end;
  BinaryExpression(Expression front, Expression end){
    this.front = front;
    this.end = end;
  }
}
int BTN_DEFAULT = color(245, 190, 25); // Orange
int BTN_PRESSED = color(230, 170, 20); // dark Orange
int BTN_HOVER = color(250, 200, 30); // light Orange

int NUMBTN_DEFAULT = color(250);
int NUMBTN_PRESSED = color(240);
int NUMBTN_HOVER = color(255);

abstract class Button {
  String label; //angka, simbol atau huruf
  PVector pos, size; 
  int c, cDefault, cPressed, cHover; //warna label dan tombol
  float roundness;
  float btnTextSize;
  boolean isPressed;

  // constructor
  Button(String labelB, float xpos, float ypos, float widthB, float heightB) {
    this(labelB, xpos, ypos, widthB, heightB, 10);
  }

  // constructor (fully customize)
  Button(String labelB, float xpos, float ypos, float widthB, float heightB, float roundnessB) {
    this.label = labelB;
    this.pos = new PVector(xpos, ypos);
    this.size = new PVector(widthB, heightB);
    this.roundness = roundnessB;
    this.isPressed = false;
    this.cDefault = BTN_DEFAULT;
    this.cPressed = BTN_PRESSED;
    this.cHover = BTN_HOVER;
    this.btnTextSize = min(this.size.x, this.size.y)/2.5f;
  }
  
  public void display() {
    if(this.onHover()){
        if(mousePressed){
          // biar jalannya cuma 1x
          if(!isPressed){
            this.c = cPressed;
            this.onClick();
          }
          this.isPressed = true;
        } else {
          this.isPressed = false;
          this.c = cHover;
        }
    } else {
      this.c = cDefault;
    }

    textSize(this.btnTextSize);
    textAlign(CENTER, CENTER);
    // button
    fill(this.c);
    rect(this.pos.x, this.pos.y, this.size.x, this.size.y, this.roundness);
    // label
    fill(BLACK);
    text(this.label, this.pos.x + (this.size.x / 2), this.pos.y + (this.size.y / 2));
  }
  
  public boolean onHover() {
    return (mouseX > this.pos.x && mouseX < (this.pos.x + this.size.x) &&
            mouseY > this.pos.y && mouseY < (this.pos.y + this.size.y));
  }

  abstract protected void onClick();
}


// create array of buttons using Builder Pattern
class ButtonBuilder{
  PVector pos, size;
  PVector boxSize;

  int colSize, rowSize;
  float pad;
  Button[][] buttons;
  String[] labels, ops, nums;
  HashMap<String, Function> function;
  

  ButtonBuilder(float xpos, float ypos, float widthB, float heightB){
    this(xpos, ypos, heightB, widthB, 4, 0.1f*heightB);
  }
  ButtonBuilder(float xpos, float ypos, float widthB, float heightB, int _colSize, float pad){
    this.colSize = _colSize;
    this.rowSize = 1;
    this.pad = pad;
    this.boxSize = new PVector(widthB, heightB);
    this.pos = new PVector(xpos, ypos);
    this.size = calcBtnSize();
    this.function = new HashMap<String, Function>();
  }

  public ButtonBuilder setLabel(String[] strings){
    this.labels = strings;
    this.rowSize = PApplet.parseInt((this.labels.length-1)/this.colSize)+1;
    this.size = calcBtnSize();
    return this;
  }

  public ButtonBuilder setOps(String[] ops){
    this.ops = ops;
    return this;
  }

  public ButtonBuilder setNumbers(String[] nums){
    this.nums = nums;
    return this;
  }

  public ButtonBuilder setFunction(String fname, Function func){
    this.function.put(fname, func);
    return this;
  }

  public Button[][] build(){
    this.buttons = new Button[this.rowSize][this.colSize];
    int i = 0;
    int j = 0;
    for(String s: this.labels){
      if(this.isNumber(s)){
        this.buttons[i][j] = new NumberButton(s, this.pos.x + this.size.x*j + this.pad*j, this.pos.y + this.size.y*i + this.pad*i, this.size.x, this.size.y);
      } else if(this.isOps(s)){
        this.buttons[i][j] = new NonFunctionButton(s, this.pos.x + this.size.x*j + this.pad*j, this.pos.y + this.size.y*i + this.pad*i, this.size.x, this.size.y);
      } else {
        this.buttons[i][j] = new FunctionButton(s, this.pos.x + this.size.x*j + this.pad*j, this.pos.y + this.size.y*i + this.pad*i, this.size.x, this.size.y, this.function.get(s));
      }
      j++;
      if(j == this.colSize){
        j = 0;
        i++;
      }
    }
    return this.buttons;
  }

  private PVector calcBtnSize(){
    return new PVector((this.boxSize.x-(this.pad*(this.colSize-1)))/this.colSize, (this.boxSize.y-(this.pad*(this.rowSize-1)))/this.rowSize);
  }

  private boolean isNumber(String s){
    for(String num: this.nums){
      if(s.equals(num)){
        return true;
      }
    }
    return false;
  }

  private boolean isOps(String s){
    for(String op: this.ops){
      if(s.equals(op)){
        return true;
      }
    }
    return false;
  }
}
class C implements Function{
  public void go(){
    view.textBox.backspace();
  }
}
class ConversionException extends Exception {
  ConversionException(){
    this("");
  }
  ConversionException(String msg){
    this.message = msg;
  }
}
class DivideExpression extends BinaryExpression {
  DivideExpression(Expression front, Expression end){
    super(front, end);
  }

  public float solve() throws Exception{
    try{
      float ans = this.end.solve();
      if(ans == 0){
        throw new ZeroDivisionException();
      }
    
      float answer = this.front.solve() / this.end.solve();
      if(answer > pow(10,12)-1 || answer < -pow(10,11)+1){
        throw new OverflowException();
      }
  
      return answer;
    } catch (Exception e){
      throw e; 
    }
  }
}
class Equal implements Function{
  public void go(){
    view.textBox.eval();
  }
}
class Exception extends Throwable{
  String message;
  Exception(){
    this("");
  }
  Exception(String msg){
    this.message = msg;
  }
  public String getMessage(){
    return this.message;
  }
}
interface Expression {
  public abstract float solve() throws Exception;
}


class ExpressionBuilder{
  String input;
  String[] oprPrio;
  HashMap<String, Expression> opr;
  int i;

  ExpressionBuilder(String s){
    this.input = s;
    // dari prio terendah
    this.oprPrio = new String[]{"+", "-", "x", "÷","%", "^", "√", "&"};
    i = 0;
  }

  public Expression parse() throws Exception{
    // preprocess bedain - unary dengan binary polanya yaitu
    // opr + "-" + angka : unary, diubah jadi &
    for(int i = 0; i < this.input.length()-2; ++i){
      char c1 = this.input.charAt(i);
      char c2 = this.input.charAt(i+1);
      char c3 = this.input.charAt(i+2);
      if(c2 == '-' && ('0' <= c3 && c3 <= '9' || c3 == '√' || c3 == 'a')){
        for(String opr: oprPrio){
          if(c1 == opr.charAt(0)){
            this.input = this.input.substring(0, i+1) + "&" + this.input.substring(i+2);
          }
        }
      }
    }
    if(this.input.charAt(0) == '-' && ('0' <= this.input.charAt(1) && this.input.charAt(1) <= '9' || this.input.charAt(1) == '√' || this.input.charAt(1) == 'a')){
      this.input = this.input.substring(0, 0) + "&" + this.input.substring(1);
    }
    this.input = this.input.replace("-&", "+");
    System.out.println(this.input);

    // rekursif
    try{
      return this.parseUtil(this.input);
    } catch (ConversionException e){
      throw e;
    }
  }

  // parse first number from the string
  private float getNextNumber(String input) throws ConversionException{
    boolean isFloat = false;
    // includes negative (if any)
    String s = input.substring(0, 1);
    for(char c: input.substring(1).toCharArray()){
      if(('0' <= c && c <= '9') || c == '.'){
        if(c == '.'){
          if(isFloat){
            throw new ConversionException("Invalid decimal number");
          }
          isFloat = true;
        }
        s += str(c);
      } else {
        if(s.length() == 1){
          throw new ConversionException("Invalid number");
        }
        return PApplet.parseFloat(s);
      }
    }
    return PApplet.parseFloat(s);
  }

  private boolean endsWithZeros(String s){
    int x = s.indexOf(".");
    if(x == -1) return false;
    for(char c: s.substring(x+1).toCharArray()){
      if(c != '0'){
        return false;
      }
    }
    return true;
  }

  private Expression parseUtil(String inp) throws ConversionException{
    i++;

    // basis
    if(inp.length() == 0){
      throw new ConversionException("Expression incomplete");
    }
    
    if(!Float.isNaN(PApplet.parseFloat(inp))){
      return new TerminalExpression(PApplet.parseFloat(inp));
    }
    //System.out.println(inp.substring(2));
    if(inp.charAt(0) == '&'){
      
      if(!Float.isNaN(PApplet.parseFloat(inp.substring(1))) || this.endsWithZeros(inp.substring(1))){
        return new NegativeExpression(new TerminalExpression(PApplet.parseFloat(inp.substring(1))));
      } else if (inp.charAt(1) == '√'){
        if(!Float.isNaN(PApplet.parseFloat(inp.substring(2))) || this.endsWithZeros(inp.substring(2))){
          return new NegativeExpression(new RootExpression(new TerminalExpression(PApplet.parseFloat(inp.substring(2)))));
        }
      }
    }
    if(inp.equals("ans")){
      if(lastAns.length() == 0){
        throw new ConversionException("Ans is empty");
      } else {
        return new TerminalExpression(PApplet.parseFloat(lastAns));
      }
    } else if(inp.equals("&ans")){
      if(lastAns.length() == 0){
        throw new ConversionException("Ans is empty");
      } else {
        return new NegativeExpression(new TerminalExpression(PApplet.parseFloat(lastAns)));
      }
    }

    // rekursif
    for(String opr: this.oprPrio){
      int len = split(inp, opr)[0].length();
      // no opr found, next
      if(len == inp.length()) continue;

      String front = inp.substring(0,len);
      String end = inp.substring(len+1, inp.length());
        
      if(opr.equals("x")){
        if(front.length() == 0  || end.length() == 0){
          throw new ConversionException("Need both side for multiplication");
        }
        return new MultiplyExpression(this.parseUtil(front), this.parseUtil(end));
      
      } else if (opr.equals("÷")){
        if(front.length() == 0  || end.length() == 0){
          throw new ConversionException("Need both side for multiplication");
        }
        return new DivideExpression(this.parseUtil(front), this.parseUtil(end));

      } else if (opr.equals("+")){
        if(front.length() == 0 || end.length() == 0){
          throw new ConversionException("Need both side for addition");
        }
        return new AddExpression(this.parseUtil(front), this.parseUtil(end));

      } else if (opr.equals("-")){
        if(front.length() == 0 || end.length() == 0){
          throw new ConversionException("Need both side for subtraction");
        }
        return new SubtractExpression(this.parseUtil(front), this.parseUtil(end));
      
      } else if(opr.equals("√")){
        if(front.length() != 0 && front.charAt(0) != '&'){
          throw new ConversionException("Left side of root operator must be empty");
        }
        if(end.length() == 0){
          throw new ConversionException("Right side of root operator must not be empty");
        }
        try{
          return new RootExpression(new TerminalExpression(this.getNextNumber(end)));
        } catch (ConversionException e){
          throw e;
        }

      } else if(opr.equals("^")){
        if(front.length() == 0 || end.length() == 0){
          throw new ConversionException("Need both side for addition");
        }
        return new PowerExpression(this.parseUtil(front), this.parseUtil(end));

      } else if (opr.equals("%")){
        if(front.length() == 0  || end.length() == 0){
          throw new ConversionException("Need both side for multiplication");
        }
        return new ModuloExpression(this.parseUtil(front), this.parseUtil(end));

      } else if (opr.equals("&")){
        if(front.length() != 0){
          throw new ConversionException("Left side of unary negative must be empty");
        }
        try{
          return new NegativeExpression(new TerminalExpression(this.getNextNumber(end)));
        } catch (ConversionException e){
          throw e;
        }
      }
    }
    throw new ConversionException("Expression incomplete");
  }
}
interface Function{
  public void go();
}
// using Strategy Pattern https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
class FunctionButton extends Button{
    Function function;
    public FunctionButton(String labelB, float xpos, float ypos, float widthB, float heightB, Function func){
      super(labelB, xpos, ypos, widthB, heightB);
      this.function = func;
    }

    protected void onClick(){
       this.function.go();
    }
}
 
 

class MC implements Function{
  public void go(){
    view.textBox.pushMemory();
  }
}
class MR implements Function{
  public void go(){
    view.textBox.pullMemory();
  }
}
class MemoryException extends Exception {
  MemoryException(String msg){
    super(msg);
  }
}
class ModuloExpression extends BinaryExpression {
  ModuloExpression(Expression front, Expression end){
    super(front, end);
  }
  public float solve() throws Exception{
    try{
      float ans = this.end.solve();
      if(ans == 0){
        throw new ZeroDivisionException();
      } else {
        float answer = this.front.solve() % this.end.solve();
        if(answer > pow(10,13)-1 || answer < -pow(10,13)+1){
          throw new OverflowException();
        } else {
          return answer;
        } 
      }
    } catch (Exception e){
      throw e;
    } 
  }
}
class MultiplyExpression extends BinaryExpression {
  MultiplyExpression(Expression front, Expression end){
    super(front, end);
  }
  
  public float solve() throws Exception{
    try{
      float ans = this.front.solve() * this.end.solve();
      if(ans > pow(10,12)-1 || ans < -pow(10,11)+1){
        throw new OverflowException();
      } else {
        return ans;
      }
    } catch (Exception e) {
      throw e;
    }
  }
}
class NegativeExpression extends UnaryExpression {
  NegativeExpression(Expression expr){
    super(expr);
  }

  public float solve() throws Exception{
    try{
      float ans = this.expr.solve();
      if(ans > pow(10,12)-1){
        throw new OverflowException();
      } else {
        return (-1) * ans;
      }
    } catch (Exception e){
      throw e;
    }
  }
}
class NonFunctionButton extends Button{
    public NonFunctionButton(String labelB, float xpos, float ypos, float widthB, float heightB){
      super(labelB, xpos, ypos, widthB, heightB);
    }

    protected void onClick(){
      view.textBox.add(this.label);
    }
}
class NumberButton extends NonFunctionButton{
    public NumberButton(String labelB, float xpos, float ypos, float widthB, float heightB){
      super(labelB, xpos, ypos, widthB, heightB);
      this.btnTextSize *= 1.1f;
      this.cDefault = NUMBTN_DEFAULT;
      this.cPressed = NUMBTN_PRESSED;
      this.cHover = NUMBTN_HOVER;
    }
}
class OFF implements Function{
  public void go(){
    view.textBox.off();
  }
}
class OverflowException extends ArithmeticException {
  OverflowException(){
    super("Number is too big");
  }
}
class PowerExpression extends BinaryExpression {
  PowerExpression(Expression front, Expression end){
    super(front, end);
  }
    
  public float solve() throws Exception{
    try{
      float ans = pow(this.front.solve(),this.end.solve());
      if(ans > pow(10,12)-1 || ans < -pow(10,11)+1){
        throw new OverflowException();
      } else {
        return ans;
      }
    } catch (Exception e){
      throw e; 
    }
  }
}
class RootExpression extends UnaryExpression {
  RootExpression(Expression expr){
    super(expr);
  }
  public float solve() throws Exception{
    try{
      float ans = this.expr.solve();
      if(ans < 0){
        throw new SquareRootOfNegativeException();
      } else {
        return sqrt(ans);
      }
    } catch (Exception e){
      throw e;
    }
  }
} 
class SquareRootOfNegativeException extends ArithmeticException {
  SquareRootOfNegativeException(){
    super("Cannot have negative root");
  }
}
class SubtractExpression extends BinaryExpression {
  SubtractExpression(Expression front, Expression end){
    super(front, end);
  }
    
  public float solve() throws Exception{
    try{
      float ans = this.front.solve() - this.end.solve();
      if(ans > pow(10,12)-1 || ans < -pow(10,11)+1){
        throw new OverflowException();
      } else {
        return ans;
      }
    } catch(Exception e){
      throw e;
    }
  }
}
class TerminalExpression<T extends Number> implements Expression {
  private float val;

  TerminalExpression(T val){
    this.val = val.floatValue();
  }
  public float solve(){
    return this.val;
  }
}
int TEXTBOX_BOX = color(250);
int TEXTBOX_EXPRESSION = color(50);
int TEXTBOX_ANSWER = color(20);
int TEXTBOX_OFF = color(190);

int maxLong = 12;

class TextBox{
  float textSizeExpression, textSizeAnswer;
  PVector pos, size;
  int c, cExpression, cAnswer;
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
    this.textSizeAnswer = this.size.y / 2.5f;
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
        if(f == PApplet.parseInt(f)){
          this.add(str(PApplet.parseInt(f)));
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
          if (f == PApplet.parseInt(f)) { //mengecek apakah bisa menjadi integer atau tidak
            this.labelAnswer = str(PApplet.parseInt(f));
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
abstract class UnaryExpression implements Expression{
  Expression expr;
  UnaryExpression(Expression expr){
    this.expr = expr;
  }
}
int bgColor = color(220);
int RED = color(220, 30, 30);
int BLACK = color(0);

public class View{
  float padding;
  float btnWeight, textBoxWeight;
  
  PVector btnPos, btnSize;
  float btnPad;
  Button[][] buttons;
  int colSize;

  PVector textBoxPos, textBoxSize;
  TextBox textBox;
  
  public View(){
    this.padding = 0.04f;
    this.btnWeight = 4;
    this.textBoxWeight = 1;
    this.colSize = 5;
    this.btnPad = 0.03f;

    this.textBoxPos = new PVector(this.padding*width, this.padding*height);
    this.textBoxSize =  new PVector((1-2*this.padding)*width, this.textBoxWeight/(this.textBoxWeight+this.btnWeight) * (1 - 3*this.padding) * height);

    this.textBox = new TextBox(this.textBoxPos.x, this.textBoxPos.y, this.textBoxSize.x, this.textBoxSize.y);
    
    this.btnSize = new PVector((1-2*this.padding)*width, (1-3*this.padding)*height - this.textBoxSize.y);
    this.btnPos = new PVector(this.padding*width, 2*this.padding*height + this.textBoxSize.y);
    
    this.buttons = new ButtonBuilder(this.btnPos.x, this.btnPos.y, this.btnSize.x, this.btnSize.y, this.colSize, this.btnPad*width)
                  .setLabel(new String[]{
                      "OFF", "%", "ans", "C","AC",
                      "7", "8", "9", "÷", "MC",
                      "4", "5", "6", "x", "MR",
                      "1", "2", "3", "+", "√",
                      "0", ".", "=", "-", "^"
                  })
                  .setOps(new String[]{
                    "+", "-", "x", "÷", "%",  "ans", "√", "^"
                  })
                  .setNumbers(new String[]{
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."
                  })
                  .setFunction("C", new C())
                  .setFunction("AC", new AC())
                  .setFunction("MR", new MR())
                  .setFunction("MC", new MC())
                  .setFunction("=", new Equal())
                  .setFunction("OFF", new OFF())
                  .build();
  }

  public void display(){
    background(bgColor);
    this.displayButtons();
    this.displayTextBox();
    this.displayText();
  }

  private void displayButtons(){
    for(Button[] btns: buttons){
      for(Button b: btns){
        b.display();
      }
    }
  }

  private void displayTextBox(){
    this.textBox.display();
  }

  private void displayText(){
    fill(50);
    textSize(18);
    textAlign(LEFT);
    text("12 DIGITS", this.textBox.pos.x + 10, this.textBox.pos.y + 28);
  }
}
  
class ZeroDivisionException extends ArithmeticException {
  ZeroDivisionException(){
    super("Cannot divide by zero");
  }
}
  public void settings() {  size(432, 768);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
