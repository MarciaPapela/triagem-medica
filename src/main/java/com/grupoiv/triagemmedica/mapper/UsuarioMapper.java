package com.grupoiv.triagemmedica.mapper;

import com.grupoiv.triagemmedica.dto.UsuarioResponse;
import com.grupoiv.triagemmedica.entity.Role;
import com.grupoiv.triagemmedica.entity.Usuario;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .enabled(usuario.getEnabled())
                .roles(mapRoles(usuario.getRoles()))
                .createdAt(usuario.getCreatedAt())
                .updatedAt(usuario.getUpdatedAt())
                .build();
    }

    private static Set<String> mapRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptySet();
        }

        return roles.stream()
                .map(Role::getName)
                .filter(roleName -> roleName != null)
                .map(Enum::name)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
