package com.ateam.popserver;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.ateam.popserver.dto.PageRequestDTO;
import com.ateam.popserver.dto.PageResponseDTO;
import com.ateam.popserver.dto.ProductDTO;
import com.ateam.popserver.dto.TradeDTO;
import com.ateam.popserver.dto.WalletDTO;
import com.ateam.popserver.model.Product;
import com.ateam.popserver.persistence.WalletRepository;
import com.ateam.popserver.service.ProductService;
import com.ateam.popserver.service.WalletService;

@SpringBootTest
public class ServiceTest {

	@Autowired
	private ProductService productService;
	
	//
	//상품등록
	//@Test
	@Transactional
	public void testRegisterProduct() {
		ProductDTO dto = ProductDTO.builder()
				.name("DTO와서비스")
				.price(1000)
				.thumb("썸네일")
				.detail("디테일")
				.achieved(0)
				.mnum(3L) //테스트니까 아무거나 넣어
				.star(5.0F)
				.build();
		System.out.println(dto);
		System.out.println(productService.registerProduct(dto));
	}
	//하나조회
	//@Test
	@Transactional
	@Commit
	public void testGetProduct() {
		System.out.println();
		ProductDTO dto = ProductDTO.builder()
				.pnum(1L)
				.build();
		System.out.println(dto);
		System.out.println(productService.getProduct(dto));
		
	}
	
	//전체조회
	//@Test
	@Transactional
	@Commit
	public void testList() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
		PageResponseDTO<ProductDTO, Product> resultDTO = productService.getList(pageRequestDTO);
		for(ProductDTO productDto : resultDTO.getDtoList()) {
			System.out.println(productDto);
		}
	}
	//목록과 페이지 번호
	//@Test
	@Transactional
	@Commit
	public void testList2() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
		PageResponseDTO<ProductDTO, Product> resultDTO = productService.getList(pageRequestDTO);
		for(ProductDTO productDto : resultDTO.getDtoList()) {
			System.out.println(productDto);
		}
		//
		System.out.println("이전: "+ resultDTO.isPrev());
		System.out.println("이전: "+ resultDTO.isNext());
		System.out.println("전체 페이지 수 :"+resultDTO.getTotalPage());
		System.out.println("번호 목록 :"+resultDTO.getPageList());
		
	}
	
	@Autowired
	private WalletService walletService;
	
	//지갑생성
	//@Test
	@Transactional
	@Commit
	public void 지갑충전() {
		WalletDTO dto = WalletDTO.builder()
				.walletid("w155afnjd2mcdk42")
				.balance(10000000)
				.build();
		System.out.println(dto);
		System.out.println("==============");
		System.out.println(walletService.initialization(dto));
	}
	//지갑조회
	//@Test
	@Transactional
	@Commit
	public void 지갑조회() {
		System.out.println(walletService.read("1MQZcwiqUddd3ND6me9vXLohbfNexbx6Es"));
	}
	
	//충전
	//@Test
	@Transactional
	@Commit
	public void 충전트레이드() {
		
	}
	
	
	
	
	

}
