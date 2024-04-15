package com.microservice.purchase.purchase.domain.usecase;


import com.microservice.purchase.purchase.domain.gateway.PurchaseDTO;
import com.microservice.purchase.purchase.domain.mapper.PurchaseMapper;
import com.microservice.purchase.purchase.domain.ports.PurchasePorts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventPurchase {

    private final PurchaseMapper purchaseMapper;
    private final PurchasePorts purchasePorts;

    public EventPurchase(PurchasePorts purchasePorts) {
        this.purchasePorts = purchasePorts;
        this.purchaseMapper = new PurchaseMapper();
    }

    public String execute(PurchaseDTO purchaseDTO, String email) {

        purchasePorts.save(
                purchaseMapper.mapToModel(purchaseDTO, email)
        );

        return "ok";
    }

}
