package com.ateam.popserver.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.popserver.model.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long>{

	
}
