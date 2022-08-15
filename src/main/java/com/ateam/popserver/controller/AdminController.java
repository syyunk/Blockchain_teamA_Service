package com.ateam.popserver.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ateam.popserver.dto.ChargeRequestDTO;
import com.ateam.popserver.dto.RespMkBlkDTO;
import com.ateam.popserver.service.ProductService;
import com.ateam.popserver.service.TradeService;
import com.ateam.popserver.service.WalletService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminController {

	private final TradeService tradeService;
	private final WalletService walletService;
	
	//충전 요청목록
	@GetMapping("/admin/chargeRequestList")
	public void list(Model model){
		log.info("충전 요청 리스트");
		model.addAttribute("result", walletService.getChargeList());
		System.out.println("==========================");
		System.out.println(walletService.getChargeList());
	}
	

	//충전 승인 버튼을 눌렀을 때
	@PostMapping("/admin/chargeAccepted")
	public String chargeAccepted(ChargeRequestDTO dto, RedirectAttributes rAttr, //전송하는 dto에 purpose추가
			HttpSession session) {
		System.out.println(dto);
		System.out.println("충전완료!!!!!!!!!!!!!!!!!!");
		log.info("충전 승인");
		//db에 저장 
		RespMkBlkDTO respDTO= walletService.chargeAccept(dto);
		System.out.println(respDTO);
		return "redirect:/admin/chargeRequestList";
	}
}
