// create array of buttons using Builder Pattern
class ButtonBuilder{
  PVector pos, size;
  PVector boxSize;

  int colSize;
  int rowSize;
  float pad;
  Button[][] buttons;
  String[] labels;

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
  }

  public ButtonBuilder setLabel(String[] strings){
    this.labels = strings;
    this.rowSize = Math.floor((this.labels.length-1)/this.colSize)+1;
    this.size = calcBtnSize();
    return this;
  }

  public Button[][] build(){
    this.buttons = new Button[this.rowSize][this.colSize];
    int i = 0;
    int j = 0;
    for(String s: this.labels){
      this.buttons[i][j] = new Button(s, this.pos.x + this.size.x*j + this.pad*j, this.pos.y + this.size.y*i + this.pad*i, this.size.x, this.size.y);
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
}