package com.ideacrest.hireup.guice;

import javax.validation.Validator;

import org.apache.http.client.HttpClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.mongodb.client.MongoDatabase;

@Singleton
public class BeanModule extends AbstractModule {

	private Validator validator;

	private HttpClient httpClient;
	
	private MongoDatabase db;

	public BeanModule(Validator validator, HttpClient httpClient, MongoDatabase db) {
		this.validator = validator;
		this.httpClient = httpClient;
		this.db = db;
	}

	@Override
	protected void configure() {
		bind(Validator.class).toInstance(validator);
		bind(HttpClient.class).toInstance(httpClient);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		bind(ObjectMapper.class).toInstance(objectMapper);
		bind(MongoDatabase.class).toInstance(db);
	}
}
