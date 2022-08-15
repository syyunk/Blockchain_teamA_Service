package com.ateam.popserver.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long wnum;

	private String walletid;
	
	private Integer balance;
	
	public Integer changeBalance(Integer amount) {
		return balance += amount;
	}
}
