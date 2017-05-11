package services.microservices;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import exceptions.UserAlreadyExistsException;
import exceptions.UsernameOrPasswordInvalidException;
import model.products.ProductList;
import model.users.User;
import util.Money;

public class UserService extends GenericService<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2769328934018732341L;

	
	
	
	@Transactional
	public void createNewUser (User newUser) throws UserAlreadyExistsException {
		this.validateNewUser(newUser);
		this.save(newUser);
	}
	
	@Transactional
	public void loginUser (User user) throws UsernameOrPasswordInvalidException {
		User possible = this.validateExist(user);
		possible.login();
		this.update(possible);
	}
	
	@Transactional
	public void logout (User user) throws UsernameOrPasswordInvalidException {
		User possible = this.validateExist(user);
		possible.logout();
		this.update(possible);
	}
	
	@Transactional
	public User authenticateUser (User user) throws UsernameOrPasswordInvalidException {
		return this.validateExist(user);
	}
	
	@Transactional
	public boolean hasWritePermission(User user) throws UsernameOrPasswordInvalidException {
		User current = this.authenticateUser(user);
		return current.hasWritePermission();
		
	}
	
	@Transactional
	public void createProductList(User anUser , ProductList aProductList) throws UsernameOrPasswordInvalidException {
		User possible = this.authenticateUser(anUser);
		aProductList.setTotalAmount(new Money(0,0));
		possible.getProfile().addNewProductList(aProductList);
		this.update(possible);
	}
	
	
	@Transactional
	public User findByUsername (String username) {
		User exampleUser = new User();
		exampleUser.setUsername(username);
		exampleUser.setEmail(null);
		exampleUser.setPassword(null);
		exampleUser.setId(null);
		exampleUser.setProfile(null);
		exampleUser.setUserPermission(null);
		exampleUser.logout();
		List<User> possible = this.getRepository().findByExample(exampleUser);
		return (possible.size() > 0) ? possible.get(0) : null;
	}
	
	@Transactional
	private User validateExist(User possible) throws UsernameOrPasswordInvalidException {
		User exist = this.findByUsername(possible.getUsername());
		if (! possible.equals(exist)) throw new UsernameOrPasswordInvalidException();
		return exist;
	}
	
	@Transactional
	private void validateNewUser (User possibleNewUser) throws UserAlreadyExistsException {
		User exist = this.findByUsername(possibleNewUser.getUsername());
		if (exist != null) throw new UserAlreadyExistsException();
	}

	@Transactional
	public List<ProductList> getListsFromUser(User saved) throws UsernameOrPasswordInvalidException {
		User exist = this.authenticateUser(saved);
		return exist.getProfile().getAllProductList();
	}
	
}
