package com.springboot.mongodb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mongodb.daoimpl.UserDaoImpl;
import com.springboot.mongodb.exceptions.UserNotFoundException;
import com.springboot.mongodb.model.User;

@Service
public class UserService {

	@Autowired
	UserDaoImpl userdaoimpl;

	public List<User> getAllUsers() {
		List<User> users = userdaoimpl.findAllUsers();
		if (users.isEmpty()) {
			throw new UserNotFoundException("User List is null");
		}
		return users;
	}

	public User getUser(String userID) {
		User user = userdaoimpl.findUser(userID);
		return user;
	}

	public User addUser(User user) {

		return userdaoimpl.saveUser(user);
	}
	
	public Object getSettings (String userID) {
		return userdaoimpl.QueryUser(userID);
	}
	
	public String getSettingsWithKey(String userID, String key) {
		return userdaoimpl.QueryUserwithKey(userID, key);
	}
	
	public String addSettings(String userID, String key, String value) {
		return userdaoimpl.saveUserSettings(userID, key,value);
	}
	
}
