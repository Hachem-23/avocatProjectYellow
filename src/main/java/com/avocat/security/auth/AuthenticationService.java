package com.avocat.security.auth;

import com.avocat.security.config.JwtService;

import com.avocat.security.repository.UserRepository;
import com.avocat.security.user.Role;
import com.avocat.security.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;


  public AuthenticationResponse register(RegisterRequest request) {
    var avocat = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
    repository.save(avocat);
    var jwtToken = jwtService.generateToken(avocat);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
  }
  public AuthenticationResponse authenticate(AuthenticationRequest request){
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var avocat =repository.findByEmail(request.getEmail())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(avocat);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();


  }
}
