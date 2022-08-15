package com.ateam.popserver.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PageRequestDTO {
	// 현재 페이지 번호
	private int page;
	
	// 페이지 당 출력할 데이터 개수
	private int size;
	
	// 검색 항목
//	private String type;
	
	// 검색할 데이터 키워드
//	private String keyword;
	
	// 생성자
	public PageRequestDTO() {
		this.page = 1;
		this.size = 10;
	}
	
	// 페이지 검색 기능 객체 생성 메서드
	public Pageable getPageable(Sort sort) {
		return PageRequest.of(page - 1, size, sort);
	}
}
