class TerminalExpression<T extends Number> implements Expression {
  private float val;

  TerminalExpression(T val){
    this.val = val.floatValue();
  }
  public float solve(){
    return this.val;
  }
}
