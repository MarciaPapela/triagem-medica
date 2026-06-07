package com.grupoiv.triagemmedica.service;

import com.grupoiv.triagemmedica.dto.LoginRequest;
import com.grupoiv.triagemmedica.dto.LoginResponse;
import com.grupoiv.triagemmedica.dto.UsuarioResponse;
import com.grupoiv.triagemmedica.entity.Usuario;
import com.grupoiv.triagemmedica.exception.ResourceNotFoundException;
import com.grupoiv.triagemmedica.mapper.UsuarioMapper;
import com.grupoiv.triagemmedica.repository.UsuarioRepository;
import com.grupoiv.triagemmedica.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com o email informado."));

        UsuarioResponse usuarioResponse = UsuarioMapper.toResponse(usuario);

        return LoginResponse.builder()
                .token(jwtService.generateToken(usuario))
                .type("Bearer")
                .id(usuarioResponse.getId())
                .nome(usuarioResponse.getNome())
                .email(usuarioResponse.getEmail())
                .roles(usuarioResponse.getRoles())
                .build();
    }
}
