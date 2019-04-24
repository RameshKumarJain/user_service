package com.ideacrest.hireup;

import java.util.Optional;

import org.apache.http.client.HttpClient;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ideacrest.app.configuration.ConfigurationProperty;
import com.ideacrest.app.eureka.EurekaServerHelper;
import com.ideacrest.app.mongo.MongoManaged;
import com.ideacrest.hireup.guice.BeanModule;
import com.ideacrest.hireup.helper.EurekaInstaceBeanHelper;
import com.ideacrest.hireup.resource.GraphQLResource;
import com.ideacrest.hireup.resource.UserResource;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.smoketurner.dropwizard.zipkin.ZipkinBundle;
import com.smoketurner.dropwizard.zipkin.ZipkinFactory;

import brave.http.HttpTracing;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class APIApplication extends Application<APIConfiguration> {

	public static void main(String[] args) throws Exception {
		new APIApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<APIConfiguration> bootstrap) {
		// Initialise zipkin
		bootstrap.addBundle(new ZipkinBundle<APIConfiguration>("hire-up-user-service") {
			@Override
			public ZipkinFactory getZipkinFactory(APIConfiguration configuration) {
				return configuration.getZipkinFactory();
			}
		});
	}

	@Override
	public void run(APIConfiguration configuration, Environment environment) throws Exception {

		// build http client
		HttpClient httpClient = getHtpClient(environment);

		// buid zipkin
		Optional<HttpTracing> tracing = configuration.getZipkinFactory().build(environment);

		// build mongo
		MongoDatabase db = buildMongoClient(environment);

		// build guice
		BeanModule beanModule = new BeanModule(environment.getValidator(), httpClient, db);
		Injector injector = Guice.createInjector(beanModule);

		// register service
		initializeAndRegisterEurekaService(environment, injector);

		// register resources
		environment.jersey().register(injector.getInstance(UserResource.class));
		environment.jersey().register(injector.getInstance(GraphQLResource.class));
	}

	private MongoDatabase buildMongoClient(Environment environment) {
//		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
//				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		MongoClient mongoClient = new MongoClient(ConfigurationProperty.MONGO_HOST_PROPERTY.get(),
				ConfigurationProperty.MONGO_PORT_PROPERTY.get());

		MongoManaged mongoManaged = new MongoManaged(mongoClient);
		environment.lifecycle().manage(mongoManaged);
		MongoDatabase db = mongoClient.getDatabase(ConfigurationProperty.MONGO_DB_PROPERTY.get());
		return db;
	}

	private void initializeAndRegisterEurekaService(Environment environment, Injector injector) throws Exception {
		EurekaServerHelper eurekaServerHelper = injector.getInstance(EurekaServerHelper.class);
		environment.lifecycle().manage(eurekaServerHelper);
		eurekaServerHelper.registerEurekaClient(EurekaInstaceBeanHelper.getEurekaInstanceBean());
	}

	public HttpClient getHtpClient(Environment environment) {
		return new HttpClientBuilder(environment).build("RESTClient");
	}
}
