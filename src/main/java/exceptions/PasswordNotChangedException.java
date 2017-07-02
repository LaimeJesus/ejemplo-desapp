package exceptions;

public class PasswordNotChangedException extends Exception {

   public PasswordNotChangedException(){
     super("Trying to change password for the same");
   }
  /**
   * 
   */
  private static final long serialVersionUID = -7838898101494325927L;

}
