import java.util.LinkedList;
import java.util.Queue;

//final PVector aspectRatio = new PVector(9, 16);
//final float screenSize = 48;
View view;
String lastAns = "";
Queue<Float> memory;

void setup() {
  // WEB:
  //size(aspectRatio.x*screenSize, aspectRatio.y*screenSize);

  // LOCAL:
  //size(432, 768);

  // ANDROID:
  fullScreen();

  textAlign(CENTER, CENTER);
  noStroke();
  smooth();
  view = new View();
  memory = new LinkedList<Float>();
  // memory.add(1);
  // System.out.println(memory.peek());
}

void draw() {
  view.display();
}
