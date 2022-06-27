package com.yedam.app.order;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Order {
	// 필드
	private Date dealDate;
	private int productId;
	private int dealAmount;
	private String ordererId;
	private Date shipmentDate;
	private boolean condition; // 0 :발송준비중, 1:발송완료
	private String productName;
	
	@Override
	public String toString() {
		String con="";
		if(condition) {
			con = "발송완료";
		}else {
			con = "발송준비중";
		}
		return dealDate + "  " + productId + "." + productName
				+ "(" + dealAmount+"개)" 
				+ "\n └ 발송예정일 : "+ shipmentDate+" ("+con+")";
		
	}
}
