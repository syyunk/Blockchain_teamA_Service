package com.ateam.popserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//controller와 service 사이 데이터 전달 위한 클래스
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class RespMkBlkDTO {
	@JsonProperty("Hash")
	private String Hash;
	@JsonProperty("Txid")
	private String Txid;
}
