package com.ideacrest.hireup.graphql;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.IOException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Singleton
public class GraphQLProvider {

	private GraphQL graphQL;

	private GraphQLDataFetchers graphQLDataFetchers;

	@Inject
	public GraphQLProvider(GraphQLDataFetchers graphQLDataFetchers) throws IOException {
		this.graphQLDataFetchers = graphQLDataFetchers;
		URL url = Resources.getResource("schema.graphqls");
		String sdl = Resources.toString(url, Charsets.UTF_8);
		GraphQLSchema graphQLSchema = buildSchema(sdl);
		this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
	}

	public GraphQL graphQL() {
		return graphQL;
	}

	private GraphQLSchema buildSchema(String sdl) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
	}

	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type(newTypeWiring("Query").dataFetcher("userById", graphQLDataFetchers.getUserByIdDataFetcher())
						.dataFetcher("userByType", graphQLDataFetchers.getUserByUserTypeDataFetcher())
						.dataFetcher("users", graphQLDataFetchers.getAllUserDataFetcher()))
				.build();
	}
}
