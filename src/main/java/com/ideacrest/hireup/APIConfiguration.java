package com.ideacrest.hireup;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smoketurner.dropwizard.zipkin.ConsoleZipkinFactory;
import com.smoketurner.dropwizard.zipkin.ZipkinFactory;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

public class APIConfiguration extends Configuration {

	@Valid
	@NotNull
	private HttpClientConfiguration httpClient = new HttpClientConfiguration();

	@JsonProperty("httpClient")
	public HttpClientConfiguration getHttpClientConfiguration() {
		return httpClient;
	}

	@JsonProperty("httpClient")
	public void setHttpClientConfiguration(HttpClientConfiguration httpClient) {
		this.httpClient = httpClient;
	}

	@Valid
	@NotNull
	public final ZipkinFactory zipkin = new ConsoleZipkinFactory();

	@JsonProperty
	public ZipkinFactory getZipkinFactory() {
		return zipkin;
	}
}
