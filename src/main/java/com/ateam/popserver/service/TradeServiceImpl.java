package com.ateam.popserver.service;

import com.ateam.popserver.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ateam.popserver.model.Trade;
import com.ateam.popserver.persistence.TradeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
	
	private void validate(final Trade entity) {
		if(entity == null) {
			log.warn("Entity is Null");
			throw new RuntimeException("Entity cannot be null");
		}
		if(entity.getTnum()==null) {
			log.warn("TNum is Null");
			throw new RuntimeException("Unknown user");
		}
	}
	
	private final TradeRepository tradeRepository;

	@Override
	public Long createTrade(TradeDTO dto) {
		Trade trade = dtoToEntity(dto);
		//entity 유효성 검사
		//DB저장
		tradeRepository.save(trade);
		//블록생성
//		buyConfirm(dto);
		return trade.getTnum();
	}

	@Override
	public RespMkBlkDTO buyConfirm(TradeDTO dto) {
		String strUrl = "http://127.0.0.1:10000/MainBlockServer";
		//jsonMessage
		String jsonMessage = "";

		ObjectMapper mapper = new ObjectMapper();

		MkBlkDTO jsonDto = new MkBlkDTO();
		jsonDto.setAmount(dto.getTotal());
		jsonDto.setFrom(dto.getBwallet());
		jsonDto.setTo(dto.getSwallet());
		jsonDto.setPurpose("블록 생성");

		try {
			jsonMessage = mapper.writeValueAsString(jsonDto);

		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);                                     		// 서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000);                                        		// InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestMethod("POST");                                     		// json으로 message를 전달하고자 할 때
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoInput(true);                                          		// Server 통신에서 입력 가능한 상태로 만듬
			con.setDoOutput(true);                                           		// Server 통신에서 입력 가능한 상태로 만듬(POST 데이터를 OutputStream으로 넘겨 주겠다는 설정)
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(jsonMessage);
			wr.flush();
			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//Stream을 처리해줘야 하는 귀찮음이 있음.
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				RespMkBlkDTO respDto = mapper.readValue(sb.toString(), RespMkBlkDTO.class);
				return respDto;
			}else{
				System.out.println(con.getResponseMessage());
			}

		}catch(Exception e){
			System.err.println(e.toString());
		}
		return null;
	}

	@Override
	public GetBlkDTO getBlock(String txid) {
		// TODO Auto-generated method stub
		//URL 주소
		String strUrl = "http://127.0.0.1:10000/MainBlockServer";
		//jsonMessage
		String jsonMessage = "";

		ObjectMapper mapper = new ObjectMapper();
//		      키            값
//		      From        => dto.getBwallet()
//		      To          => dto.getSwallet()
//		      Purpose    => "블록 생성"
//		      Amount     => dto.getTotal()
		// 이렇게 JSON으로 변경해서 보내주시면 될 것같아여 오른쪽에 있는 값으로여 ㅎㅎ

		Map<String, Object> jsonInfo = new HashMap<>();

		jsonInfo.put("TxidByHex", txid);
		jsonInfo.put("Purpose", "블록 조회");
		System.out.println(jsonInfo);

		try {
			jsonMessage = mapper.writeValueAsString(jsonInfo);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);                                     		// 서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000);                                        		// InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestMethod("POST");                                     		// json으로 message를 전달하고자 할 때
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoInput(true);                                          		// Server 통신에서 입력 가능한 상태로 만듬
			con.setDoOutput(true);                                           		// Server 통신에서 입력 가능한 상태로 만듬(POST 데이터를 OutputStream으로 넘겨 주겠다는 설정)
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(jsonMessage);
			wr.flush();
			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//Stream을 처리해줘야 하는 귀찮음이 있음.
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				GetBlkDTO respDto = mapper.readValue(sb.toString(), GetBlkDTO.class);
				System.out.println("------------"+respDto);
				return respDto;
			}else{
				System.out.println(con.getResponseMessage());
			}

		}catch(Exception e){
			System.err.println(e.toString());
		}
		return null;
	}

	@Override
	public GetTxDTO getTx(String txid) {
		// TODO Auto-generated method stub
		//URL 주소
		String strUrl = "http://127.0.0.1:10000/MainBlockServer";
		//jsonMessage
		String jsonMessage = "";

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> jsonInfo = new HashMap<>();

		jsonInfo.put("TxidByHex", txid);
		jsonInfo.put("Purpose", "tx 조회");
		System.out.println(jsonInfo);

		try {
			jsonMessage = mapper.writeValueAsString(jsonInfo);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);                                     		// 서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000);                                        		// InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestMethod("POST");                                     		// json으로 message를 전달하고자 할 때
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoInput(true);                                          		// Server 통신에서 입력 가능한 상태로 만듬
			con.setDoOutput(true);                                           		// Server 통신에서 입력 가능한 상태로 만듬(POST 데이터를 OutputStream으로 넘겨 주겠다는 설정)
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(jsonMessage);
			wr.flush();
			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//Stream을 처리해줘야 하는 귀찮음이 있음.
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				GetTxDTO respDto = mapper.readValue(sb.toString(), GetTxDTO.class);
				System.out.println("------------getTx서비스 받은 값 :: "+respDto);
				return respDto;
			}else{
				System.out.println(con.getResponseMessage());
			}

		}catch(Exception e){
			System.err.println(e.toString());
		}
		return null;
	}

	//사용자 충전+판매 내역 조회(구매확정 전, 후 포함)
	@Override
	public List<TradeDTO> getList(String walletid) {
		Sort sort = Sort.by("tnum").descending();
	//	Page<Trade> result = tradeRepository.findAll(pageable);
		
		PageRequestDTO pagedto=new PageRequestDTO();
		List<Trade> result = tradeRepository.getListWithMember(walletid);

		//Entity를 DTO로 변환해주는 함수 설정
		Function<Trade, TradeDTO> fn =(this::tradeToTradeDTO);
		System.out.println("service result :: "+tradeRepository.getListWithMember(walletid));
		return result.stream().map(this::tradeToTradeDTO).collect(Collectors.toList());
	}
	
	//사용자 구매 내역 조회(구매확정 전, 후 포함)
	@Override
	public List<TradeDTO> getList2(String walletid) {
		Sort sort = Sort.by("tnum").descending();
		//	Page<Trade> result = tradeRepository.findAll(pageable);
		
		PageRequestDTO pagedto=new PageRequestDTO();
		List<Trade> result = tradeRepository.getListWithMemberBuy(walletid);
		
		//Entity를 DTO로 변환해주는 함수 설정
		Function<Trade, TradeDTO> fn =(this::tradeToTradeDTO);
		System.out.println("service result :: "+tradeRepository.getListWithMember(walletid));
		return result.stream().map(this::tradeToTradeDTO).collect(Collectors.toList());
	}


}
