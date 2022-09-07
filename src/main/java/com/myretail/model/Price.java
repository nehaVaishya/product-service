package com.myretail.model;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
	
	private double value;
	
	@SerializedName("currency_code")
	@Field("currency_code")
	@JsonProperty("currency_code")
	private String currencyCode;
	
}
