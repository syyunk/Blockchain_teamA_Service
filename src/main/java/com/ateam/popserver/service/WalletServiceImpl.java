package com.ateam.popserver.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ateam.popserver.dto.ChargeRequestDTO;
import com.ateam.popserver.dto.MkBlkDTO;
import com.ateam.popserver.dto.RespMkBlkDTO;
import com.ateam.popserver.dto.TradeDTO;
import com.ateam.popserver.dto.WalletDTO;
import com.ateam.popserver.model.ChargeRequest;
import com.ateam.popserver.model.Trade;
import com.ateam.popserver.model.Wallet;
import com.ateam.popserver.persistence.ChargeRequestRepository;
import com.ateam.popserver.persistence.TradeRepository;
import com.ateam.popserver.persistence.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
	
	private final WalletRepository walletRepository;
	private final TradeRepository tradeRepository;
	private final ChargeRequestRepository chargeRequestRepository;

	@Override
	public Integer getBalance(String walletid) {
		Wallet w = walletRepository.findByWalletid(walletid);
		return w.getBalance();
		
	}
	@Override
	public Integer initialization(WalletDTO dto) {
		// 
		log.info(dto);
		//해당 주소의 잔액을 가져온다
		
		//충전금액을 추가한다.
		Wallet w = dtoToEntityWallet(dto);
		w.getBalance();
		walletRepository.save(w);
		return w.getBalance();
	}
	@Override
	public WalletDTO read(String walletid) {
		// 
		log.info("지갑 조회");
		Wallet w = walletRepository.findByWalletid(walletid);
		WalletDTO wDto= entityToDTO(w);
		
		return wDto;
	}
	//충전요청 
	@Override
	public void chargeRequest(ChargeRequestDTO dto) {

		//충전승인요청DB로 저장
		ChargeRequest cr = ChargeRequest.builder()
				.walletid(dto.getSWallet())
				.balance(dto.getBalance())
				.amount(dto.getTotal())
				.confirm(0)
				.build();
		chargeRequestRepository.save(cr);
		
//		Trade t = Trade.builder()
//				.bWallet(dto.getBWallet())
//				.sWallet(dto.getSWallet())
////				.date(LocalDateTime.now())
////				.product(dto.getPNum())
////				.txid(dto.getTxid())
////				.total(dto.getTotal())
////				.confirm(dto.getConfirm())
//				.build();
//		tradeRepository.save(t);
//		
//		Wallet w = walletRepository.findByWalletid(dto.getSWallet());
//		Wallet nw = Wallet.builder()
//				.balance(w.getBalance()+ (dto.getTotal()))
//				.walletid(dto.getSWallet())
//				.build();
//				
//		walletRepository.save(nw);
//		tradeRepository.
//		dtoToEntityTrade(null)
//		tradeRepository.save(trade);
	}
	//충전요청 리스트
	@Override
	public List<ChargeRequestDTO> getChargeList() {
		List<ChargeRequest> entitylist = chargeRequestRepository.findAll();
		List<ChargeRequestDTO> list = entitylist.stream().map(i -> entityToDTO(i))
				.collect(Collectors.toList());
		return list;
	}
	@Override
	public RespMkBlkDTO chargeAccept(ChargeRequestDTO dto) {
		
		//URL 주소 
	      String strUrl = "http://127.0.0.1:10000/MainBlockServer";
	      //jsonMessage
	      String jsonMessage = "";
	      
	      ObjectMapper mapper = new ObjectMapper();
	      //tx생성요청해서 
	      //블록id값 txid값 돌려받기 성공한 후
	      String companyWalletid = "1EBM14fstrhwEX6i8KwHvLuNfNNJ4GKuH5";
	      MkBlkDTO bDto =MkBlkDTO.builder()
    		  	.From(companyWalletid)
				.To(dto.getSWallet())
				.Amount(dto.getTotal())
				.Purpose("블록 생성")
				.build();
		
		System.out.println(bDto);
		
		try {
	         jsonMessage = mapper.writeValueAsString(bDto); 
	         System.out.println(jsonMessage);
	         // 이 부분 수정해야 될 것 같아여. 위에 처럼 dto에서 일부만 가져오는거라 통재로 보내주면 구조체랑
	         // 매핑이 안되서 에러가 뜨거든용
	         // 그말씀은 즉슨 to from amount만 여기서 보내라는말씀이시죠네. 거기에 Purpose    => "블록 생성" 이것도 넣어주셔야되용 설명 깔끔합니다.
	         // 굿굿 :)
	         // 다른 데는 고칠부분은 없는거 같아용 ㅎㅎ 너무너무 감사드리고요~ 이따가 또물어볼거생긱면 여쩌봐도될까여?^^ 당연하져 ㅎㅎ
	         // 그럼 테스트할때 postman에서 restapi 통신 되는지 확인만 하면되겠네요 ? 넵 스프링에서도 버튼누르면 블럭 생성되게끔 구현하셨으니 버튼 눌러서 테스팅하는 것도 괜찮을거같아여
	      } catch (JsonProcessingException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      }
	      
	      try {
	         URL url = new URL(strUrl);         
	         HttpURLConnection con = (HttpURLConnection) url.openConnection();         
	         con.setConnectTimeout(5000);                                     // 서버에 연결되는 Timeout 시간 설정         
	         con.setReadTimeout(15000);                                        // InputStream 읽어 오는 Timeout 시간 설정         
	         //con.addRequestProperty("x-api-key", RestTestCommon.API_KEY);             // key값 설정         
	         con.setRequestMethod("POST");                                     // json으로 message를 전달하고자 할 때          
	         con.setRequestProperty("Content-Type", "application/json");         
	         con.setDoInput(true);                                          // Server 통신에서 입력 가능한 상태로 만듬
	         con.setDoOutput(true);                                           // Server 통신에서 입력 가능한 상태로 만듬(POST 데이터를 OutputStream으로 넘겨 주겠다는 설정)
	         con.setUseCaches(false);         
	         con.setDefaultUseCaches(false);         
	         BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(con.getOutputStream())); //아래 부분과 맞춰줘야 함.          
	         wr.write(jsonMessage);                                           //json 형식의 message 전달          
	         wr.flush();         
	         StringBuilder sb = new StringBuilder();         
	         if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {            
	            //Stream을 처리해줘야 하는 귀찮음이 있음.     BufferedReader       
	            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
	            System.out.println("~~~됐나요!~~~~" + br);         
	            System.out.println(br);      
	            //String dataconver = URLEncoder.decode(br,"utf-8");
	            System.out.println("======== 22222222222222222222222 ===================");
	            String line;
	            
	            while ((line = br.readLine()) != null) {
	            	System.out.println(line);//✍️✍️✍️이클립스 버그 확인 
	               sb.append(line).append("\n");
	            }
	            
	            System.out.println("======== 33333333333333333333333 ===================");
	            
	            br.close();            
	            // 코어에서 들어온 Json데이터를 RespMkBlkDTO 에 담아서 리턴
	            RespMkBlkDTO respDto = mapper.readValue(sb.toString(), RespMkBlkDTO.class);
	            System.out.println(respDto.getTxid());
	            System.out.println(respDto);
	            
	            
	            //받아온 값으로 승인완료로 db 저장
	            String sWalletid = walletRepository.findByWalletid(dto.getSWallet()).getWalletid();
	            System.out.println("======== 44444444444444 ===================");
	            Trade t = Trade.builder()
	    	    		.bwallet(companyWalletid)
	    	    		.swallet(sWalletid)
	    	    		.date(LocalDateTime.now())
	    	    		.txid(respDto.getTxid())
	    	    		.total(dto.getTotal())
	    	    		.confirm(1)
	    	    		.build();
	            System.out.println("======== 555555555 ==================="+t);
	    		tradeRepository.save(t);
	    		System.out.println("트레이드db저장dto : "+t);
	    		ChargeRequest cr = ChargeRequest.builder()
	    				.cnum(dto.getCnum())
	    				.walletid(dto.getSWallet())
	    				.balance(dto.getBalance())
	    				.amount(dto.getTotal())
	    				.confirm(1)
	    				.build();
	    		chargeRequestRepository.save(cr);
	    		System.out.println("==============66666666666666==================");
	    		System.out.println("충전요청업데이트dto :"+cr);
	    		

	    		//잔액 db에 저장
	    		System.out.println("월렛id:======================>>>");
	    		String walletid = dto.getSWallet(); 
	    		System.out.println("========================"+dto.getSWallet());
	    		
	    		Wallet w = walletRepository.findByWalletid(dto.getSWallet());
	    		Integer resultBalance = w.getBalance() + dto.getTotal();
	    		Wallet nw = Wallet.builder().wnum(w.getWnum()).balance(resultBalance).walletid(w.getWalletid()).build();
	    		walletRepository.save(nw);
	    		System.out.println("==============7777777777777==================");
	    		//송신자 잔액 db에 값 저장
	    		Wallet cw = walletRepository.findByWalletid(companyWalletid);
	    		Integer comBalance = cw.getBalance() - dto.getTotal();
	    		nw = Wallet.builder().wnum(cw.getWnum()).balance(comBalance).walletid(cw.getWalletid()).build();
	    		walletRepository.save(nw);
	    		System.out.println("==============88888888 =================="+comBalance);

	    		//승인요청테이블에 승인완료 날짜 반영?????? 
	    		Optional<ChargeRequest> opt = chargeRequestRepository.findById(dto.getCnum());
	    		if (opt.isPresent()) {
	    			ChargeRequest cr1 = opt.get();
	    			ChargeRequest ncr = ChargeRequest.builder().cnum(cr1.getCnum())
	    					.walletid(cr1.getWalletid())
	    					.balance(cr1.getBalance())
	    					.amount(cr1.getAmount())
	    					.confirm(cr1.getConfirm())
	    					.build();
	    			chargeRequestRepository.save(ncr);
	    		}
	    		
	            return respDto;
	         } else {            
	            System.out.println(con.getResponseMessage());         
	         }   
	         
	      }catch(Exception e){         
	         System.err.println(e.toString());      
	      }
	   
		return null;
	}

}
