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
		return "제품번호 : " + productId 
				+ ", 제품명 : " + productName 
				+ ", 가격 : " + productPrice;
	}




}
