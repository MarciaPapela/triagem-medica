package com.grupoiv.triagemmedica.repository;

import com.grupoiv.triagemmedica.entity.Role;
import com.grupoiv.triagemmedica.enums.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
