package com.myretail.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myretail.exception.ProductNotFoundException;
import com.myretail.model.Product;

/* 
 * All the required services methods, that should be implemented by the product service impl class.
 *
 */
@Service
public interface ProductService {
	
	List<Product> getAllProducts();
	Product findProductById(Long id);
	Product saveProduct(Product product);
	List<Product>saveProducts(List<Product> products);
	Product updateProductById(Long id, Product product) throws ProductNotFoundException;
	Long deleteProductById(Long id);
	
}
