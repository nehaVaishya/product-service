package com.myretail.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.myretail.model.Price;
import com.myretail.model.Product;
import com.myretail.repository.ProductRepository;


@SpringBootTest()
@AutoConfigureMockMvc
public class ProductServiceTest {

	
	@Mock
	ProductRepository productRepository;
	
	@Autowired ProductService productService;
	
	@Test
	public void findProductById_WhenId_IsValid_Success() throws Exception{
		
		Product product = new Product();
		product.setId(13860428l);
		product.setName(null);
		product.setCurrentPrice(new Price(13.49,"USD"));
		Optional<Product> op = Optional.of(product);
		Mockito.when(productRepository.findById(13860428l)).thenReturn(op);
		
		Product currentProduct = productService.findProductById(13860428l);
		assertEquals(op.get(), currentProduct);
		
	}
	
	
	
}
