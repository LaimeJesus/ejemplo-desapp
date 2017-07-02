package exceptions;

public class PasswordInvalidException extends Exception {

  public PasswordInvalidException(){
    super("Invalid password");
  }
  
  private static final long serialVersionUID = -1879898553903275817L;

}
