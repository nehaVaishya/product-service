# Read Me First
This is the product-service microservice which helps in getting the product information.

# Tech stack used:
* Springboot / Java
* MongoDB
* Maven

#Database changes:optional if using your own MongoDBcloud
Replace spring.data.mongodb.uri property with your MongoDB connection url.

#How to run:
* Build using maven (optional)
* Run the jar file with the command:	
	java -jar /pathToYouJarFile -Dspring.profiles.active={env}
	
	example java -jar /Users/neha/Target_Workspace/product-service/target/product-details-service-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=dev