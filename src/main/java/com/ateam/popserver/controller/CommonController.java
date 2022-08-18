package com.ateam.popserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ateam.popserver.dto.PageRequestDTO;
import com.ateam.popserver.service.MemberService;
import com.ateam.popserver.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CommonController {

	
	private final ProductService productService;
	
	//메인함수
	@Transactional
	@GetMapping({"/","/main"})
	public String productList(PageRequestDTO pageRequestDTO, Model model) {
		log.info("메인화면 이동");

//			System.out.println("1111111111111"+productService.getList(pageRequestDTO));
		model.addAttribute("result", productService.getList(pageRequestDTO));

		return "/main";
	}
	
	@GetMapping({"/about"})
	public void about( Model model) {
		log.info("회사소개 화면 이동");
	}
	
	
}
