package com.springboot.mongodb.controllers;

import java.util.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.springboot.mongodb.model.Pets;
import com.springboot.mongodb.model.User;
import com.springboot.mongodb.repositories.PetsRepository;
import com.springboot.mongodb.repositories.UserRepository;
import com.springboot.mongodb.services.UserService;
import com.springboot.mongodb.constants.Constants;

@RestController
@RequestMapping("/mongodb")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mt;

	@Autowired
	private PetsRepository petsRepository;

	@Autowired
	UserService service;

	public static final String JsonPayLoadformat = Constants.APPLICATION_JSON;

	@RequestMapping(value = "/getallUsersList", method = RequestMethod.GET, produces = JsonPayLoadformat, consumes = JsonPayLoadformat)
	public List<User> getUsers() {
		return service.getAllUsers();
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = JsonPayLoadformat, consumes = JsonPayLoadformat)
	public User addNewUsers(@RequestBody User user) {
		return service.addUser(user);
	}

	@RequestMapping(value = "/getUser/{userID}", method = RequestMethod.GET) // , produces = JsonPayLoadformat, consumes
																				// = JsonPayLoadformat)
	public User getUserDetails(@PathVariable String userID) {
		return service.getUser(userID);
	}

	@RequestMapping(value = "/settings/{userID}/{key}", method = RequestMethod.GET) // , produces = JsonPayLoadformat,
																					// consumes = JsonPayLoadformat)
	public Object getUserSettings(@PathVariable String userID, String key) {
		return service.getSettingsWithKey(userID, key);
	}

	@RequestMapping(value = "/settings/{userId}/{key}/{value}", method = RequestMethod.GET)
	public String getUserSetting(@PathVariable String userId, @PathVariable String key, String value) {
		return service.addSettings(userId, key, value);
	}

	@RequestMapping(value = "/dbname", method = RequestMethod.GET)
	public List<String> getMongoDBList() {

		MongoClient mongo = new MongoClient();
		List<String> show_dbs = mongo.getDatabaseNames();
		return show_dbs;
	}

	@RequestMapping(value = "/insertDocument/{collection}", method = RequestMethod.GET)
	public String createDocument(@PathVariable String collection) {

		MongoClient mongo = new MongoClient();
		MongoDatabase db = mongo.getDatabase("journaldev");
		MongoCollection<Document> col = db.getCollection(collection);
		Document document = new Document();
		document.append("name", "Pankaj");
		document.append("creatonDate", new Date());
		document.append("creatonDate", new Date());
		

		col.insertOne(document);

		System.out.println("ID Generated=" + document.getObjectId("_id").toString());
		mongo.close();

		return "Collection inserted";

	}

	/** Some Testing service **/

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String ping() {
		DBObject ping = new BasicDBObject("ping", "1");
		try {
			String answer = mt.getDb().toString();
			String host = mt.getMongoDbFactory().toString();
			return answer + ":" + host;// .getErrorMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}

	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public String ping2() {
		DBObject ping = new BasicDBObject("ping", "1");
		String jsonStr = null;
//		List<User> lst = new ArrayList<>();
		MongoCollection<Document> lst = mt.getCollection("user");
		ObjectMapper om = new ObjectMapper();
		try {
			jsonStr = om.writeValueAsString(lst);
			System.out.println(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonStr;// .getErrorMessage();

	}

	@RequestMapping(value = "/getpets", method = RequestMethod.GET)
	public List<Pets> getAllPets() {
		return petsRepository.findAll();
	}

}
