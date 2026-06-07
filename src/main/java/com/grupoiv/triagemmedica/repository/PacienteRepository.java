package com.grupoiv.triagemmedica.repository;

import com.grupoiv.triagemmedica.entity.Paciente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByNid(String nid);

    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    boolean existsByNid(String nid);
}
