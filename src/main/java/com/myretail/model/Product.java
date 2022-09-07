package com.myretail.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	@Id
	private Long id;
	
	@Transient
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private String name;
	
	@Field("current_price")
	@SerializedName("current_price")
	@JsonProperty("current_price")
	private Price currentPrice;
	
}
