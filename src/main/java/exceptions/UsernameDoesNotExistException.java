package exceptions;

public class UsernameDoesNotExistException extends Exception {

  public UsernameDoesNotExistException(){
    super("Username does not exist");
  }
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 597289695910834157L;

}
