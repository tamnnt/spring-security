package com.tamnnt.security.service;

import com.tamnnt.security.request.AuthenticationRequest;
import com.tamnnt.security.request.RegisterRequest;
import com.tamnnt.security.response.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request); // login method
    AuthenticationResponse register(RegisterRequest request);
}
