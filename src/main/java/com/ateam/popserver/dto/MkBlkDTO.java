package com.ateam.popserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//controller와 service 사이 데이터 전달 위한 클래스
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MkBlkDTO {
	private String 			From;
	private String			To;
	private String			Purpose;
	private long			Amount;
}
