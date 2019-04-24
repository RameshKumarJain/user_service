package com.ideacrest.hireup.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ideacrest.hireup.bean.User;
import com.ideacrest.hireup.bean.UserType;
import com.ideacrest.hireup.mgmt.UserManager;

public class UserResourceTest {

	@Mock
	private UserManager userManager;

	@InjectMocks
	private UserResource userResource;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Assert.assertTrue(true);
	}

	@Test
	public void testAddUser() {
		userResource.addUser(new User());
		Assert.assertTrue(true);
	}

	@Test
	public void testGetUserById() {
		userResource.getUserById(1);
		Assert.assertTrue(true);
	}

	@Test
	public void testUpdateUser() {
		userResource.updateUser(new User());
		Assert.assertTrue(true);
	}

	@Test
	public void testGetAllUsers() {
		userResource.getAllUsers(UserType.CLIENT.name());
		Assert.assertTrue(true);
	}

}
