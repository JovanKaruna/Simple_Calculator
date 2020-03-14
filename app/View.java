package app;

color bgColor = color(220);
color RED = color(220, 30, 30);
color BLACK = color(0);

public class View{
  int padding;
  float btnWeight, textBoxWeight;
  
  PVector btnPos, btnSize;
  float btnPad;
  Button[][] buttons;
  int colSize;

  PVector textBoxPos, textBoxSize;
  TextBox textBox;
  
  public View(){
    this.padding = 0.04;
    this.btnWeight = 3;
    this.textBoxWeight = 1;
    this.colSize = 5;
    this.btnPad = 0.03;
    
    this.textBoxPos = new PVector(this.padding*width, this.padding*height);
    this.textBoxSize =  new PVector((1-2*this.padding)*width, this.textBoxWeight/(this.textBoxWeight+this.btnWeight) * (1 - 3*this.padding) * height);

    this.textBox = new TextBox(this.textBoxPos.x, this.textBoxPos.y, this.textBoxSize.x, this.textBoxSize.y);
    
    this.btnSize = new PVector((1-2*this.padding)*width, (1-3*this.padding)*height - this.textBoxSize.y);
    this.btnPos = new PVector(this.padding*width, 2*this.padding*height + this.textBoxSize.y);
    
    this.buttons = new ButtonBuilder(this.btnPos.x, this.btnPos.y, this.btnSize.x, this.btnSize.y, this.colSize, this.btnPad*width)
                  .setLabel(new String[]{
                      "(", ")", "ans", "C","AC",
                      "7", "8", "9", "÷", "MC",
                      "4", "5", "6", "x", "MR",
                      "1", "2", "3", "+", "√",
                      "0", ".", "=", "-", "^"
                  })
                  .setOps(new String[]{
                    "+", "-", "x", "÷", "(", ")", "ans", "√", "^"
                  })
                  .setNumbers(new String[]{
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."
                  })
                  .setFunction("C", new C())
                  .setFunction("AC", new AC())
                  .setFunction("MR", new MR())
                  .setFunction("MC", new MC())
                  .setFunction("=", new Equal())
                  .build();
  }

  public void display(){
    background(bgColor);
    this.displayButtons();
    this.displayTextBox();
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
}
  