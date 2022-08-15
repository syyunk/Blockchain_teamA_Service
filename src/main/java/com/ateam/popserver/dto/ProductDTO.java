package com.ateam.popserver.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.ateam.popserver.model.Wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private Long pnum;
	private String name;
	private Integer price;
	
	private String thumb;//url - db에서 받아올 때 경로/ html 없는 필드
	private MultipartFile img; //html 의 name input과 이름을 동일하게 해야한다
	
	private String detail;
	private Integer achieved; 
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	//작성자 정보
	private Long mnum;
	private String nickname;
	private Float star;
	
	
	//댓글 수
//	private int replyCount;

	
}
