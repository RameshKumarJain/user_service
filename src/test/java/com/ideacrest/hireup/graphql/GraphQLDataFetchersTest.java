package com.ideacrest.hireup.graphql;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ideacrest.hireup.bean.User;
import com.ideacrest.hireup.dao.UserDao;

import graphql.Scalars;
import graphql.execution.ExecutionPath;
import graphql.execution.ExecutionStepInfo;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class GraphQLDataFetchersTest {

	@InjectMocks
	private GraphQLDataFetchers graphQLDataFetchers;

	@Mock
	private UserDao userDao;

	@Mock
	private DataFetchingEnvironment environment;;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(environment.getExecutionStepInfo()).thenReturn(ExecutionStepInfo.newExecutionStepInfo()
				.type(Scalars.GraphQLString).path(ExecutionPath.rootPath()).build());
	}

	@Test
	public void testGetUserByIdDataFetcher() throws Exception {
		DataFetcher userDataFetchers = graphQLDataFetchers.getUserByIdDataFetcher();
		Mockito.when(environment.getArgument("_id")).thenReturn("1");
		Mockito.when(userDao.getUserById(1)).thenReturn(new User());
		CompletableFuture<User> user = (CompletableFuture<User>) userDataFetchers.get(environment);
		Assert.assertTrue("User should not be null!!", user.get() != null);
	}

	@Test
	public void testGetUserByUserTypeDataFetcher() throws Exception {
		DataFetcher<List<User>> usersDataFetchers = graphQLDataFetchers.getUserByUserTypeDataFetcher();
		Mockito.when(environment.getArgument("_id")).thenReturn("CLIENT");
		Mockito.when(userDao.getUserByType(Mockito.anyString())).thenReturn(Arrays.asList(new User()));
		CompletableFuture<List<User>> userList = (CompletableFuture<List<User>>) usersDataFetchers.get(environment);
		Assert.assertTrue("User should not be null!!", userList.get() != null);
	}

	@Test
	public void testGetAllUserDataFetcher() throws Exception {
		DataFetcher<List<User>> usersDataFetchers = graphQLDataFetchers.getAllUserDataFetcher();
		Mockito.when(userDao.getAllUser()).thenReturn(Arrays.asList(new User()));
		CompletableFuture<List<User>> userList = (CompletableFuture<List<User>>) usersDataFetchers.get(environment);
		Assert.assertTrue("User should not be null!!", userList.get() != null);
	}

}
