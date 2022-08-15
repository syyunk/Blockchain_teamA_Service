package com.ateam.popserver.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ateam.popserver.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
	
	Wallet findByWalletid(String walletid);
}
