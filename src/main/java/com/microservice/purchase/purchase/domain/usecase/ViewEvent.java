package com.microservice.purchase.purchase.domain.usecase;


import com.microservice.purchase.exception.AccountAPIException;
import com.microservice.purchase.purchase.domain.entities.Purchase;
import com.microservice.purchase.purchase.domain.gateway.PurchaseDTO;
import com.microservice.purchase.purchase.domain.gateway.TrackingDTO;
import com.microservice.purchase.purchase.domain.mapper.JsonBodyHandler;
import com.microservice.purchase.purchase.domain.mapper.PurchaseMapper;
import com.microservice.purchase.purchase.domain.mapper.TrackingMapper;
import com.microservice.purchase.purchase.domain.ports.PurchasePorts;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

@Component
public class ViewEvent {

    private final PurchaseMapper purchaseMapper;
    private final PurchasePorts purchasePorts;
    private final TrackingMapper trackingMapper;
    private final HttpClient client;

    @Value("${tracking.add}")
    private String trackingURL;

    public ViewEvent(PurchasePorts purchasePorts) {
        this.purchasePorts = purchasePorts;
        this.trackingMapper = new TrackingMapper();
        this.purchaseMapper = new PurchaseMapper();
        this.client = HttpClient.newHttpClient();
    }

    public PurchaseDTO execute(String event, String email, String token) {

        Purchase purchase = purchasePorts.findByEmailAndEvent(email, event).orElseThrow(
                () ->  new AccountAPIException(HttpStatus.NOT_FOUND, "User not found with email: " + email)
        );

        HttpRequest getUserDetails = HttpRequest.newBuilder(
                        URI.create(trackingURL)
                )
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(trackingMapper.mapToDto(purchase, "View Purchase")).toString()))
                .build();

        client.sendAsync(getUserDetails, new JsonBodyHandler<>(TrackingDTO.class));

        return purchaseMapper.mapToDto(purchase);
    }

}
