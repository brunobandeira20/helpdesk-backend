package com.bruno.helpdesk.resources;

import com.bruno.helpdesk.domain.dtos.CredenciaisDTO;
import com.bruno.helpdesk.domain.dtos.LoginResponseDTO;
import com.bruno.helpdesk.security.TokenService;
import com.bruno.helpdesk.security.UserSS;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AutenticacaoResource {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid CredenciaisDTO credencialDTO) {

    var usernamePassword = new UsernamePasswordAuthenticationToken(credencialDTO.getEmail(), credencialDTO.getSenha());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((UserSS) auth.getPrincipal());
    return ResponseEntity.ok(new LoginResponseDTO(token));
  }
}