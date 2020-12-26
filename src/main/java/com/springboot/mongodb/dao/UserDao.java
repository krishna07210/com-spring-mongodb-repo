package com.springboot.mongodb.dao;

import java.util.*;

import com.springboot.mongodb.model.User;

public interface UserDao {

	List<User> findAllUsers();

	User findUser(String userID);

	User saveUser(User user);

	Object QueryUser(String userID);

	String QueryUserwithKey(String userID, String key);

	String saveUserSettings(String userID, String key, String value);

}
