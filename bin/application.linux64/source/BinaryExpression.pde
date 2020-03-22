abstract class BinaryExpression implements Expression{
  Expression front, end;
  BinaryExpression(Expression front, Expression end){
    this.front = front;
    this.end = end;
  }
}
