package com.grupoiv.triagemmedica.repository;

import com.grupoiv.triagemmedica.entity.AvaliacaoSintomas;
import com.grupoiv.triagemmedica.enums.Gravidade;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoSintomasRepository extends JpaRepository<AvaliacaoSintomas, Long> {

    List<AvaliacaoSintomas> findByPacienteId(Long pacienteId);

    List<AvaliacaoSintomas> findByGravidade(Gravidade gravidade);

    List<AvaliacaoSintomas> findByPacienteNid(String nid);

    List<AvaliacaoSintomas> findByDataAvaliacaoBetween(LocalDateTime inicio, LocalDateTime fim);
}
