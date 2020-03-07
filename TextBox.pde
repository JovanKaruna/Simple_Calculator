class TextBox{
  PVector pos, size;
  color c;
  TextBox(float xpos, float ypos, float xsize, float ysize){
    this.pos = new PVector(xpos, ypos);
    this.size = new PVector(xsize, ysize);
    this.c = color(240);
  }

  public void display(){
    fill(this.c);
    rect(this.pos.x, this.pos.y, this.size.x, this.size.y);
  }
}