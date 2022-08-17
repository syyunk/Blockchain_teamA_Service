package com.ateam.popserver.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.popserver.model.Member;
import com.ateam.popserver.model.Wallet;

public interface MemberRepository extends JpaRepository<Member, Long>{

	Member findByWallet(Wallet wallet);//????

	//회원가입시 중복체크 위한 메소드
	Member findByNickname(String nickname);

	Member findByMid(String mid);

	Optional<Member> findMemberByMid(String mid);
}
