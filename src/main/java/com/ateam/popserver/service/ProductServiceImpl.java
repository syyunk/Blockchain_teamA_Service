package com.ateam.popserver.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ateam.popserver.dto.PageRequestDTO;
import com.ateam.popserver.dto.PageResponseDTO;
import com.ateam.popserver.dto.ProductDTO;
import com.ateam.popserver.dto.TradeDTO;
import com.ateam.popserver.model.Member;
import com.ateam.popserver.model.Product;
import com.ateam.popserver.persistence.MemberRepository;
import com.ateam.popserver.persistence.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	//이미지 저장할 폴더만들기
	@Value("${com.ateam.upload.path}")
	private String uploadPath;
	

	// 업로드한 날짜 별로 이미지를 저장하기 위해서 날짜별로 디렉토리를 만들어서 경로를 리턴하는 메서드
	private String makeFolder() {
		String realUploadPath = "images";

		// 디렉토리 생성
		File uploadPathDir = new File(uploadPath, realUploadPath);
		if (!uploadPathDir.exists()) {
			uploadPathDir.mkdirs();
		}

		return realUploadPath;
	}


	
	@Override
	   public Long registerProduct(ProductDTO dto) {
	      MultipartFile uploadFile = dto.getImg();
	      if(uploadFile.isEmpty() == false) {
	         System.out.println("이미지파일은 있다");
	         if(uploadFile.getContentType().startsWith("image")==false) {
	            System.out.println("이미지파일이 아님"+uploadFile.getContentType());
	            return null;
	         }
	         String originalName = uploadFile.getOriginalFilename();
	         String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
	         String realUploadPath = makeFolder();

	         String uuid = UUID.randomUUID().toString();

	         String saveName = uploadPath + File.separator + uuid + fileName;
	         Path savePath = Paths.get(saveName); //
	         System.out.println(realUploadPath);
	         System.out.println(uploadPath+"3333333333333333333333333333");
	         System.out.println(savePath);
	         try {
	            uploadFile.transferTo(savePath); // 지정경로로 보내버려!
	         }catch(Exception e){
	            e.printStackTrace();
	         }
	         //경로를 html 파일의 이미지 태그에 경로를 적어줄 때 /슬래시로 적어야 함!! dto에 저장할 때는 파일 경로를 변환
	         dto.setThumb(uuid+fileName); //dto 에 저장
	      }

	      Product p = dtoToEntityProduct(dto);
	      System.out.println("=========================1111111111111111========================");
	      System.out.println(p);
	      productRepository.save(p);

	      return p.getPnum();

	   }

	@Override
	public PageResponseDTO<ProductDTO, Product> getList(PageRequestDTO requestDTO) {
		Sort sort = Sort.by("pnum").descending();
		Pageable pageable = PageRequest.of(requestDTO.getPage()-1, requestDTO.getSize(),sort);
		Page<Product> result = productRepository.findAll(pageable);
		
		Function<Product, ProductDTO> fn = (entity->entityToDTO(entity));
//		Page<Object[]> result = productRepository.getProductWithPnum(PageRequestDTO.getPabeable(Sort.by("pnum").descending()));
		return new PageResponseDTO<>(result, fn);
	}

//	public PageResponseDTO<ProductDTO, Object[]> getList2(PageRequestDTO requestDTO) {
//		log.info(requestDTO);
//		Function<Object[], ProductDTO> fn = (en->{
////			PageRequestDTO.getPageable(Sort.by("pnum").descending());
//			entityToDTO((Product)en[0], (Member)en[1]);	
//		});
////		Page<Object[]> result = boardRepository.getBno)
////			
////		});	
//		Page<Product> result = productRepository.findAll(pageable);
//		return new PageResponseDTO<>(result, fn);
//		
//		Sort sort = Sort.by("pnum").descending();
//		Pageable pageable = PageRequest.of(requestDTO.getPage()-1, requestDTO.getSize(),sort);
//		
//		
//	}

	@Override
	public Long updateProduct(ProductDTO dto) {
//		이미지수정 관련
//		Product p = dtoToEntityProduct(dto);
//		productRepository.save(p);
		return null;
	}

	@Override
	public String deleteProduct(ProductDTO dto) {
		Product p = dtoToEntityProduct(dto);
		productRepository.delete(p);
		return p.getName();
	}

	@Override
	public ProductDTO getProduct(ProductDTO dto) {
//		Long pid = dto.getPnum();
//		Optional<Product> opt = productRepository.findById(pid);
//		if(opt.isPresent()) {
//			return entityToDto(opt.get());
//		}
		return null;
	}
	

	@Override
	public ProductDTO readBasic(Long pnum) {
		//ProductDTO dto = ProductDTO.builder().pnum(pnum).build();//이건 서비스 테스트에서 하던건데 안돼. 왜? 
		Optional<Product> product = productRepository.findById(pnum); //서비스에서는 레포지토리를 호출해야함. 
		if(product.isPresent()){
			System.out.println(product);
		}
		return product.isPresent()?entityToDTO(product.get()):null;
	}
	
	//상품상세에 필요한 리드
	@Override
	   public ProductDTO read(Long pnum) {
	      //ProductDTO dto = ProductDTO.builder().pnum(pnum).build();//이건 서비스 테스트에서 하던건데 안돼. 왜? 
	      Optional<Product> product = productRepository.findById(pnum); //서비스에서는 레포지토리를 호출해야함. 
	      if(product.isPresent()){
	         System.out.println(product);
	      }
	      return product.isPresent()?entityToDTO(product.get()):null;
	   }
	
	
	
	//충전거래내역 조회할 떄 필요한 리드
	@Override
	public ProductDTO read(Long mnum, Long pnum) {
		//ProductDTO dto = ProductDTO.builder().pnum(pnum).build();//이건 서비스 테스트에서 하던건데 안돼. 왜? 
//		Optional<Product> product = productRepository.findById(pnum); //서비스에서는 레포지토리를 호출해야함. 
//		if(product.isPresent()){
//			System.out.println("service dto get :::::: "+product.get());
//		}
//		Product prodDTO = product.get();
		
		ProductDTO pdto = new ProductDTO();
		
		System.out.println("===============1111111111111111=======");
		System.out.println("pnum:: "+pnum);
		if(pnum!=null) {
			Product prodDTO = productRepository.findByPnum(pnum);			
			System.out.println("prodDTO :: "+prodDTO);
			pdto = entityToDTO(prodDTO);
		}else {
			//~pnum -> dto.builder().pnum(0L).
			pdto = pdto.builder().pnum(0L).mnum(mnum).name("충전").price(0).build();
			System.out.println("pdto::"+pdto);
		}
	
		
		return pdto;
	}

	@Override
	public String makeTx(TradeDTO tDto) {
		// 성공하면 txid 반환
		
		return null;
	}



}