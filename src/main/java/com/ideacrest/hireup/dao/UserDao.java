package com.ideacrest.hireup.dao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import org.bson.conversions.Bson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ideacrest.app.mongo.MongoService;
import com.ideacrest.hireup.bean.User;
import com.mongodb.client.MongoDatabase;

@Singleton
public class UserDao extends MongoService<User> {

	public static final String USER_COLLECTION_NAME = "users";

	public static final String ID = "_id";

	public static final String USER_TYPE = "userType";

	@Inject
	public UserDao(MongoDatabase db, ObjectMapper objectMapper) {
		super(db, objectMapper, USER_COLLECTION_NAME, User.class);
	}

	public User getUserById(long id) {
		return findOneByKey(eq(ID, id));
	}

	public User getUserByIdAndType(long id, String type) {
		Bson query = and(eq(ID, id), eq(USER_TYPE, type));
		return findOneByKey(query);
	}

	public List<User> getAllUser() {
		return find();
	}

	public List<User> getUserByType(String type) {
		return findByKey(eq(USER_TYPE, type));
	}

	public void addUser(User user) {
		insertOne(user);
	}

	public void updateUser(User user) {
		updateOne(user, user.get_id());
	}

}
