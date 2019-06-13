package com.yakov.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yakov.coupons.beans.User;
import com.yakov.coupons.beans.UserLoginData;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.logic.UserController;

@RestController
@RequestMapping("/users")
public class UserApi {

@Autowired
private UserController userController;

@PostMapping("/login")
public UserLoginData login(@RequestBody User user) throws MyException {
	return userController.login(user.getUserName(),user.getUserPassword());
}

@PostMapping
public void addUser(@RequestBody User user)throws MyException{
	userController.addUser(user);
}

@GetMapping("/{userId}")
public User getUserById(@PathVariable("userId")long id)throws MyException{
	return userController.getUser(id);
}
@GetMapping
public List<User>getAllUsers()throws MyException{
	return userController.getAllUsers();
}

@GetMapping("/companyUsers")
public List<User> getUsersByCompanyId(@RequestParam("companyId")long companyId) throws MyException {
	return userController.getUsersByCompanyId(companyId);
}
@PutMapping
public void updateUser(@RequestBody User user)throws MyException{
	userController.updateUser(user);
}

@DeleteMapping("/{userId}")
public void deleteUser(@PathVariable("userId")long id)throws MyException{
	userController.deleteUser(id);
}



}
