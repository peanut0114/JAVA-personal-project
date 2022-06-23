package com.yedam.app.board;

import com.yedam.app.common.Management;
import com.yedam.app.product.Product;

public class ReviewManagement extends Management{

	public ReviewManagement() {
		while (true) {
			//리뷰출력
			ReviewPrint();
			//1.후기등록 2.댓글작성 3.뒤로가기 9.홈
			menuPrint();
			int menuNo = menuSelect();

			if (menuNo == 1) {
				//후기등록
			} else if (menuNo == 2) {
				//댓글작성
				new 
			} else if (menuNo == 3) {
				//뒤로가기
				break;
			} else if (menuNo == 9) {
				// 홈
				new Management();
			} else {
				// 입력오류
				showInputError();
			}
			System.out.println();
		} 
	}

	private void ReviewPrint() {
		List<Comment> list = cDAO.selectAll();
		for(Comment info : list) {
			System.out.println(info);
		}
	}

	@Override
	protected int menuSelect() {
		System.out.println("상품번호 선택 > ");
		int productId = Integer.parseInt(sc.nextLine());
		Product product = pDAO.selectOne(productId);
		if(product==null) {
			System.out.println("해당 쿠키가 존재하지 않습니다.");
			return 0;
		}
		return productId;
		
	}

	@Override
	protected void menuPrint() {
		
	}
	
}
