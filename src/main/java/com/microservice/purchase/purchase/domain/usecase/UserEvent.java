package com.microservice.purchase.purchase.domain.usecase;


import com.microservice.purchase.exception.AccountAPIException;
import com.microservice.purchase.purchase.domain.entities.Purchase;
import com.microservice.purchase.purchase.domain.gateway.PurchaseDTO;
import com.microservice.purchase.purchase.domain.gateway.PurchaseListDTO;
import com.microservice.purchase.purchase.domain.mapper.PurchaseMapper;
import com.microservice.purchase.purchase.domain.ports.PurchasePorts;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEvent {

    private final PurchaseMapper purchaseMapper;
    private final PurchasePorts purchasePorts;

    public UserEvent(PurchasePorts purchasePorts) {
        this.purchasePorts = purchasePorts;
        this.purchaseMapper = new PurchaseMapper();
    }

    public PurchaseListDTO execute(String email) {

        List<Purchase> purchases = purchasePorts.findByEmail(email).orElseThrow(
                () ->  new AccountAPIException(HttpStatus.NOT_FOUND, "User not found with email: " + email)
        );

        return new PurchaseListDTO(purchases.stream()
                .map(purchaseMapper::mapToDto)
                .toList()
        );
    }

}
