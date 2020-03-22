class ModuloExpression extends BinaryExpression {
  ModuloExpression(Expression front, Expression end){
    super(front, end);
  }
  public float solve() throws Exception{
    try{
      float ans = this.end.solve();
      if(ans == 0){
        throw new ZeroDivisionException();
      } else {
        float answer = this.front.solve() % this.end.solve();
        if(answer > pow(10,13)-1 || answer < -pow(10,13)+1){
          throw new OverflowException();
        } else {
          return answer;
        } 
      }
    } catch (Exception e){
      throw e;
    } 
  }
}
