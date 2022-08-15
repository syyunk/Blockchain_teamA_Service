package com.ateam.popserver.dto;

import org.springframework.web.multipart.MultipartFile;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

	private String err;
	
	private Long rnum;
	
	private String reply;
	
	private String replyer;
	
	private Long grp;
	
	private Long lev;
	
	
	
}

