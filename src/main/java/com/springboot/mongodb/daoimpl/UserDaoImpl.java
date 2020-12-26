package com.springboot.mongodb.daoimpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.springboot.mongodb.dao.UserDao;
import com.springboot.mongodb.exceptions.UserNotFoundException;
import com.springboot.mongodb.model.User;
import com.springboot.mongodb.repositories.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private MongoTemplate mongotemplate;

	@Autowired
	private UserRepository userRepository;

	public List<User> findAllUsers() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			return null;
		}
		return users;
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User findUser(String userID) {

		Optional<User> user = userRepository.findById(userID);

		if (!user.isPresent()) {
			throw new UserNotFoundException("User ID -" + userID);
		}

		return user.get();
	}

	@Override
	public Object QueryUser(String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where(userId).is(userId));
		User user = mongotemplate.findOne(query, User.class);

//		Object userSettings = user.getUserSettings();
//		return userSettings;

		return user != null ? user.getUserSettings() : "User not found.";
	}

	public String QueryUserwithKey(String userID, String key) {

		Query query = new Query();
		query.fields().include("userSettings");
		query.addCriteria(
				Criteria.where("userId").is(userID).andOperator(Criteria.where("userSettings." + key).exists(true)));
		User user = mongotemplate.findOne(query, User.class);
		return user != null ? user.getUserSettings().get(key) : "Not found.";

	}

	public String saveUserSettings(String userID, String key, String value) {

		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userID));
		User user = mongotemplate.findOne(query, User.class);
		if (user != null) {
			user.getUserSettings().put(key, value);
			mongotemplate.save(user);
			return "Key added.";
		} else {
			return "User not found.";
		}

	}
}
