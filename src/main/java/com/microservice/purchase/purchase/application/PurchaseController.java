package com.microservice.purchase.purchase.application;

import com.microservice.purchase.purchase.domain.gateway.PurchaseDTO;
import com.microservice.purchase.purchase.domain.usecase.EventPurchase;
import com.microservice.purchase.security.JwtAuthenticationFilter;
import com.microservice.purchase.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final EventPurchase eventPurchase;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;

    public PurchaseController(EventPurchase eventPurchase, JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenProvider jwtTokenProvider) {
        this.eventPurchase = eventPurchase;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/")
    public ResponseEntity<String> createAccount(
            HttpServletRequest request,
            @Valid @RequestBody PurchaseDTO purchaseDTO
    ) {
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        jwtTokenProvider.validateToken(token);
        String email = jwtTokenProvider.getUsername(token);

        return new ResponseEntity<>(eventPurchase.execute(purchaseDTO, email, token), HttpStatus.CREATED);
    }

}
