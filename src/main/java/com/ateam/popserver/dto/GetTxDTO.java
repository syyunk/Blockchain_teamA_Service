package com.ateam.popserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//controller와 service 사이 데이터 전달 위한 클래스
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetTxDTO {
	@JsonProperty("Txid")
	private String Txid;
	@JsonProperty("From")
   private String From;
	@JsonProperty("To")
   private String To;
	@JsonProperty("Amount")
   private String Amount;
}