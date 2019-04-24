# user_service

How to start the user_service application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/sample-user_service-1.0.0.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Rest Service
----

1 Get User by id - GET
2 Add User - POST
3 Update User - PUT
4 Get all User by userType - GET

GraphQL service
-----

Single rest points where you query three different types/methods
	
	userById(_id: ID): User
	userByType(userType: UserType): [User]
	users: [User]
