class Button {
  String label;
  PVector pos, size;
  color c;
  float roundness;
  
  // constructor
  Button(float xpos, float ypos, float widthB, float heightB){
      this("", xpos, ypos, widthB, heightB);
  }

  Button(String labelB, float xpos, float ypos, float widthB, float heightB) {
    this(labelB, xpos, ypos, widthB, heightB, 10);
  }

  Button(String labelB, float xpos, float ypos, float widthB, float heightB, float roundnessB) {
    this.label = labelB;
    this.c = RED;
    this.pos = new PVector(xpos, ypos);
    this.size = new PVector(widthB, heightB);
    this.roundness = roundnessB;
  }
  
  public void display() {
    if(this.onHover()){
        if(mousePressed){
          this.c = color(200, 130, 5);
        } else {
          this.c = color(240, 170, 30);
        }
    } else {
        this.c = ORANGE;
    }

    fill(this.c);
    rect(this.pos.x, this.pos.y, this.size.x, this.size.y, this.roundness);
    fill(BLACK);
    text(this.label, this.pos.x + (this.size.x / 2), this.pos.y + (this.size.y / 2));
  }
  
  public boolean onHover() {
    return (mouseX > this.pos.x && mouseX < (this.pos.x + this.size.x) &&
            mouseY > this.pos.y && mouseY < (this.pos.y + this.size.y));
  }
}