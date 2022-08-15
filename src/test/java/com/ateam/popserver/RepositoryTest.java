package com.ateam.popserver;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.ateam.popserver.model.Member;
import com.ateam.popserver.model.Product;
import com.ateam.popserver.model.Wallet;
import com.ateam.popserver.persistence.ChargeRequestRepository;
import com.ateam.popserver.persistence.MemberRepository;
import com.ateam.popserver.persistence.ProductRepository;
import com.ateam.popserver.persistence.WalletRepository;

@SpringBootTest
public class RepositoryTest {
	@Autowired
	private WalletRepository walletRepository;
	private ChargeRequestRepository chargeRequestRepository;
	//@Test
	@Transactional
	@Commit
	public void 잔액조회() {
		Integer balance = walletRepository.findByWalletid("16wuTYRdmJpgCEddQqFTfCd8iuixJiZXRD").getBalance();
		System.out.println(balance);
	}
	
	//@Test
	@Transactional
	@Commit
	public void 충전() {
		Integer amount = 1000;
		Wallet w = walletRepository.findById(1L).get();
		System.out.println(w.changeBalance(amount));	
		
	}
    //@Test
    @Transactional
    @Commit
    public void insertWallet() {
    	Wallet wallet = Wallet.builder()
                .walletid("1MQZcwiqUddd3ND6me9vXLohbfNexbx6Es")
                .balance(1000000)
                .build();
        walletRepository.save(wallet);
        wallet = Wallet.builder().walletid("1QDWrZtg1BU9oBCZ9CWqHJUrmTwviix9ab")
        		.balance(10).build();
        walletRepository.save(wallet);
    }
    //@Test
    @Transactional
    @Commit
    void 지갑주소로_지갑찾아오기() {
    	Wallet w = walletRepository.findByWalletid("1MQZcwiqUddd3ND6me9vXLohbfNexbx6Es");
    	System.out.println(w);
    }
	//맴버생성시 지갑생성
    public Wallet newWallet() {
		
		
        Wallet wallet = Wallet.builder()
                    .walletid("1QDWrZtg1BU9oBCZ9CWqHJUrmTwviix9ab")
                    .balance(1000000)
                    .build();
        walletRepository.save(wallet);
        
        
        return wallet;
    }
	       
	   //Members
	   @Autowired
	   private MemberRepository memberRepository;

	   //이건뭐지
	   //@Test
	   @Transactional
		@Commit
	    public void findByNickname(String nick) {
		   Member member = memberRepository.findById(nick);
		   
	   }
	    @Autowired
		private MemberAuthRepository memberAuthRepository;
		
		//맴버생성시 접근지정자 생성 
		//@Test
		@Transactional
		@Commit
	    public void newAuth(Member member) {
//	    	Member member= Member.builder().mnum((long)41).build();
	    	System.out.println("********************2");
			MemberAuth memberAuth = MemberAuth.builder()
					.member(member)
					.auth("ROLE_MEMBER")
					.build();
			memberAuthRepository.save(memberAuth);
	        
	    }
	    
		//회원가입
	   //@Test 
	   @Transactional
	   @Commit
	    public void insertMember() {
			Wallet wallet = Wallet.builder()
//					.walletid("1AcKKsiyStd2sd3mjRuzSC3JtmethcGxtW")
//					.walletid("1pHWx9NeTTA4C2AM1oRhFedoNy65Hxhak")
					.walletid("1NtcbYVNA62mBzi7teBZFnX9DD2D3rM1aX")
					.balance(0)
					.build();
			walletRepository.save(wallet);
			
			Member member = Member.builder()
				   .wallet(wallet)
				   .id("id3")
				   .pw("0000")
				   .nickname("user3")
				   .tel("010-2234-2222")
				   .addr("서울시 서대문구 조은e동")
				   .star(Float.valueOf(5))
				   .build();
			memberRepository.save(member);
			newAuth(member);
	    }
	   
	   //@Test
	   @Transactional
	   public void getMember() {
//		   memberRepository.findByWallet(null)
	   }
	   //@Test
	   @Transactional
	   public void getWallet() {
		   Wallet w = walletRepository.findByWalletid("1MQZcwiqUddd3ND6me9vXLohbfNexbx6Es");
		   System.out.println(w);
	   }
	   
	  //Product
	     @Autowired
	     private ProductRepository productRepository;
	     
	     @Test
	     @Transactional
	     @Commit
	    public void insertProduct() {
	    	 System.out.println("======================");
  	  		 Member member= Member.builder().mnum(5L).build(); //✍️✍️✍️✍️✍️
	    	System.out.println(member);
	       Product p= Product.builder()
	    		   .member(member)
	    		   .name("상품명")
	    		   .price(100)
	    		   .thumb("상품1.png")
	    		   .detail("상품설명")
	    		   .achieved(0)
	    		   .build(); 
	       productRepository.save(p);
	       
	        IntStream.rangeClosed(1,5).forEach(i -> {
	             int prices = (int)(Math.random() *10000);
	              Product product = Product.builder()
	                     .member(member)
	                    .name("중고 핸드폰"+(int)prices/100)
	                    .price(prices)
	                    .thumb("썸네일")
	                    .detail("갤럭시"+(int)prices/100+" 미개봉")
	                    .build();
	            productRepository.save(product);
	        });
	      }
	     //@Test
	     @Transactional
	     @Commit
	     public void deleteProduct() {
	    	 Product p = Product.builder().pnum(6L).build();
	    	 productRepository.delete(p);
	     }
	   //데이터 전체 보기 테스트
	 	//@Test
	 	public void getAll() {
	 		List<Product> list = productRepository.findAll();
	 		System.out.println(list);
	 	}
		//페이징 과 정렬
		//@Test
		public void getPaging() {
			Sort sort = Sort.by("pnum").descending();
			Pageable pageable = PageRequest.of(0, 10, sort);
			Page<Product> page = productRepository.findAll(pageable);
			page.get().forEach(item -> {
				System.out.println(item);
			});
		}
		//외래키를 이용한 조회
		//@Test
		public void getFindMember() {
			Member member = Member.builder()
					.mnum(2L)
					.build();
			List<Product> list = productRepository.findProductByMember(member);
			System.out.println(list);
		}
		//pnum으로 조회
		//@Test //이것만 하면 보드에서만 정보를 가지고오게됨. 즉, 조인을 하지 않으면 member 정보가 없으니까 에러가 발생함 
		@Transactional //
		public void getProduct() {
			Optional<Product> result = productRepository.findById(2L);
			System.out.println(result.get());
			System.out.println(result.get().getMember());
			System.out.println("======== @Transactional붙여야 함.===============");
			System.out.println(result); 
		}
		//
		//@Test
		public void testProductWithPnum() {
			Object result = productRepository.getProductWithPnum(4L);
			System.out.println(result); 
			System.out.println(Arrays.toString((Object[])result)); 
		}
		



	     
	    //product_img_file
	    @Autowired
	    private ProductImgFileRepository productImgFileRepository;
	    
	    private String imageType;
	    
	   //@Test
	   @Transactional
	   @Commit
	   public void insertProductImgFile() {
	      Product product= Product.builder().pnum(5L).build(); 
	       IntStream.rangeClosed(1,5).forEach(i -> {
	          imageType = "detail";
	          if(i==1) {
	             imageType = "thumb";
	          }
	            ProductImgFile productImgFile = ProductImgFile.builder()
	                  .product(product)
	                  .imgName("사진"+i)
	                  .imgType(imageType)
	                  .imgUrl("x"+i+"ecrwew")
	                  .build();
	           productImgFileRepository.save(productImgFile);
	       });
	     }

}
