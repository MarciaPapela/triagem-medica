package com.grupoiv.triagemmedica.security;

import com.grupoiv.triagemmedica.entity.Role;
import com.grupoiv.triagemmedica.entity.Usuario;
import com.grupoiv.triagemmedica.repository.UsuarioRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado com o email informado."));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .disabled(Boolean.FALSE.equals(usuario.getEnabled()))
                .authorities(mapAuthorities(usuario))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(Usuario usuario) {
        return usuario.getRoles()
                .stream()
                .map(Role::getName)
                .filter(roleName -> roleName != null)
                .map(Enum::name)
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
