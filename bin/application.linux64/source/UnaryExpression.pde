abstract class UnaryExpression implements Expression{
  Expression expr;
  UnaryExpression(Expression expr){
    this.expr = expr;
  }
}
