package com.ateam.popserver.controller;

import com.ateam.popserver.dto.MemberDTO;
import com.ateam.popserver.model.Member;
import com.ateam.popserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {
	private final MemberService memberService;

	//삽입 화면으로 이동하는 요청을 처리
	@GetMapping("member/register")
	public void register() {
	   log.info("데이터 삽입화면 요청************");
	  }
	
	@PostMapping("member/register")
	public String registerMember(@Valid MemberDTO dto, Errors errors, Model model,
								 HttpSession session, RedirectAttributes rttr) {
		   log.info("데이터 삽입처리 요청!!!!!!!");
		   log.info("넘어온 파라미터는 ?????"+dto.toString());

		   if (errors.hasErrors()) {            
			   /* 회원가입 실패시 입력 데이터 값을 유지 */            
			   model.addAttribute("MemberDto", dto);
			   
			   /* 유효성 통과 못한 필드와 메시지를 핸들링 */            
			   Map<String, String> validatorResult = memberService.validateHandling(errors);            
			   for (String key : validatorResult.keySet()) {                
				   model.addAttribute(key, validatorResult.get(key));            
			   }
			   //회원가입 페이지로 다시 리턴
			   return "/member/register";
		   }
		   //삽입
		   memberService.registerMember(dto);

		   return "redirect:/main";
		   
	   }

	@GetMapping("/member/login")
	public void login() {
		log.info("로그인폼 요청*************");
	}
	
	@GetMapping("myPage/mypage")
	public void mypage() {
		log.info("mypage폼 요청*************");
	}
	
	@PostMapping("myPage/modify")
	public void mypageProcess(@AuthenticationPrincipal Member member) {
		log.info("mypage POST 요청 *************");
		
		

	}
	
	
	
	
	
}
