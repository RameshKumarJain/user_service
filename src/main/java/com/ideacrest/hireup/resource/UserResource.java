package com.ideacrest.hireup.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.ideacrest.hireup.bean.User;
import com.ideacrest.hireup.mgmt.UserManager;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private UserManager userManager;

	@Inject
	public UserResource(UserManager userManager) {
		this.userManager = userManager;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {
		return userManager.addUser(user);

	}

	@GET
	public Response getUserById(@QueryParam("id") long id) {
		return userManager.getUserById(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {
		return userManager.updateUser(user);
	}

	@GET
	@Path("all")
	public Response getAllUsers(@QueryParam("userType") String userType) {
		return userManager.getAllUsers(userType);
	}
}
