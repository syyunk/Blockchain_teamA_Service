package com.ateam.popserver;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ateam.popserver.model.ChargeRequest;
import com.ateam.popserver.persistence.ChargeRequestRepository;
import com.ateam.popserver.persistence.WalletRepository;
import com.ateam.popserver.service.WalletService;

@SpringBootTest
public class JTest {

	@Autowired
	private ChargeRequestRepository chargeRequestRepository;
	
	@Test
	@Transactional
	@Commit
	void 충전요청리스트로저장 () {
		ChargeRequest c = ChargeRequest.builder()
				.walletid("1QDWrZtg1BU9oBCZ9CWqHJUrmTwviix9ab")
				.balance(0)
				.amount(1000)
				.confirm(0)
				.build();
		chargeRequestRepository.save(c);
	}
	
	//@Test
	void id로찾기 () {
		List<ChargeRequest> clist= chargeRequestRepository.findByWalletidOrderByCnum("w202afnjd2mcdk42");
		System.out.println(clist.get(0));
	}
	
	//@Test
	public void PK로가져오기 () {
		System.out.println("========================");
		ChargeRequest c = chargeRequestRepository.findById(1L).get();		
		if(c!=null) {
			System.out.println(c);
		}else {
			System.out.println("존재하지 않는 데이터입니다.");
		}
	}

	//서비스
	@Autowired 
	private WalletService walletService;
	//충전 요청 리스트 가져오기
	@Test
	@Transactional
	@Commit
	void get충전요청리스트() {
//		List<ChargeRequest> list = chargeRequestRepository.findAll();
//		System.out.println(list);
		System.out.println(walletService.getChargeList());
	}
	//
	//@Test
	@Transactional
	@Commit
	void 충전컨펌한후DB저장하고_값리셋() {
		ChargeRequest c = ChargeRequest.builder()
				.cnum(1L)
				.walletid("w202afnjd2mcdk42")
				.balance(0)
				.confirm(1)
				.build();
		chargeRequestRepository.save(c);
	}
//	
	//@Test
	@Transactional
	@Commit
	void 지갑주소로지갑가져오기() {
		String walletid = "";
	}
	
}
