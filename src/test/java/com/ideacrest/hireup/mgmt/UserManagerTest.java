package com.ideacrest.hireup.mgmt;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ideacrest.app.validator.BeanValidatorUtility;
import com.ideacrest.hireup.bean.User;
import com.ideacrest.hireup.bean.UserType;
import com.ideacrest.hireup.dao.UserDao;

public class UserManagerTest {

	@Mock
	private UserDao userDao;

	@Mock
	private BeanValidatorUtility beanValidatorUtility;

	@InjectMocks
	private UserManager userManager;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUserByIdWithInvalidId() {
		Mockito.when(userDao.getUserById(Mockito.anyLong())).thenReturn(null);
		Response result = userManager.getUserById(12);
		int expectedStatusCode = Status.NOT_FOUND.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testGetUserByIdWithValidId() {
		Mockito.when(userDao.getUserById(Mockito.anyLong())).thenReturn(new User());
		Response result = userManager.getUserById(1);
		int expectedStatusCode = Status.OK.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testGetAllUserWithNullUserType() {
		Mockito.when(userDao.getAllUser()).thenReturn(Arrays.asList(new User()));
		Response result = userManager.getAllUsers(null);
		int expectedStatusCode = Status.OK.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testGetAllUserWithUserType() {
		Mockito.when(userDao.getUserByType(Mockito.anyString())).thenReturn(Arrays.asList(new User()));
		Response result = userManager.getAllUsers(UserType.FREELANCER.name());
		int expectedStatusCode = Status.OK.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testAddUserWithInvalidPayload() {
		Mockito.when(beanValidatorUtility.validateBean(Mockito.any(User.class)))
				.thenReturn(Arrays.asList("Invalid name"));
		Response result = userManager.addUser(new User());
		int expectedStatusCode = Status.BAD_REQUEST.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testAddUserWithExistingId() {
		Mockito.when(beanValidatorUtility.validateBean(Mockito.any(User.class))).thenReturn(new ArrayList<String>());
		Mockito.when(userDao.getUserById(Mockito.anyInt())).thenReturn(new User());
		Response result = userManager.addUser(new User());
		int expectedStatusCode = Status.BAD_REQUEST.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testAddUserWithValidId() {
		Mockito.when(beanValidatorUtility.validateBean(Mockito.any(User.class))).thenReturn(new ArrayList<String>());
		Mockito.when(userDao.getUserById(Mockito.anyInt())).thenReturn(null);
		Mockito.doNothing().when(userDao).insertOne(Mockito.any(User.class));
		Response result = userManager.addUser(new User());
		int expectedStatusCode = Status.OK.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testUpdateUserWithInvalidPayload() {
		Mockito.when(beanValidatorUtility.validateBean(Mockito.any(User.class)))
				.thenReturn(Arrays.asList("Invalid name"));
		Response result = userManager.updateUser(new User());
		int expectedStatusCode = Status.BAD_REQUEST.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testUpdateUserWithNonExistingId() {
		Mockito.when(beanValidatorUtility.validateBean(Mockito.any(User.class))).thenReturn(new ArrayList<String>());
		Mockito.when(userDao.getUserByIdAndType(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
		User user = new User();
		user.setUserType(UserType.CLIENT);
		Response result = userManager.updateUser(user);
		int expectedStatusCode = Status.NOT_FOUND.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}

	@Test
	public void testUpdateUserWithValidId() {
		Mockito.when(beanValidatorUtility.validateBean(Mockito.any(User.class))).thenReturn(new ArrayList<String>());
		Mockito.when(userDao.getUserByIdAndType(Mockito.anyInt(), Mockito.anyString())).thenReturn(new User());
		Mockito.doNothing().when(userDao).updateOne(Mockito.any(User.class), Mockito.any());
		User user = new User();
		user.setUserType(UserType.CLIENT);
		Response result = userManager.updateUser(user);
		int expectedStatusCode = Status.OK.getStatusCode();
		int actualStatusCode = result.getStatus();
		Assert.assertTrue(
				"Invalid status code.\nExpected: " + expectedStatusCode + "\n Actual code: " + actualStatusCode,
				expectedStatusCode == actualStatusCode);
		Assert.assertTrue("Response entity should not be null.", result.getEntity() != null);
	}
}
