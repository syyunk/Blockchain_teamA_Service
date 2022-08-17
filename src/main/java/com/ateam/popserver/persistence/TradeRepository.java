package com.ateam.popserver.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ateam.popserver.model.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long>{

	Trade findBySwallet(String swallet);
	Trade findByBwallet(String bwallet);
	
	
	//내가 충전+판매한 내역 조회
	@Query(value="select * from Trade t where t.swallet = :swallet", nativeQuery = true)
	List<Trade> getListWithMember(@Param("swallet") String swallet);
	
	//내가 구매한 내역 조회
	@Query(value="select * from Trade t where t.bwallet = :bwallet", nativeQuery = true)
	List<Trade> getListWithMemberBuy(@Param("bwallet") String bwallet);
}
