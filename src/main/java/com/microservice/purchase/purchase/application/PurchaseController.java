package com.microservice.purchase.purchase.application;

import com.microservice.purchase.purchase.domain.gateway.PurchaseDTO;
import com.microservice.purchase.purchase.domain.gateway.PurchaseListDTO;
import com.microservice.purchase.purchase.domain.usecase.EventPurchase;
import com.microservice.purchase.purchase.domain.usecase.UserEvent;
import com.microservice.purchase.purchase.domain.usecase.ViewEvent;
import com.microservice.purchase.security.JwtAuthenticationFilter;
import com.microservice.purchase.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final EventPurchase eventPurchase;
    private final ViewEvent viewEvent;
    private final UserEvent userEvent;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;

    public PurchaseController(EventPurchase eventPurchase, ViewEvent viewEvent, UserEvent userEvent, JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenProvider jwtTokenProvider) {
        this.eventPurchase = eventPurchase;
        this.viewEvent = viewEvent;
        this.userEvent = userEvent;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/")
    public ResponseEntity<String> purchaseEvent(
            HttpServletRequest request,
            @Valid @RequestBody PurchaseDTO purchaseDTO
    ) {
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        jwtTokenProvider.validateToken(token);
        String email = jwtTokenProvider.getUsername(token);

        return new ResponseEntity<>(eventPurchase.execute(purchaseDTO, email, token), HttpStatus.CREATED);
    }

    @GetMapping("/event/{event}")
    public ResponseEntity<PurchaseDTO> purchaseEvent(
            HttpServletRequest request,
            @PathVariable("event") String event
    ) {
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        jwtTokenProvider.validateToken(token);
        String email = jwtTokenProvider.getUsername(token);

        return new ResponseEntity<>(viewEvent.execute(event, email, token), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PurchaseListDTO> purchaseUserEvent(
            HttpServletRequest request
    ) {
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        jwtTokenProvider.validateToken(token);
        String email = jwtTokenProvider.getUsername(token);

        return new ResponseEntity<>(userEvent.execute(email), HttpStatus.OK);
    }

}
