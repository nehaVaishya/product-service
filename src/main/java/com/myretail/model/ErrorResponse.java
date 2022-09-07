package com.myretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

	@JsonProperty("status_code")
	private int statusCode;
	private String message;
	
	public ErrorResponse() {
		// TODO Auto-generated constructor stub
	}

}
