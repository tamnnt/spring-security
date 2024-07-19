package com.tamnnt.security.controller;


import com.tamnnt.security.config.jwt.JwtUtilities;
import com.tamnnt.security.exception.UserException;
import com.tamnnt.security.repository.UserRepository;
import com.tamnnt.security.request.AuthenticationRequest;
import com.tamnnt.security.request.RegisterRequest;
import com.tamnnt.security.response.AuthenticationResponse;
import com.tamnnt.security.service.AuthenticationService;
import com.tamnnt.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;
//    @Qualifier("userService")
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
@Autowired
private JwtUtilities jwtUtilities;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginRequest(@Validated @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
