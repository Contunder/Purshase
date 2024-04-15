package com.microservice.purchase.purchase.domain.gateway;

import com.microservice.purchase.purchase.domain.entities.Purchase;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseListDTO {
    List<PurchaseDTO> purchasesDTO;

    public PurchaseListDTO(List<PurchaseDTO> purchasesDTO) {
        this.purchasesDTO = purchasesDTO;
    }
}
