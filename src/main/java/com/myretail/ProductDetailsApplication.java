package com.myretail;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myretail.model.Product;
import com.myretail.service.ProductService;

@SpringBootApplication
@EnableMongoRepositories
public class ProductDetailsApplication implements CommandLineRunner {
	@Autowired Gson gson;
	@Autowired ProductService productService;
	@Autowired MongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(ProductDetailsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/products.json"));		
		
		Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
		
		List<Product> products = gson.fromJson(reader, listType);
		mongoTemplate.dropCollection("products");
		productService.saveProducts(products);
		
	}

}
