package exceptions;

public class PurchaseRecordNotExistException extends Exception{

	public PurchaseRecordNotExistException(String msg) {
		super(msg);
	}

	public PurchaseRecordNotExistException(){
	  super("Purchase record does not exist");
	}
	
	private static final long serialVersionUID = -8755933967650253053L;

}
