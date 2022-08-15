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
public class GetBlkDTO {
	@JsonProperty("Hash")
   private String Hash;
	@JsonProperty("Data")
   private String Data;
	@JsonProperty("Txid")
   private String Txid;
	@JsonProperty("Timestamp")
   private String Timestamp;
}