package button;

class NonFunctionButton extends Button{
    public NonFunctionButton(String labelB, float xpos, float ypos, float widthB, float heightB){
      super(labelB, xpos, ypos, widthB, heightB);
    }

    protected void onClick(){
      view.textBox.add(this.label);
    }
}