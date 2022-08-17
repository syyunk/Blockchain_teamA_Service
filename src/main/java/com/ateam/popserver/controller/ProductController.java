package com.ateam.popserver.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ateam.popserver.dto.MemberDTO;
import com.ateam.popserver.dto.PageRequestDTO;
import com.ateam.popserver.dto.ProductDTO;
import com.ateam.popserver.dto.TradeDTO;
import com.ateam.popserver.model.Member;
import com.ateam.popserver.model.Product;
import com.ateam.popserver.service.MemberService;
import com.ateam.popserver.service.ProductService;
import com.ateam.popserver.service.TradeService;

import antlr.collections.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
//@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
	private final MemberService memberService;
	private final TradeService tradeService;
	


	//메인함수
	@Transactional
	@GetMapping({"/","/main"})
	public String productList(PageRequestDTO pageRequestDTO, Model model) {
		log.info("메인화면 이동");

//			System.out.println("1111111111111"+productService.getList(pageRequestDTO));
		model.addAttribute("result", productService.getList(pageRequestDTO));

		return "/main";
	}
	
	@GetMapping("/product/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info("목록보기");
		model.addAttribute("result", productService.getList(pageRequestDTO));
	};

	@RequestMapping(value = "/product/register", method = RequestMethod.GET)
	public void register(@AuthenticationPrincipal Member member, Model model) {
		log.info("폼 요청...");
		model.addAttribute("dto", member);
	}
	
	@RequestMapping(value = "/product/register", method = RequestMethod.POST)
	public String register(ProductDTO dto, RedirectAttributes rAttr) {

		
		log.info("폼 등록 처리 중...");
		Long pnum = productService.registerProduct(dto);
		rAttr.addFlashAttribute("msg", pnum+"삽입 성공");
		return "redirect:/product/list";
	}
	
	@GetMapping("/product/read")
	//리스트엥 연결된 링크 @{/product/read(pnum=${dto.pnum})}
	public String read(Long pnum, @ModelAttribute("requestDTO")PageRequestDTO requestDTO, Model model) {
//		if (member == null) {
//			return "redirect:/member/login";
//		}

		log.info("상세보기 요청", requestDTO);

		//서비스한테 받아온 결과를 모델을 통해 전달
		ProductDTO dto = productService.readBasic(pnum);
		model.addAttribute("dto", dto);

		MemberDTO tempDTO = MemberDTO.builder().mnum(dto.getMnum()).build();

		model.addAttribute("mdto", tempDTO);

		return "/product/read";
	}
	
	@GetMapping("/product/buy")
	public void buy(Long pnum, @AuthenticationPrincipal Member member, Model model) {
		log.info("구매 전 처리 페이지 요청" + pnum);
		
		//서비스한테 받아온 결과를 모델을 통해 전달

		log.info("pdto ::: ", productService.readBasic(pnum));
		model.addAttribute("dto", productService.read(member.getMnum(), pnum));
		//잔액
		model.addAttribute("balance", member.getWallet().getBalance());

	}

//	
//	@PostMapping("/makeTx")
//	public ResponseEntity<?> makeTx(TxDTO tDto){
//		//블록체인 서버로 요청 JSON으로 보내기.
//		
//		productService.makeTx(){
//			
//		};
//		
//	}
}
