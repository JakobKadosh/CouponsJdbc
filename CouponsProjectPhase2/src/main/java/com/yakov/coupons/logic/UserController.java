package com.yakov.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.beans.PostLoginUserData;
import com.yakov.coupons.beans.User;
import com.yakov.coupons.beans.UserLoginData;
import com.yakov.coupons.dao.UserDAO;
import com.yakov.coupons.enums.ClientType;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.InputChecker;

@Controller
public class UserController {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private MyCachManager cacheManager;

	public UserLoginData login(String email, String password) throws MyException {
		try {
			
		PostLoginUserData postlogin = generatePostLoginData(email);
		ClientType clientType = userDAO.login(email, password);
		int token = generateEncryptedToken(email);
		cacheManager.put(token, postlogin);
		
		UserLoginData userLoginData = new UserLoginData(postlogin.getUserId(), postlogin.getCompanId(), clientType,
				token);
		
		return userLoginData;
		}catch (MyException e) {
			throw new MyException(ErrorType.LOGIN_FAILED, "Login Faild");
		}
	}

	private PostLoginUserData generatePostLoginData(String email) throws MyException {
		User user = userDAO.getUserByName(email);
		PostLoginUserData postLoginUser = new PostLoginUserData(user.getUserId(), user.getCompanyId(), user.getClientType());
		return postLoginUser;
	}

	// here we're doing something similar to encryption.
	// this technique work one way and cannot be decrypted
	private int generateEncryptedToken(String email) {
		int ran = (int)Math.random()*1010;
		int ran2 = (int)Math.random()*1010;
		String token = "Salt - junk data" + email + "Sheker kolshehu" + ran*ran2;
		return token.hashCode();
	}

	public long addUser(User user) throws MyException {
		if (isUserValidToAdd(user)) {
			return userDAO.addUser(user);
		} else
			throw new MyException(ErrorType.GENERAL_ERROR, "faild to add user");
	}

	public User getUser(long userID) throws MyException {
		if (InputChecker.isValidId(userID)) {
			return userDAO.getUser(userID);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid user id");
		}
	}
	
	public List<User>getAllUsers()throws MyException{
		return userDAO.getAllUsers();
	}

	public List<User> getUsersByCompanyId(long companyId) throws MyException {
		if (InputChecker.isValidId(companyId)) {
			return userDAO.getUsersByCompanyId(companyId);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "company id is invalid");
		}

	}
	//SQL is responsible to delete all dependencies "ON DELETE CASCADE"

	public void deleteUser(long userID) throws MyException {
		if (InputChecker.isValidId(userID)) {
			userDAO.deleteUser(userID);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid user id");
		}
	}

	public void updateUser(User user) throws MyException {
		if (isUserValidToUpdate(user)) {
			userDAO.updateUser(user);
		} else
			throw new MyException(ErrorType.GENERAL_ERROR, "faild to update user");

	}

	private boolean isUserExistByName(String userName) throws MyException {
		return userDAO.isUserExistByName(userName);
	}

	// private methods to be used locally for validations.

	// this will check the input data to be real
	// and also that the user is'nt register already
	private boolean isUserValidToAdd(User user) throws MyException {
		if (user == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "user is null!");
		} else if (isUserExistByName(user.getUserName())) {
			throw new MyException(ErrorType.GENERAL_ERROR, " user name exsits on the data base already");
		} else if (!InputChecker.isValidEmail(user.getUserName())) {
			throw new MyException(ErrorType.INVALID_EMAIL, "user mail address is invalid");
		} else if (!InputChecker.isValidPassword(user.getUserPassword())) {
			throw new MyException(ErrorType.INVALID_PASSWORD, "users password is invalid");
		}
		return true;
	}

	// this method will also check the authenticity of the data before updating in
	// the data base.
	private boolean isUserValidToUpdate(User user) throws MyException {
		if (user == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "user is null!");
		} else if (!InputChecker.isValidEmail(user.getUserName())) {
			throw new MyException(ErrorType.INVALID_EMAIL, "user mail address is invalid");
		} else if (!InputChecker.isValidPassword(user.getUserPassword())) {
			throw new MyException(ErrorType.INVALID_PASSWORD, "users password is invalid");
		}
		return true;
	}

}
