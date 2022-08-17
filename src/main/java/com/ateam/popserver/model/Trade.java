package com.ateam.popserver.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "trade")
@ToString(exclude = {"product"})
public class Trade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tnum")
    private Long tnum;
	
	@Column(name = "bwallet", nullable=false)
    private String bwallet;
	
	@Column(name = "swallet", nullable=false)
	private String swallet;
	
	@Column(name = "date")
	private LocalDateTime date;
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pnum", nullable=true)
    private Product product;
    
    @Column(name = "txid")
    private String txid;
    
    @Column(name = "total", nullable=false)
    private Integer total;

    @Column(name = "confirm")
    private int confirm;


}
