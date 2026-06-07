package com.grupoiv.triagemmedica.repository;

import com.grupoiv.triagemmedica.entity.Medico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Optional<Medico> findByNumeroOrdem(String numeroOrdem);

    List<Medico> findByEspecialidadeContainingIgnoreCase(String especialidade);

    List<Medico> findByDisponibilidadeTrue();

    boolean existsByNumeroOrdem(String numeroOrdem);
}
