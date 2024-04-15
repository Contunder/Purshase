package com.microservice.purchase.purchase.domain.mapper;


import com.microservice.purchase.purchase.domain.entities.Purchase;
import com.microservice.purchase.purchase.domain.gateway.PurchaseDTO;

public class PurchaseMapper {

    public Purchase mapToModel(PurchaseDTO purchaseDTO, String email) {
        return Purchase.builder()
                .email(email)
                .event(purchaseDTO.getEvent())
                .eventDate(purchaseDTO.getEventDate())
                .amount(purchaseDTO.getAmount())
                .build();
    }

}
