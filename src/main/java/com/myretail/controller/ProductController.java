package com.myretail.controller;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.myretail.exception.ProductNotFoundException;
import com.myretail.external.api.ProductDetails;
import com.myretail.model.Product;
import com.myretail.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired RestTemplate restTemplate;
	
	@Autowired ProductDetails productDetails;
	
	@Autowired ProductService productService;
	
	@Autowired Gson gson;
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> products(@PathVariable("id") Long id) throws ProductNotFoundException,ServiceUnavailableException,RuntimeException{

		try {
			// GET the product details based on the ID from the external service.
			String result = productDetails.getProductDetail(id);
						
			log.info("RESULT " + result);
			
			JsonObject jsonObject = gson.fromJson(result,JsonObject.class);
			// Expect null pointer exception here, if the desire key is not present in json object
			String productName = jsonObject.get("data").getAsJsonObject()
					.get("product").getAsJsonObject()
					.get("item").getAsJsonObject()
					.get("product_description").getAsJsonObject()
					.get("title").getAsString();
			
			log.info("Product name " + productName);
			
			// Combine the product details and consolidate the required data for response.
			Product product = productService.findProductById(id);	
			if(productName.isEmpty() || product == null) throw new ProductNotFoundException();
				product.setName(productName);
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}
		
		catch(ProductNotFoundException | NullPointerException | JsonSyntaxException | IllegalStateException | ClassCastException | HttpClientErrorException ex ) {
			throw new ProductNotFoundException("No product found with " + id);
		}
		catch(RestClientException ex) {
			throw new ProductNotFoundException("No product found with " + id);
		}
		
	}
	
	@PostMapping()
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		log.info("Create Product");
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable("id") Long id){
		return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductById(id,product));
	}
}
