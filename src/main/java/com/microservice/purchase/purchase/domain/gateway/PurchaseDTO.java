package com.microservice.purchase.purchase.domain.gateway;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class PurchaseDTO {

    private String event;
    private Date eventDate;
    private double amount;

}
