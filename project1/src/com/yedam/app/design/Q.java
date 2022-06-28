package com.yedam.app.design;

public class Q {
/*

 
 --------------------------
 문제 해결 내역
 
 1. 부모 메소드 A를 사용할때 A 안에서 불러오는 또다른 메소드 B만 자식 클래스에서 오버라이딩해서 쓸 수 없다!
 ->> 부모클래스에선 자식클래스의 메소드에 접근 불가 
 
 2. 로그인 저장 정보엔 비번/아이디만 담아둠 ->> selectOne으로 모든 값 받아올것
 ------------------------------
 
 페이지네이션을 위한 숫자 리스트 생성기
 *
 * 사용 방법:
 * 1. 인스턴스 생성: new Paging(Integer pagesPerBlock, Integer postsPerPage, Long totalPostCount)
 *  - pagesPerBlock = 한 블럭당 들어갈 페이지 개수 (예: 5인 경우 한 블럭에 [1 2 3 4 5] 등으로 표시)
 *  - postsPerPage = 페이지 하나 당 보여지는 Post(row)의 개수
 *  - totalPostCount = 테이블에 등록된 총 Post 개수
 *  - 위의 변수들은 setter/getter를 가지고 있습니다.
 *
 * 2. getTotalLastPageNum() 으로 총 페이지 개수를 확인합니다.
 *
 * 3. getFixedBlock(Integer currentPageNum) 또는 getElasticBlock(Integer currentPageNum)으로 페이지 리스트 생성합니다.
 *  - currentNum = 현재 페이지
 *  - 결과는 Map<String, Object> 형태로 반환되며, pageList키의 값이 페이지 리스트입니다.
 *  - 예) {totalPostCount=5555, isPrevExist=false, isNextExist=true, blockLastPageNum=11, postsPerPage=12,
 *        totalLastPageNum=462, currentPageNum=1, pagesPerBlock=11, pageList=[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
 *        blockFirstPageNum=1}
 *
 * 4. getFixedBlock()은 현재 페이지 위치가 항상 고정되어 있으며, getElasticBlock()은 현재 페이지가 가능하면 블럭의 한가운데 위치하도록 합니다.
 *  - 예) 현재페이지가 6이고 블럭당 페이지 수가 5인 경우 getFixedBlock()에서는 [6, 7, 8, 9, 10]로 표시되지만,
 *       getElasticBlock()인 경우 [4, 5, 6, 7, 8]로 표시됩니다.
 *  - getElasticBlock()은 pagesPerBlock이 홀수일 때에만 사용할 수 있습니다.
 *
 */
}
