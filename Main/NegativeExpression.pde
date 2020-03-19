class NegativeExpression extends UnaryExpression {
  NegativeExpression(Expression expr){
    super(expr);
  }

  public float solve() throws Exception{
    try{
      float ans = this.expr.solve();
      if(ans > pow(10,12)-1){
        throw new OverflowException();
      } else {
        return (-1) * ans;
      }
    } catch (Exception e){
      throw e;
    }
  }
}
