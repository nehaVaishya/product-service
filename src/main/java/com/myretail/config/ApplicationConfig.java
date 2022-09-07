package com.myretail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.myretail.external.api.ProductDetails;

@Configuration
public class ApplicationConfig {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean 
	public ProductDetails getProductDetails() {
		return new ProductDetails();
	}
	
	@Bean
	public Gson getGson() {
		return new Gson();
	}
}
