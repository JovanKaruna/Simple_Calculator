class RootExpression extends UnaryExpression {
  RootExpression(Expression expr){
    super(expr);
  }
  public float solve() throws Exception{
    try{
      float ans = this.expr.solve();
      if(ans < 0){
        throw new SquareRootOfNegativeException();
      } else {
        return sqrt(ans);
      }
    } catch (Exception e){
      throw e;
    }
  }
} 
