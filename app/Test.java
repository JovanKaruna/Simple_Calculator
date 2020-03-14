  
  // HashMap<Integer, String> hm;
  // NonFunctionButton nfb;
  FunctionButton fb;
  void setup(){
    size(400, 400);
    // hm = new HashMap<Integer, String>();
    // hm.put(1, "one");
    // nfb = new NonFunctionButton("ta", 10, 10, 50, 50);
    fb = new FunctionButton("s", 50, 50, 50, 50, new MC());
    background(0);
  }

  void draw() {
    
    
    // nfb.display();
    fb.display();
    // fill(255);
    // text(hm.get(1), 100, 100);
    // text("test", 50, 50);
  }