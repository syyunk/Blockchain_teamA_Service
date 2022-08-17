package com.ateam.popserver.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeDTO {
	private Long tnum;
	
    private String bwallet;
	
	private String swallet;
	
//	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDateTime date;
	
    private Long pnum;
    
    private String txid;
    
    private Integer total;
    
    private Integer confirm;
    
//    //거래상품 번호
//    private Long productNum;
    
    
}
