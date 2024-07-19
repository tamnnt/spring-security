package com.tamnnt.security.service.impl;

import com.tamnnt.security.config.jwt.JwtUtilities;
import com.tamnnt.security.dto.UserDto;
import com.tamnnt.security.dto.UserMapper;
import com.tamnnt.security.exception.UserException;
import com.tamnnt.security.model.Role;
import com.tamnnt.security.model.Token;
import com.tamnnt.security.model.User;
import com.tamnnt.security.repository.RoleRepository;
import com.tamnnt.security.repository.TokenRepository;
import com.tamnnt.security.repository.UserRepository;
import com.tamnnt.security.request.AuthenticationRequest;
import com.tamnnt.security.request.RegisterRequest;
import com.tamnnt.security.response.AuthenticationResponse;
import com.tamnnt.security.service.AuthenticationService;
import com.tamnnt.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //    @Qualifier("userService")
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtilities jwtUtilities;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User newUser = new User();
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException(request.getEmail() + " already exist");
        }
        Role role = roleRepository.findByName("USER").get();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(Collections.singletonList(role));
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        User createdUser = userRepository.save(newUser);
        String jwtToken = jwtUtilities.generateToken(createdUser.getEmail(),Collections.singletonList(role.getName()));
        Token token = Token.builder() // luu token xuong db
                .userId(createdUser.getId())
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
        return AuthenticationResponse
                .builder()
                .userDto(UserMapper.mapToUserDto(createdUser))
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        List<String> rolesName = new ArrayList<>();
        user.getRoles().forEach(r -> rolesName.add(r.getName()));
        String token = jwtUtilities.generateToken(user.getEmail(), rolesName);
        UserDto userDto = UserMapper.mapToUserDto(user);
        return AuthenticationResponse.builder()
                .userDto(userDto)
                .token(token).build();
    }
}
