package com.microservice.purchase.purchase.domain.ports;

import com.microservice.purchase.purchase.domain.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchasePorts extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findByEmail(String email);
    Boolean existsByEmail(String email);

}
