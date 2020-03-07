Button satu,dua,tiga,empat,lima,enam,tujuh,delapan,sembilan,nol, titik, equal, tambah, kali,bagi, kurang;  // the button
int clk = 1;   
void setup(){
  size(500,700);
  smooth();
  
  tujuh     = new Button("7", 10 ,90 ,40,40);
  delapan   = new Button("8", 60 ,90 ,40,40);
  sembilan  = new Button("9", 110,90 ,40,40);
  empat     = new Button("4", 10 ,140,40,40);
  lima      = new Button("5", 60 ,140,40,40);
  enam      = new Button("6", 110,140,40,40);
  satu      = new Button("1", 10 ,190,40,40);
  dua       = new Button("2", 60 ,190,40,40);
  tiga      = new Button("3", 110,190,40,40);
  nol       = new Button("0", 10 ,240,40,40);
  titik     = new Button(".", 60 ,240,40,40);
  equal     = new Button("=", 110,240,40,40);
  tambah    = new Button("+", 160,90 ,40,40);
  kali      = new Button("x", 160,140,40,40);
  bagi      = new Button("/", 160,190,40,40);
  kurang    = new Button("-", 160,240,40,40);
}
  
  
void draw() {
  // draw a square if the mouse curser is over the button
  if (satu.MouseIsOver()) {
    rect(200, 20, 50, 50);
  }
  else {
    // hide the square if the mouse cursor is not over the button
    background(0);
  }
  // draw the button in the window
  satu.Draw();
  dua.Draw();
  tiga.Draw();
  empat.Draw();
  lima.Draw();
  enam.Draw();
  tujuh.Draw();
  delapan.Draw();
  sembilan.Draw();
  nol.Draw();
  tambah.Draw();
  kali.Draw();
  bagi.Draw();
  kurang.Draw();
  equal.Draw();
  titik.Draw();
}

// mouse button clicked
void mousePressed()
{}