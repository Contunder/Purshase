package com.microservice.purchase.purchase.domain.usecase;


import com.microservice.purchase.purchase.domain.entities.Purchase;
import com.microservice.purchase.purchase.domain.gateway.PurchaseDTO;
import com.microservice.purchase.purchase.domain.gateway.TrackingDTO;
import com.microservice.purchase.purchase.domain.mapper.JsonBodyHandler;
import com.microservice.purchase.purchase.domain.mapper.PurchaseMapper;
import com.microservice.purchase.purchase.domain.mapper.TrackingMapper;
import com.microservice.purchase.purchase.domain.ports.PurchasePorts;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Component
public class EventPurchase {

    private final PurchaseMapper purchaseMapper;
    private final PurchasePorts purchasePorts;
    private final TrackingMapper trackingMapper;
    private final HttpClient client;

    @Value("${tracking.add}")
    private String trackingURL;

    public EventPurchase(PurchasePorts purchasePorts) {
        this.purchasePorts = purchasePorts;
        this.trackingMapper = new TrackingMapper();
        this.purchaseMapper = new PurchaseMapper();
        this.client = HttpClient.newHttpClient();
    }

    public String execute(PurchaseDTO purchaseDTO, String email, String token) {

        Purchase purchase = purchasePorts.save(
                purchaseMapper.mapToModel(purchaseDTO, email)
        );

        HttpRequest getUserDetails = HttpRequest.newBuilder(
                        URI.create(trackingURL)
                )
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(trackingMapper.mapToDto(purchase)).toString()))
                .build();

        client.sendAsync(getUserDetails, new JsonBodyHandler<>(TrackingDTO.class));


        return "ok";
    }

}
