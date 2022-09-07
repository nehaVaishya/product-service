package com.myretail.external.api;

import java.util.Arrays;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.myretail.exception.ProductNotFoundException;
import com.myretail.helper.Constants;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductDetails {

	@Autowired RestTemplate restTemplate;
	
	private HttpEntity <String> setHeader(){
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      return new HttpEntity<String>(headers);
	}
	@Retry(name="default",fallbackMethod="handleRemoteCallServiceDown")
	public String getProductDetail(Long id) throws ProductNotFoundException,ServiceUnavailableException {
	    try {
			String result = restTemplate.exchange(Constants.PRODUCT_NAME_END_POINT + id, HttpMethod.GET,setHeader(),String.class).getBody();
			return result;	    	
	    }
	    catch(HttpClientErrorException ex) {
	    	throw new ProductNotFoundException();
	    }

	}
	// You can send back the fallback response here, For now we are throwing the Service down exception
	// To convey the client.
	public String handleRemoteCallServiceDown(Long id, ServiceUnavailableException ex) throws ServiceUnavailableException {
		log.info("Product details  " + ex.getClass().toString());
		throw new ServiceUnavailableException("Remote Service is temporarily down.");
	}
	
}
