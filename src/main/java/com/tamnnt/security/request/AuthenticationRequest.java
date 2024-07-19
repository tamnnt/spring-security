package com.tamnnt.security.request;

import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
public class AuthenticationRequest {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
