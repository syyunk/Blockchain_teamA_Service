package com.ateam.popserver.controller;

import java.time.LocalDateTime;
import java.util.Date;
//http://localhost/board/trade
import java.util.List;
import java.util.stream.Collectors;

import com.ateam.popserver.dto.*;
import com.ateam.popserver.model.Member;
import com.ateam.popserver.model.Product;
import com.ateam.popserver.model.Trade;
import com.ateam.popserver.persistence.TradeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ateam.popserver.persistence.WalletRepository;
import com.ateam.popserver.service.MemberService;
import com.ateam.popserver.service.TradeService;
import com.ateam.popserver.service.WalletService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSession;

//데이터를 리턴하기 위한 COntroller를 만들기 위한 어노테이션
@Controller
@Log4j2
@RequiredArgsConstructor
public class TradeController {
	private final TradeRepository tradeRepository;
	private final TradeService tradeService;
	private final WalletService walletService;
	private final MemberService memberService;

	//tradeComfir, test데이터 생성용 페이지
	@GetMapping("/myPage/trade")
//		@GetMapping("/main")
	public void createTrade(@AuthenticationPrincipal Member member, Long smnum) {
		log.info("거래저장으로 이동");

		log.info("------------" + smnum);
	}

	//tradeComfir, test데이터 생성용 페이지
	@PostMapping("/myPage/trade")
	public String createTrade(TradeDTO dto, RedirectAttributes redirectAttributes) {
		dto.setConfirm(0);
		dto.setDate(LocalDateTime.now());
		log.info("거래 저장 중.." + dto);
		System.out.println("+++++++"+dto);
//			Long tNum = tradeService.buyConfirm(dto);
		tradeService.createTrade(dto);
		//View에 데이터 전달
		redirectAttributes.addFlashAttribute("msg 삽입");

		return "redirect:/myPage/trade";
	}

	//구매확정버튼 클릭 시 거래를 DB에 저정 처리할 메서드
	@GetMapping("/myPage/tradeConfirm")
	public void createTradeConfirm(PageRequestDTO pageRequestDTO, Model model) {
		log.info("목록 보기.......");

		System.out.println("~~~~~~~~~~~~~~~~~~~"+tradeService.getList(pageRequestDTO));
		model.addAttribute("result", tradeService.getList(pageRequestDTO));

	}

	@PostMapping("/myPage/tradeConfirm")
	public String createTradeConfirm(TradeDTO dto) {

		System.out.println("dto~~~~~~~~~~~~~~~~~~~~~~~"+dto);
		RespMkBlkDTO respDto = tradeService.buyConfirm(dto);
//				   System.out.println("dto22~~~~~~~~~~~~~~~~~~~~~"+respDto);
//				   log.info("넘어온 파라미터는 ?????");
		//System.out.println("넘어온값은:" + dto);
		//tradeService.comfirm();

		//업데이트분 tnum(PK)이 조건절이 되어서 모든 필드값을 업데이트해준다.
		if(respDto != null) {
			System.out.println("update 때렸어요");
			Product product = Product.builder().pnum(dto.getPnum()).build();
			Trade t = Trade.builder()
					.tnum(dto.getTnum())
					.bWallet(dto.getBwallet())
					.sWallet(dto.getSwallet())
					.txid(respDto.getTxid())
					.product(product)
					.date(LocalDateTime.now())
					.total(dto.getTotal())
					.confirm(1)		//구매확정이기에 0 -> 1로 변경
					.build();
			tradeRepository.save(t);
		}

		return "redirect:/myPage/tradeConfirm";
	}




	//블록 조회
	@GetMapping("/myPage/getBlock")
	@ResponseBody
	public GetBlkDTO getBlock(String txid) throws Exception {
		GetBlkDTO respDTO = tradeService.getBlock(txid);
//				respDTO.setTimestamp(respDTO.getTimestamp())

		return respDTO;
	}

	//tx 조회
	@GetMapping("/myPage/getTx")
	@ResponseBody
	public GetTxDTO getTx(String txid, HttpSession httpSession,
						  RedirectAttributes redirectAttributes) throws Exception {
		GetTxDTO respDTO = tradeService.getTx(txid);

		return respDTO;
	}

	//구매확정버튼 클릭시
	@GetMapping("/board/tradeBlock")
	public void createTradeBlock() {

	}
	//충전요청
	@GetMapping("/wallet/chargeRequest")
	public void chargeRequest(String walletid, Model model) {
		log.info("충전 요청중...");

		WalletDTO w = walletService.read(walletid);
//			memberService.
		System.out.println(w);
		model.addAttribute("wDto",w);
		model.addAttribute("walletid",walletid);

		model.addAttribute("mDto",memberService.getMemberByWalletId(walletid));
	}
	//		@GetMapping("/product/charge")
//		public void charge(String walletid, Model model) {
//			WalletDTO w = walletService.read(walletid);
//			model.addAttribute("wallet",w);
////			TradeDTO t = TradeDTO.builder()
//		}
	@PostMapping("/wallet/chargeRequest")
	public String chargeRequest(ChargeRequestDTO dto) {
		log.info("충전요청으로 이동");
		//월렛 저장
		walletService.chargeRequest(dto);
		return "redirect:/product/list";

		//트레이드 에는 충전 승인후 저장

	}

}
