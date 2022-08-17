package com.ateam.popserver.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.ateam.popserver.model.Wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long 	mnum;

    private Long	wnum;
    private String walletid;
    private Wallet wallet;
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String	mid;


    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,16}", message = "6~16자 영문, 숫자, 특수문자를 모두 사용하세요.")
    private String	pw;		//?=.*\\W <-특수문자 최소 한 개 입력

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String	nickname;

    @Pattern(regexp = "^(01[016789]{1})\\-\\d{3,4}\\-\\d{4}$", message = "올바른 연락처를 입력하세요.")
    private String	tel;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String	addr;
    private float	star;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "지갑 이름은 특수문자를 제외한 2~10자리여야 합니다.")
    private String alias;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private LocalDateTime lastlogindate;

}
