package services;

import java.util.List;

import model.User;

public class LoginService {

	private List<User> users;

	public void login(User anUser){
		if(this.exists(anUser)){
			//login an user
		}
		//update an user
	}
	
	private void save(User anUser) {
		this.users.add(anUser);
	}

	private boolean exists(User anUser) {
		return true;
	}

	//register
	public void signUp(User anUser){
		if(this.exists(anUser)){
			//throw an exception
		}
		this.newUser(anUser);
	}
	
	private void newUser(User anUser) {
		this.save(anUser);
	}

	public void logout(User anUser) {
		if(this.exists(anUser)){
			//check if anUser is logged
		}
		//update an user
	}
}
