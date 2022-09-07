package com.myretail.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretail.exception.ProductNotFoundException;
import com.myretail.model.Product;
import com.myretail.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired ProductRepository productRepo;
	
	@Override
	public List<Product> getAllProducts() {
		
		return productRepo.findAll();
	}

	@Override
	public Product findProductById(Long id) {
		// TODO Auto-generated method stub
		Optional<Product> product = productRepo.findById(id);
		if(product.isPresent()) return product.get();
		return null;
	}

	@Override
	public Product saveProduct(Product product) {
		
		return productRepo.save(product);
	}

	@Override
	public List<Product> saveProducts(List<Product> products) {
		
		return productRepo.saveAll(products);
	}

	@Override
	public Product updateProductById(Long id, Product currentProduct) {
		Product product = findProductById(id);
		if(product == null) {
			throw new ProductNotFoundException("No product found with " + id);
		}
		product.setCurrentPrice(currentProduct.getCurrentPrice());
		return saveProduct(product);
	}

	@Override
	public Long deleteProductById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
