package com.microservice.purchase.purchase.domain.gateway;

import com.microservice.purchase.purchase.domain.entities.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    String email;
    String password;
    Set<Role> roles;
}
