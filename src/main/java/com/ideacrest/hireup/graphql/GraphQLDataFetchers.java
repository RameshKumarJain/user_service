package com.ideacrest.hireup.graphql;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ideacrest.hireup.bean.User;
import com.ideacrest.hireup.dao.UserDao;

import graphql.schema.AsyncDataFetcher;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Singleton
public class GraphQLDataFetchers {

	private Logger LOGGER = LoggerFactory.getLogger(GraphQLDataFetchers.class);

	private UserDao userDao;

	@Inject
	public GraphQLDataFetchers(UserDao userDao) {
		this.userDao = userDao;
	}

	public DataFetcher getUserByIdDataFetcher() {
		return AsyncDataFetcher.async(dataFetchingEnvironment -> {
			LOGGER.info(getExecutionPathFromDataFetchingEnvironment(dataFetchingEnvironment)
					+ " getUserByIdDataFetcher --> fetcher is called - " + System.currentTimeMillis());
			String userId = dataFetchingEnvironment.getArgument("_id");
			User resultData = userDao.getUserById(Long.valueOf(userId));
			return resultData;
		});
	}

	public DataFetcher getUserByUserTypeDataFetcher() {
		return AsyncDataFetcher.async(dataFetchingEnvironment -> {
			LOGGER.info(getExecutionPathFromDataFetchingEnvironment(dataFetchingEnvironment)
					+ " getUserByUserTypeDataFetcher --> fetcher is called - " + System.currentTimeMillis());
			String userType = dataFetchingEnvironment.getArgument("userType");
			List<User> resultData = userDao.getUserByType(userType);
			return resultData;
		});
	}

	public DataFetcher getAllUserDataFetcher() {
		return AsyncDataFetcher.async(dataFetchingEnvironment -> {
			LOGGER.info(getExecutionPathFromDataFetchingEnvironment(dataFetchingEnvironment)
					+ " getAllUserDataFetcher --> fetcher is called - " + System.currentTimeMillis());
			return userDao.getAllUser();
		});
	}

	private String getExecutionPathFromDataFetchingEnvironment(DataFetchingEnvironment dataFetchingEnvironment) {
		return dataFetchingEnvironment.getExecutionStepInfo().getPath().toString();
	}
}
