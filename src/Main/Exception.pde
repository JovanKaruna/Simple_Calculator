class Exception extends Throwable{
  String message;
  Exception(){
    this("");
  }
  Exception(String msg){
    this.message = msg;
  }
  public String getMessage(){
    return this.message;
  }
}
