color BTN_DEFAULT = color(245, 190, 25); // Orange
color BTN_PRESSED = color(230, 170, 20); // dark Orange
color BTN_HOVER = color(250, 200, 30); // light Orange

color NUMBTN_DEFAULT = color(250);
color NUMBTN_PRESSED = color(240);
color NUMBTN_HOVER = color(255);

abstract class Button {
  String label; //angka, simbol atau huruf
  PVector pos, size; 
  color c, cDefault, cPressed, cHover; //warna label dan tombol
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
    this.btnTextSize = min(this.size.x, this.size.y)/2.5;
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
