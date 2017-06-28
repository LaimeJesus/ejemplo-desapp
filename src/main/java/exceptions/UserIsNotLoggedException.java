package exceptions;

public class UserIsNotLoggedException extends Exception {

	public UserIsNotLoggedException(String msg) {
		super(msg);
	}

	public UserIsNotLoggedException() {
		super("User is not logged");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4478550129623784581L;

}