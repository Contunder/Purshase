package com.microservice.purchase.purchase.domain.mapper;


import com.microservice.purchase.purchase.domain.entities.Purchase;
import com.microservice.purchase.purchase.domain.gateway.TrackingDTO;

public class TrackingMapper {

    public TrackingDTO mapToDto(Purchase purchase, String email) {
        return TrackingDTO.builder()
                .email(email)
                .createType("Purchase Ticket")
                .createId(purchase.getId())
                .build();
    }

}
