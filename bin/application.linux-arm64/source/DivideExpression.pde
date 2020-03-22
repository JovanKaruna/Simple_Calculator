class DivideExpression extends BinaryExpression {
  DivideExpression(Expression front, Expression end){
    super(front, end);
  }

  public float solve() throws Exception{
    try{
      float ans = this.end.solve();
      if(ans == 0){
        throw new ZeroDivisionException();
      }
    
      float answer = this.front.solve() / this.end.solve();
      if(answer > pow(10,12)-1 || answer < -pow(10,11)+1){
        throw new OverflowException();
      }
  
      return answer;
    } catch (Exception e){
      throw e; 
    }
  }
}
