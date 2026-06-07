package com.grupoiv.triagemmedica.service;

import com.grupoiv.triagemmedica.dto.UsuarioRequest;
import com.grupoiv.triagemmedica.dto.UsuarioResponse;
import com.grupoiv.triagemmedica.entity.Role;
import com.grupoiv.triagemmedica.entity.Usuario;
import com.grupoiv.triagemmedica.enums.RoleName;
import com.grupoiv.triagemmedica.exception.BadRequestException;
import com.grupoiv.triagemmedica.exception.DuplicateResourceException;
import com.grupoiv.triagemmedica.exception.ResourceNotFoundException;
import com.grupoiv.triagemmedica.mapper.UsuarioMapper;
import com.grupoiv.triagemmedica.repository.RoleRepository;
import com.grupoiv.triagemmedica.repository.UsuarioRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return UsuarioMapper.toResponse(obterUsuarioPorId(id));
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorEmail(String email) {
        return UsuarioMapper.toResponse(obterUsuarioPorEmail(email));
    }

    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Ja existe um usuario com o email informado.");
        }

        Set<Role> roles = resolverRoles(request.getRoles());

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(roles)
                .build();

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(savedUsuario);
    }

    @Transactional
    public void desativar(Long id) {
        Usuario usuario = obterUsuarioPorId(id);
        usuario.setEnabled(false);
        usuarioRepository.save(usuario);
    }

    private Usuario obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com o id informado."));
    }

    private Usuario obterUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com o email informado."));
    }

    private Set<Role> resolverRoles(Set<RoleName> requestedRoles) {
        Set<RoleName> rolesParaBuscar = requestedRoles;

        if (rolesParaBuscar == null || rolesParaBuscar.isEmpty()) {
            rolesParaBuscar = Set.of(RoleName.PACIENTE);
        }

        Set<Role> roles = new LinkedHashSet<>();
        for (RoleName roleName : rolesParaBuscar) {
            if (roleName == null) {
                throw new BadRequestException("Foi informada uma role invalida.");
            }

            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new BadRequestException("A role informada nao existe: " + roleName.name()));
            roles.add(role);
        }

        return roles;
    }
}
