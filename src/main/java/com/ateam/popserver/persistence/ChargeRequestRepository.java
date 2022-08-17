package com.ateam.popserver.persistence;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.popserver.model.ChargeRequest;

public interface ChargeRequestRepository extends JpaRepository<ChargeRequest, Long> {
//	Optional<ChargeRequest> findByCnum(Integer cnum);
	List<ChargeRequest> findByWalletidOrderByCnum(String walletid);
}
