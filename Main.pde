final PVector aspectRatio = new PVector(9, 16);
final float screenSize = 48;
View view;

void setup(){
  size(aspectRatio.x*screenSize, aspectRatio.y*screenSize);
  //surface.setSize((int)Math.floor(aspectRatio.x*screenSize), (int)Math.floor(aspectRatio.y*screenSize));
  //surface.setLocation(200,50);
  //surface.setResizable(true);
  textAlign(CENTER, CENTER);
  noStroke();
  textSize(25);
  smooth();
  view = new View();
  background(0);
}

void draw() {
  view.display();
}