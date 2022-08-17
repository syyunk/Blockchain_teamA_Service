package com.ateam.popserver.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ateam.popserver.dto.*;
import com.ateam.popserver.model.Member;
import com.ateam.popserver.model.Product;
import com.ateam.popserver.model.Trade;

public interface TradeService {
	
	//결제페이지에서 거래물품 정보 조회
	//public TradeDTO getProduct(TradeDTO dto);
	
	//거래내역 생성
	public Long createTrade(TradeDTO dto);

	RespMkBlkDTO buyConfirm(TradeDTO dto);

	GetBlkDTO getBlock(String txid);

	GetTxDTO getTx(String txid);

	List<TradeDTO> getList(String walletid);
	List<TradeDTO> getList2(String walletid);

	//DTO를 Entity로 변환해주는 메서드
	public default Trade dtoToEntity(TradeDTO dto) {
		//프론트 완성시 productDTO를 받아서 아래 변수에 대입한다.				
		Product product = Product.builder().pnum(dto.getPnum()).build();
		System.out.println("==================" + dto.getPnum());
//		Product product = Product.builder().pNum()

		return Trade.builder().product(product).tnum(dto.getTnum()).bwallet(dto.getBwallet())
				.swallet(dto.getSwallet()).date(LocalDateTime.now()).product(product)
				.txid(dto.getTxid()).total(dto.getTotal()).confirm(dto.getConfirm()).build();
	}
	
	//Entity를 DTO로 변환해주는 메서드
	public default TradeDTO entityToDTO(Product product, Trade trade) {
		TradeDTO tradeDTO = TradeDTO.builder()
				.tnum(trade.getTnum())
				.bwallet(trade.getBwallet())
				.swallet(trade.getSwallet())
				.date(trade.getDate())
				.pnum(product.getPnum())
				.txid(trade.getTxid())
				.total(trade.getTotal())
				.confirm(trade.getConfirm())
				.build();

		return tradeDTO;
	}

	default TradeDTO tradeToTradeDTO(Trade trade) {
		if(trade.getProduct() == null) {
			TradeDTO dto = TradeDTO.builder()
					.tnum(trade.getTnum())
					.bwallet(trade.getBwallet())
					.swallet(trade.getSwallet())
					.txid(trade.getTxid())
					.total(trade.getTotal())
					.confirm(trade.getConfirm())
					.pnum(0L)
					.date(trade.getDate())
					.build();
			return dto;
		} else {
			TradeDTO dto = TradeDTO.builder()
					.tnum(trade.getTnum())
					.bwallet(trade.getBwallet())
					.swallet(trade.getSwallet())
					.txid(trade.getTxid())
					.total(trade.getTotal())
					.confirm(trade.getConfirm())
					.pnum(trade.getProduct().getPnum())
					.date(trade.getDate())
					.build();	
			return dto;
		}
	}
}
