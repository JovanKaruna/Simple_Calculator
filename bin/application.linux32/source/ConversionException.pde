class ConversionException extends Exception {
  ConversionException(){
    this("");
  }
  ConversionException(String msg){
    this.message = msg;
  }
}
