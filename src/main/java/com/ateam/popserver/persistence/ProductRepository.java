package com.ateam.popserver.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ateam.popserver.model.Member;
import com.ateam.popserver.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	//상품 등록/ 전체조회/ 하나조희/ 삭제 - 상품 올린 회원 - 
	
	//회원정보로 판매중인 상품 가져오는 메서드
	List<Product> findProductByMember(Member member);
	Product findByPnum(Long pnum);
	
	//상품정보 가져올 때 글번호로 회원 정보도 함께 가져오는 메서드 . 입력받은 pnum에 해당하는 데이터 찾아오기
	@Query("select p, w from Product p left join p.member w where p.pnum = :pnum")
	Object getProductWithPnum(@Param("pnum") Long pnum);
	
	
//	@Query(value="select  from Product p left join p.member w",
//			countQuery = "")
//	Page<Object []> getBoardPaging(Pageable pageable)
}
