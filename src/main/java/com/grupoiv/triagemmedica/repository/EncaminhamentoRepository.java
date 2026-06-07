package com.grupoiv.triagemmedica.repository;

import com.grupoiv.triagemmedica.entity.Encaminhamento;
import com.grupoiv.triagemmedica.enums.EstadoEncaminhamento;
import com.grupoiv.triagemmedica.enums.Prioridade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncaminhamentoRepository extends JpaRepository<Encaminhamento, Long> {

    List<Encaminhamento> findByMedicoId(Long medicoId);

    List<Encaminhamento> findByEspecialidadeContainingIgnoreCase(String especialidade);

    List<Encaminhamento> findByPrioridade(Prioridade prioridade);

    List<Encaminhamento> findByEstado(EstadoEncaminhamento estado);

    Optional<Encaminhamento> findByAvaliacaoId(Long avaliacaoId);
}
