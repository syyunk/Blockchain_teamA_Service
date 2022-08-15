package com.ateam.popserver.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeRequestDTO {
	private long cnum;
	private String bWallet;
	private String sWallet;
	private Integer balance;
	private Integer total;
	private Integer confirm;
    private LocalDateTime regdate;
    private LocalDateTime moddate;
}
