package com.yedam.app.order;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderInfo {
	// 필드
	private Date dealDate;
	private int productId;
	private int dealAmount;
	private String ordererId;
	private Date shipmentDate;
	private boolean conditon; // 0 :발송준비중, 1:발송완료
	private String productName;

	@Override
	public String toString() {
		return dealDate + "  " + productId + ". " + productName
				+ ", 주문량 : " + dealAmount + ", 발송예정일 : "+ shipmentDate;
		
	}
}
