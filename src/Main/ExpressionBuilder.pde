import java.util.HashMap;

class ExpressionBuilder{
  String input;
  String[] oprPrio;
  HashMap<String, Expression> opr;
  int i;

  ExpressionBuilder(String s){
    this.input = s;
    // dari prio terendah
    this.oprPrio = new String[]{"+", "-", "x", "÷","%", "^", ";", "&"};
    i = 0;
  }

  public Expression parse() throws Exception{
    // preprocess bedain - unary dengan binary polanya yaitu
    // opr + "-" + angka : unary, diubah jadi &
    for(int i = 0; i < this.input.length()-2; ++i){
      char c1 = this.input.charAt(i);
      char c2 = this.input.charAt(i+1);
      char c3 = this.input.charAt(i+2);
      if(c2 == '-' && ('0' <= c3 && c3 <= '9' || c3 == ';' || c3 == 'a')){
        for(String opr: oprPrio){
          if(c1 == opr.charAt(0)){
            this.input = this.input.substring(0, i+1) + "&" + this.input.substring(i+2);
          }
        }
      }
    }
    if(this.input.charAt(0) == '-' && ('0' <= this.input.charAt(1) && this.input.charAt(1) <= '9' || this.input.charAt(1) == ';' || this.input.charAt(1) == 'a')){
      this.input = this.input.substring(0, 0) + "&" + this.input.substring(1);
    }
    this.input = this.input.replace("-&", "+");
    System.out.println(this.input);

    // rekursif
    try{
      return this.parseUtil(this.input);
    } catch (ConversionException e){
      throw e;
    }
  }

  // parse first number from the string
  private float getNextNumber(String input) throws ConversionException{
    boolean isFloat = false;
    // includes negative (if any)
    String s = input.substring(0, 1);
    for(char c: input.substring(1).toCharArray()){
      if(('0' <= c && c <= '9') || c == '.'){
        if(c == '.'){
          if(isFloat){
            throw new ConversionException("Invalid decimal number");
          }
          isFloat = true;
        }
        s += str(c);
      } else {
        if(s.length() == 1){
          throw new ConversionException("Invalid number");
        }
        return float(s);
      }
    }
    return float(s);
  }

  private boolean endsWithZeros(String s){
    int x = s.indexOf(".");
    if(x == -1) return false;
    for(char c: s.substring(x+1).toCharArray()){
      if(c != '0'){
        return false;
      }
    }
    return true;
  }

  private Expression parseUtil(String inp) throws ConversionException{
    i++;

    // basis
    if(inp.length() == 0){
      throw new ConversionException("Expression incomplete");
    }
    
    if(!Float.isNaN(float(inp))){
      return new TerminalExpression(float(inp));
    }
    //System.out.println(inp.substring(2));
    if(inp.charAt(0) == '&'){
      
      if(!Float.isNaN(float(inp.substring(1))) || this.endsWithZeros(inp.substring(1))){
        return new NegativeExpression(new TerminalExpression(float(inp.substring(1))));
      } else if (inp.charAt(1) == ';'){
        if(!Float.isNaN(float(inp.substring(2))) || this.endsWithZeros(inp.substring(2))){
          return new NegativeExpression(new RootExpression(new TerminalExpression(float(inp.substring(2)))));
        }
      }
    }
    if(inp.equals("ans")){
      if(lastAns.length() == 0){
        throw new ConversionException("Ans is empty");
      } else {
        return new TerminalExpression(float(lastAns));
      }
    } else if(inp.equals("&ans")){
      if(lastAns.length() == 0){
        throw new ConversionException("Ans is empty");
      } else {
        return new NegativeExpression(new TerminalExpression(float(lastAns)));
      }
    }

    // rekursif
    for(String opr: this.oprPrio){
      int len = split(inp, opr)[0].length();
      // no opr found, next
      if(len == inp.length()) continue;

      String front = inp.substring(0,len);
      String end = inp.substring(len+1, inp.length());
        
      if(opr.equals("x")){
        if(front.length() == 0  || end.length() == 0){
          throw new ConversionException("Need both side for multiplication");
        }
        return new MultiplyExpression(this.parseUtil(front), this.parseUtil(end));
      
      } else if (opr.equals("÷")){
        if(front.length() == 0  || end.length() == 0){
          throw new ConversionException("Need both side for multiplication");
        }
        return new DivideExpression(this.parseUtil(front), this.parseUtil(end));

      } else if (opr.equals("+")){
        if(front.length() == 0 || end.length() == 0){
          throw new ConversionException("Need both side for addition");
        }
        return new AddExpression(this.parseUtil(front), this.parseUtil(end));

      } else if (opr.equals("-")){
        if(front.length() == 0 || end.length() == 0){
          throw new ConversionException("Need both side for subtraction");
        }
        return new SubtractExpression(this.parseUtil(front), this.parseUtil(end));
      
      } else if(opr.equals(";")){
        if(front.length() != 0 && front.charAt(0) != '&'){
          throw new ConversionException("Left side of root operator must be empty");
        }
        if(end.length() == 0){
          throw new ConversionException("Right side of root operator must not be empty");
        }
        try{
          return new RootExpression(new TerminalExpression(this.getNextNumber(end)));
        } catch (ConversionException e){
          throw e;
        }

      } else if(opr.equals("^")){
        if(front.length() == 0 || end.length() == 0){
          throw new ConversionException("Need both side for addition");
        }
        return new PowerExpression(this.parseUtil(front), this.parseUtil(end));

      } else if (opr.equals("%")){
        if(front.length() == 0  || end.length() == 0){
          throw new ConversionException("Need both side for multiplication");
        }
        return new ModuloExpression(this.parseUtil(front), this.parseUtil(end));

      } else if (opr.equals("&")){
        if(front.length() != 0){
          throw new ConversionException("Left side of unary negative must be empty");
        }
        try{
          return new NegativeExpression(new TerminalExpression(this.getNextNumber(end)));
        } catch (ConversionException e){
          throw e;
        }
      }
    }
    throw new ConversionException("Expression incomplete");
  }
}
