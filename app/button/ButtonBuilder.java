package button;

import java.util.HashMap;

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
    this(xpos, ypos, heightB, widthB, 4, 0.1*heightB);
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
    this.rowSize = Math.floor((this.labels.length-1)/this.colSize)+1;
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