package button;

class NumberButton extends NonFunctionButton{
    public NumberButton(String labelB, float xpos, float ypos, float widthB, float heightB){
      super(labelB, xpos, ypos, widthB, heightB);
      this.btnTextSize = 30;
      this.cDefault = NUMBTN_DEFAULT;
      this.cPressed = NUMBTN_PRESSED;
      this.cHover = NUMBTN_HOVER;
    }
}