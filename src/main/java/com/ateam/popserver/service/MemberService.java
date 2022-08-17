package com.ateam.popserver.service;

import com.ateam.popserver.dto.MemberDTO;
import com.ateam.popserver.model.Member;
import org.springframework.validation.Errors;

import java.util.Map;

public interface MemberService {
	
	//데이터 삽입
	public Map<String, String> validateHandling(Errors error);
	public String[] registerMember(MemberDTO dto);
	public MemberDTO getMember(MemberDTO memberDTO);
	public MemberDTO getMemberByWalletId(String walletid);
	public String updateMember(MemberDTO dto);
	public String deleteMember(MemberDTO dto);
	//mnum으로 DTO 반환
	public MemberDTO getMemberDto(Long mnum);
	public MemberDTO getMemberByMnum(Long mnum);

	//DTO객체를 Entity로 변환해주는 메소드
	//클라이언트 정보로 데이터베이스 변환을 수행하기 위해 사용
	public default Member dtoToEntity(MemberDTO dto) {
		Member member=Member.builder()
				.mnum(dto.getMnum())
				.mid(dto.getMid())
				.pw(dto.getPw())
				.nickname(dto.getNickname())
				.tel(dto.getTel())
				.addr(dto.getAddr())
				.wallet(dto.getWallet())	
				.build();
		return member;
	}
	
	//Entity 객체를 dto객체로 변환해주는 메소드
	//조회할 때 사용
	public default MemberDTO entityToDto(Member member) {
		MemberDTO dto=MemberDTO.builder()
				.mnum(member.getMnum())
				.mid(member.getMid())
				.pw(member.getPw())
				.nickname(member.getNickname())
				.tel(member.getTel())
				.addr(member.getAddr())
				.wnum(member.getWallet().getWnum())
				.build();
		return dto;
	}
	
}
