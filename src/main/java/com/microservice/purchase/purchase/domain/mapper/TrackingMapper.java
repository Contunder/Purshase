package com.microservice.purchase.purchase.domain.mapper;


import com.microservice.purchase.purchase.domain.entities.Purchase;
import com.microservice.purchase.purchase.domain.gateway.TrackingDTO;

public class TrackingMapper {

    public TrackingDTO mapToDto(Purchase purchase, String eventType) {
        return TrackingDTO.builder()
                .email(purchase.getEmail())
                .createType(eventType)
                .createId(purchase.getId())
                .build();
    }

}
