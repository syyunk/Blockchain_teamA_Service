package com.ateam.popserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.ateam.popserver.dto.PageRequestDTO;
import com.ateam.popserver.dto.PageResponseDTO;
import com.ateam.popserver.dto.ProductDTO;
import com.ateam.popserver.dto.TradeDTO;
import com.ateam.popserver.model.Member;
import com.ateam.popserver.model.Product;
import com.ateam.popserver.persistence.MemberRepository;



public interface ProductService {

	//데이터 삽입
	public Long registerProduct(ProductDTO dto);
	//하나보기
	public ProductDTO getProduct(ProductDTO dto);	
	public ProductDTO readBasic(Long pnum);	
	public ProductDTO read(Long pnum);
	public ProductDTO read(Long mnum, Long pnum);
	//목록보기
	public PageResponseDTO<ProductDTO, Product> getList(PageRequestDTO pageRequestDTO);
//	public PageResponseDTO<ProductDTO, Object[]> getList2(PageRequestDTO pageRequestDTO);
	public Long updateProduct(ProductDTO dto);
	public String deleteProduct(ProductDTO dto);
//	public String getProduct(ProductDTO dto);
	
	public default Product dtoToEntityProduct(ProductDTO dto) {
		Member member = Member.builder().mnum(dto.getMnum()).build();
		Product p = Product.builder()
				.pnum(dto.getPnum())
				.name(dto.getName())
				.price(dto.getPrice())
				.thumb(dto.getThumb())
				.detail(dto.getDetail())
				.achieved(dto.getAchieved())
				.member(member)
				.build();
		return p;
	}
	public default ProductDTO entityToDTO(Product p) {
		ProductDTO dto = ProductDTO.builder()
				.pnum(p.getPnum())
				.name(p.getName())
				.price(p.getPrice())
				.thumb(p.getThumb())
				.detail(p.getDetail())
				.achieved(p.getAchieved())
				.regDate(p.getRegDate())
				.modDate(p.getModDate())
				.mnum(p.getMember().getMnum()).nickname(p.getMember().getNickname())
				.build();
		return dto;
	}
	
	public String makeTx(TradeDTO tDto);
	
	
}
