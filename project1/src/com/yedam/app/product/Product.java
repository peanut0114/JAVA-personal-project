package com.yedam.app.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	// 필드
	private int productId;
	private String productName;
	private int productPrice;
	private String productExplain;
	
	@Override
	public String toString() {
		return productId +". " + productName+" (" + productPrice
				+"원)\n   ★★"+productExplain+"★★";
	}




}
