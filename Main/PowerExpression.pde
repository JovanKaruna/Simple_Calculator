class PowerExpression extends BinaryExpression {
  PowerExpression(Expression front, Expression end){
    super(front, end);
  }
    
  public float solve() throws Exception{
    try{
      float ans = pow(this.front.solve(),this.end.solve());
      if(ans > pow(10,12)-1 || ans < -pow(10,11)+1){
        throw new OverflowException();
      } else {
        return ans;
      }
    } catch (Exception e){
      throw e; 
    }
  }
}
