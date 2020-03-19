// import java.util.Queue;

//final PVector aspectRatio = new PVector(9, 16);
//final float screenSize = 48;
View view;
String lastAns = "";
// Queue<Expression> memory;


void setup(){
  // WEB:
  //size(aspectRatio.x*screenSize, aspectRatio.y*screenSize);
  
  // LOCAL:
   size(432, 768);
  
  // ANDROID:
  //surface.setSize((int)Math.floor(aspectRatio.x*screenSize), (int)Math.floor(aspectRatio.y*screenSize));
  //surface.setLocation(200,50);
  //surface.setResizable(true);
  
  textAlign(CENTER, CENTER);
  noStroke();
  smooth();
  view = new View();
  // memory = new ArrayList<Integer>();
  // memory.add(1);
  // System.out.println(memory.peek());
}

void draw() {
  view.display();
}
