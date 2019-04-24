package com.ideacrest.hireup.mgmt;

import java.util.List;

import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ideacrest.app.validator.BeanValidatorUtility;
import com.ideacrest.hireup.bean.User;
import com.ideacrest.hireup.dao.UserDao;

@Singleton
public class UserManager {

	private UserDao userDao;

	private BeanValidatorUtility beanValidatorUtility;

	@Inject
	public UserManager(UserDao userDao, BeanValidatorUtility beanValidatorUtility) {
		this.userDao = userDao;
		this.beanValidatorUtility = beanValidatorUtility;
	}

	public Response getUserById(long id) {
		User userInDB = userDao.getUserById(id);
		if (userInDB == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User with id : " + id + " does not exist")
					.build();
		}
		return Response.status(Response.Status.OK).entity(userInDB).build();
	}

	public Response getAllUsers(String userType) {
		List<User> users;
		if (userType == null) {
			users = userDao.getAllUser();
		} else {
			users = userDao.getUserByType(userType);
		}
		return Response.status(Response.Status.OK).entity(users).build();
	}

	public Response addUser(User user) {

		List<String> validationMessage = beanValidatorUtility.validateBean(user);
		if (validationMessage.size() > 0) {
			return validationErrorResponse(validationMessage);
		}
		return addUserAndGetResponse(user);

	}

	public Response updateUser(User user) {

		List<String> validationMessage = beanValidatorUtility.validateBean(user);
		if (validationMessage.size() > 0) {
			return validationErrorResponse(validationMessage);
		}
		return updateUserAndGetResponse(user);

	}

	private Response addUserAndGetResponse(User user) {
		User userInDB = userDao.getUserById(user.get_id());
		if (userInDB == null) {
			userDao.insertOne(user);
			return Response.status(Response.Status.OK).entity(user.get_id()).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("User with id : " + user.get_id() + " already exist")
				.build();
	}

	private Response updateUserAndGetResponse(User user) {
		User userInDB = userDao.getUserByIdAndType(user.get_id(), user.getUserType().name());
		if (userInDB == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("User with id : " + user.get_id() + " does not exist").build();
		}
		userDao.updateOne(user, user.get_id());
		return Response.status(Response.Status.OK).entity(user.get_id()).build();
	}

	private Response validationErrorResponse(List<String> validationMessage) {
		return Response.status(Response.Status.BAD_REQUEST).entity(validationMessage).build();
	}

}
