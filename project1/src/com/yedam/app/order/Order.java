package com.yedam.app.order;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Order {
	// 필드
	private int orderNum;
	private Date dealDate;
	private int productId;
	private int dealAmount;
	private String ordererId;
	private int orderPrice;
	private Date shipmentDate;
	private int condition; // 0 :발송준비중, 1:발송완료
	private String productName;
	
	@Override
	public String toString() {
		String con="";
		if(condition==0) {
			con = "○";
		}else {
			con = "●";
		}
		return orderNum+" "+dealDate + "  " + productId + "." + productName
				+ "(" + dealAmount+"개)" 
				+ " 상태:"+con
				+ "\n   └ 결제 금액 : "+ orderPrice +"원"
				+ "\n   └ 발송예정일 : "+ shipmentDate;
		
	}
}
