package com.grupoiv.triagemmedica.service;

import com.grupoiv.triagemmedica.dto.AtualizarEstadoEncaminhamentoRequest;
import com.grupoiv.triagemmedica.dto.EncaminhamentoRequest;
import com.grupoiv.triagemmedica.dto.EncaminhamentoResponse;
import com.grupoiv.triagemmedica.entity.AvaliacaoSintomas;
import com.grupoiv.triagemmedica.entity.Encaminhamento;
import com.grupoiv.triagemmedica.entity.Medico;
import com.grupoiv.triagemmedica.enums.EstadoEncaminhamento;
import com.grupoiv.triagemmedica.enums.Prioridade;
import com.grupoiv.triagemmedica.exception.BadRequestException;
import com.grupoiv.triagemmedica.exception.DuplicateResourceException;
import com.grupoiv.triagemmedica.exception.ResourceNotFoundException;
import com.grupoiv.triagemmedica.mapper.EncaminhamentoMapper;
import com.grupoiv.triagemmedica.repository.AvaliacaoSintomasRepository;
import com.grupoiv.triagemmedica.repository.EncaminhamentoRepository;
import com.grupoiv.triagemmedica.repository.MedicoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EncaminhamentoService {

    private final EncaminhamentoRepository encaminhamentoRepository;
    private final AvaliacaoSintomasRepository avaliacaoSintomasRepository;
    private final MedicoRepository medicoRepository;

    @Transactional(readOnly = true)
    public List<EncaminhamentoResponse> listarTodos() {
        return encaminhamentoRepository.findAll()
                .stream()
                .map(EncaminhamentoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EncaminhamentoResponse buscarPorId(Long id) {
        return EncaminhamentoMapper.toResponse(obterEncaminhamentoPorId(id));
    }

    @Transactional(readOnly = true)
    public List<EncaminhamentoResponse> buscarPorMedico(Long medicoId) {
        obterMedicoPorId(medicoId);

        return encaminhamentoRepository.findByMedicoId(medicoId)
                .stream()
                .map(EncaminhamentoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncaminhamentoResponse> buscarPorEspecialidade(String especialidade) {
        return encaminhamentoRepository.findByEspecialidadeContainingIgnoreCase(especialidade)
                .stream()
                .map(EncaminhamentoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncaminhamentoResponse> buscarPorPrioridade(Prioridade prioridade) {
        return encaminhamentoRepository.findByPrioridade(prioridade)
                .stream()
                .map(EncaminhamentoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EncaminhamentoResponse> buscarPorEstado(EstadoEncaminhamento estado) {
        return encaminhamentoRepository.findByEstado(estado)
                .stream()
                .map(EncaminhamentoMapper::toResponse)
                .toList();
    }

    @Transactional
    public EncaminhamentoResponse criar(EncaminhamentoRequest request) {
        AvaliacaoSintomas avaliacao = obterAvaliacaoPorId(request.getAvaliacaoId());

        if (encaminhamentoRepository.findByAvaliacaoId(request.getAvaliacaoId()).isPresent()) {
            throw new DuplicateResourceException("Ja existe um encaminhamento para a avaliacao informada.");
        }

        Medico medico = obterMedicoSeInformado(request.getMedicoId());
        validarDisponibilidadeMedico(medico);

        Encaminhamento encaminhamento = EncaminhamentoMapper.toEntity(request, avaliacao, medico);
        Encaminhamento savedEncaminhamento = encaminhamentoRepository.save(encaminhamento);
        return EncaminhamentoMapper.toResponse(savedEncaminhamento);
    }

    @Transactional
    public EncaminhamentoResponse atualizar(Long id, EncaminhamentoRequest request) {
        Encaminhamento encaminhamento = obterEncaminhamentoPorId(id);
        AvaliacaoSintomas avaliacao = obterAvaliacaoPorId(request.getAvaliacaoId());

        Long avaliacaoAtualId = encaminhamento.getAvaliacao() != null ? encaminhamento.getAvaliacao().getId() : null;
        if (avaliacaoAtualId == null || !avaliacaoAtualId.equals(request.getAvaliacaoId())) {
            encaminhamentoRepository.findByAvaliacaoId(request.getAvaliacaoId())
                    .ifPresent(existing -> {
                        throw new DuplicateResourceException(
                                "Ja existe um encaminhamento para a avaliacao informada.");
                    });
        }

        Medico medico = obterMedicoSeInformado(request.getMedicoId());
        validarDisponibilidadeMedico(medico);

        EncaminhamentoMapper.updateEntity(encaminhamento, request, avaliacao, medico);
        Encaminhamento updatedEncaminhamento = encaminhamentoRepository.save(encaminhamento);
        return EncaminhamentoMapper.toResponse(updatedEncaminhamento);
    }

    @Transactional
    public EncaminhamentoResponse atualizarEstado(Long id, AtualizarEstadoEncaminhamentoRequest request) {
        Encaminhamento encaminhamento = obterEncaminhamentoPorId(id);
        encaminhamento.setEstado(request.getEstado());

        if (request.getObservacoes() != null && !request.getObservacoes().isBlank()) {
            encaminhamento.setObservacoes(request.getObservacoes());
        }

        Encaminhamento updatedEncaminhamento = encaminhamentoRepository.save(encaminhamento);
        return EncaminhamentoMapper.toResponse(updatedEncaminhamento);
    }

    @Transactional
    public void deletar(Long id) {
        Encaminhamento encaminhamento = obterEncaminhamentoPorId(id);
        encaminhamentoRepository.delete(encaminhamento);
    }

    private Encaminhamento obterEncaminhamentoPorId(Long id) {
        return encaminhamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encaminhamento nao encontrado com o id informado."));
    }

    private AvaliacaoSintomas obterAvaliacaoPorId(Long avaliacaoId) {
        return avaliacaoSintomasRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliacao de sintomas nao encontrada com o id informado."));
    }

    private Medico obterMedicoPorId(Long medicoId) {
        return medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Medico nao encontrado com o id informado."));
    }

    private Medico obterMedicoSeInformado(Long medicoId) {
        if (medicoId == null) {
            return null;
        }

        return obterMedicoPorId(medicoId);
    }

    private void validarDisponibilidadeMedico(Medico medico) {
        if (medico != null && !Boolean.TRUE.equals(medico.getDisponibilidade())) {
            throw new BadRequestException("O medico informado nao esta disponivel para encaminhamento.");
        }
    }
}
