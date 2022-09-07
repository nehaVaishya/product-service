package com.myretail.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;
import com.myretail.exception.ProductNotFoundException;
import com.myretail.model.Price;
import com.myretail.model.Product;
import com.myretail.service.ProductService;


@SpringBootTest()
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
    Gson mapper;

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	ProductService productService;
	
	@Test
	public void getProductById_WhenId_IsValid_Success() throws Exception{
		String uri = "/products/13860428";
		Product product = new Product();
		product.setId(13860428l);
		product.setName("The Big Lebowski (Blu-ray)");
		product.setCurrentPrice(new Price(13.49,"USD"));
		Mockito.when(productService.findProductById(13860428l)).thenReturn(product);
		mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void productNotFound_WhenId_IsInvalid() throws Exception {
		
		Mockito.when(productService.findProductById(Mockito.anyLong())).thenReturn(null);
	    long id = 1222;
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/products/" + id)
	            .accept(MediaType.APPLICATION_JSON);
	    mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isNotFound())
	    	   .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
	    	   .andExpect(result -> assertEquals("No product found with " + id, result.getResolvedException().getMessage()));
	}
	
	@Test
	public void updateProductWithID_Success() throws Exception {
		
		String expectedJson = "{\"id\":13860428,\"name\":null,\"current_price\":{\"value\":18.0,\"currency_code\":\"EURO\"}}";
		
		Product product = Product.builder()
				.id(13860428l)
				.currentPrice(new Price(18,"EURO")).build();
		Mockito.when(productService.updateProductById(Mockito.anyLong(), Mockito.any())).thenReturn(product);
		
	    
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/products/13860428")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.toJson(product));
		
		MockHttpServletResponse response = mockMvc.perform(mockRequest)
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
	    
        assertEquals(expectedJson, response.getContentAsString());
	}
	
}
