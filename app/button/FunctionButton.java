package button;

// using Strategy Pattern https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
class FunctionButton extends Button{
    Function function;
    public FunctionButton(String labelB, float xpos, float ypos, float widthB, float heightB, Function func){
      super(labelB, xpos, ypos, widthB, heightB);
      this.function = func;
    }

    protected void onClick(){
      this.function.go();
    }
}