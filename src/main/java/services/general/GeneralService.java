package services.general;


import exceptions.UserAlreadyExistsException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.Offer;
import model.products.Product;
import model.products.ProductList;
import model.users.User;
import services.microservices.UserService;

public class GeneralService {
	
	
	
	
	
	
	private UserService userService;
	private GeneralOfferService generalOfferService;
	
	
	
	

	public void createUser (User newUser) throws UserAlreadyExistsException{
		getUserService().createNewUser(newUser);	
	}
	
	public void loginUser (User user) throws UsernameOrPasswordInvalidException {
		getUserService().loginUser(user);
	}
	
	public void logoutUser (User user) throws UsernameOrPasswordInvalidException{
		getUserService().logout(user);
	}
	
	public void authenticateUser (User user) throws UsernameOrPasswordInvalidException {
		getUserService().authenticateUser(user);
	}
	
	public void createOffer (Offer offer , User user) throws UsernameOrPasswordInvalidException, WrongUserPermissionException {
		if (getUserService().hasWritePermission(user)) {
			getGeneralOfferService().save(offer);
		} throw new WrongUserPermissionException();
	}
	
	public void addProduct (Product p) {}
	
	public void selectProduct (User u , ProductList pl , Product p) {}
	
	public void removeProduct (User u , ProductList pl , Product p ) {}
	
	public void generateRecommmendation (User u , Product p) {}
	
	public void applyOffer (User u , Offer o , ProductList pl) {}
	
	public void removeOffer (User u , Offer o , ProductList pl) {}

	
	
	
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public GeneralOfferService getGeneralOfferService() {
		return generalOfferService;
	}
	public void setGeneralOfferService(GeneralOfferService generalOfferService) {
		this.generalOfferService = generalOfferService;
	}
	
	
	
	
}
