package com.ateam.popserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chargerequest")
@ToString
public class ChargeRequest extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cnum;
	
	private String walletid;
	
	private Integer balance;
	private Integer amount;
	
	private Integer confirm;
	
	public Integer changeBalance() {
		return balance = 0;
	}

};
