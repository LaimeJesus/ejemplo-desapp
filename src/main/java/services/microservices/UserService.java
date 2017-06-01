package services.microservices;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import model.products.ProductList;
import model.registers.PurchaseRecord;
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
	public void loginUser (User user) throws UsernameDoesNotExistException, UsernameOrPasswordInvalidException {
		User possible = validateExist(user);
		User current = validatePassword(user);
		possible.login();
		this.update(possible);
	}

	@Transactional
	public void logout (User user) throws UsernameDoesNotExistException, UserIsNotLoggedException {
		User possible = this.validateLogged(user);
		possible.logout();
		this.update(possible);
	}
	
	@Transactional
	public User authenticateUser (User user) throws UsernameDoesNotExistException {
		return this.validateExist(user);
	}
	
	@Transactional
	public boolean hasWritePermission(User user) throws UsernameDoesNotExistException {
		User current = this.authenticateUser(user);
		return current.hasWritePermission();
		
	}
	
	@Transactional
	public void createProductList(User anUser , ProductList aProductList) throws UsernameDoesNotExistException, UserIsNotLoggedException {
		User possible = this.validateLogged(anUser);
		aProductList.setTotalAmount(new Money(0,0));
		possible.getProfile().addNewProductList(aProductList);
		this.update(possible);
	}
	
	
	@Transactional
	public User findByUsername (String username){
		List<User> possible = this.getRepository().findByField("username", username);
		if(possible.size()>0){
			return possible.get(0);
		}
		else{
			return null;
		}
	}
	
	@Transactional
	private User validateExist(User possible) throws UsernameDoesNotExistException {
		User exist = this.findByUsername(possible.getUsername());
		if (exist == null || !possible.getUsername().equals(exist.getUsername())) throw new UsernameDoesNotExistException();
		return exist;
	}
	
	@Transactional
	private void validateNewUser (User possibleNewUser) throws UserAlreadyExistsException{
		User exist = this.findByUsername(possibleNewUser.getUsername());
		if (exist != null) throw new UserAlreadyExistsException();
	}

	@Transactional
	public List<ProductList> getListsFromUser(User saved) throws UsernameDoesNotExistException {
		User exist = this.authenticateUser(saved);
		return exist.getProfile().getAllProductList();
	}
	
	
	@Transactional
	public List<PurchaseRecord> getPurchaseRecords(User user) throws UsernameDoesNotExistException{
		User exist = this.authenticateUser(user);
		return exist.getProfile().getPurchaseRecords();
	}

	@Transactional
	public User validateLogged(User user) throws UserIsNotLoggedException, UsernameDoesNotExistException {
		User exist = this.validateExist(user);
		if(exist.getIsLogged()){
			return exist;
		}
		throw new UserIsNotLoggedException();
	}
	
	@Transactional
	private User validatePassword(User user) throws UsernameOrPasswordInvalidException {
		User persisted = this.findByUsername(user.getUsername());
		if (user.getPassword().equals(persisted.getPassword())){
			return persisted;
		} throw new UsernameOrPasswordInvalidException();
	}

	@Transactional
	public User getUserById(Integer userId) throws UserDoesNotExistException {
		User user = findById(userId);
		if(user == null){
			throw new UserDoesNotExistException();
		}
		return user;
	}

	@Transactional
	public List<ProductList> getProductLists(Integer userId) throws UserDoesNotExistException {
		return getUserById(userId).getProfile().getAllProductList();
	}
}
