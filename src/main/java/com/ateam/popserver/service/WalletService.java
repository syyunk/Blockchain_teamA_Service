package com.ateam.popserver.service;

import java.util.List;

import com.ateam.popserver.dto.ChargeRequestDTO;
import com.ateam.popserver.dto.RespMkBlkDTO;
import com.ateam.popserver.dto.TradeDTO;
import com.ateam.popserver.dto.WalletDTO;
import com.ateam.popserver.model.ChargeRequest;
import com.ateam.popserver.model.Wallet;

public interface WalletService {
	public Integer initialization(WalletDTO dto);
	public WalletDTO read(String walletid);
	//잔액조회
	public Integer getBalance(String walletid);
	//지갑 충전 요청
	public void chargeRequest(ChargeRequestDTO dto);
	//지갑 충전 요청 리스트
	public List<ChargeRequestDTO> getChargeList();
	//충전 승인
	public RespMkBlkDTO chargeAccept(ChargeRequestDTO dto);
	
	public default Wallet dtoToEntityWallet(WalletDTO dto) {
		Wallet w = Wallet.builder()
				.walletid(dto.getWalletid())
				.balance(dto.getBalance())
				.build();
		return w;
	}
	public default WalletDTO entityToDTO(Wallet w) {
		WalletDTO dto = WalletDTO.builder()
				.walletid(w.getWalletid())
				.balance(w.getBalance())
				.build();
		return dto;
	}
	
	public default ChargeRequestDTO entityToDTO(ChargeRequest chr) {
		String comWalletid = "1EBM14fstrhwEX6i8KwHvLuNfNNJ4GKuH5";		//admin walletid
		ChargeRequestDTO cr = ChargeRequestDTO.builder()
				.cnum(chr.getCnum())
				.bWallet(comWalletid)
				.sWallet(chr.getWalletid())
				.balance(chr.getBalance())
				.total(chr.getAmount())
				.confirm(chr.getConfirm())
				.regdate(chr.getRegDate())
				.moddate(chr.getModDate())
				.build();
		return cr;
	}
	
}
