package com.grupoiv.triagemmedica.service;

import com.grupoiv.triagemmedica.dto.AvaliacaoSintomasRequest;
import com.grupoiv.triagemmedica.dto.AvaliacaoSintomasResponse;
import com.grupoiv.triagemmedica.entity.AvaliacaoSintomas;
import com.grupoiv.triagemmedica.entity.Paciente;
import com.grupoiv.triagemmedica.enums.Gravidade;
import com.grupoiv.triagemmedica.exception.ResourceNotFoundException;
import com.grupoiv.triagemmedica.mapper.AvaliacaoSintomasMapper;
import com.grupoiv.triagemmedica.repository.AvaliacaoSintomasRepository;
import com.grupoiv.triagemmedica.repository.PacienteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvaliacaoSintomasService {

    private final AvaliacaoSintomasRepository avaliacaoSintomasRepository;
    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public List<AvaliacaoSintomasResponse> listarTodas() {
        return avaliacaoSintomasRepository.findAll()
                .stream()
                .map(AvaliacaoSintomasMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AvaliacaoSintomasResponse buscarPorId(Long id) {
        return AvaliacaoSintomasMapper.toResponse(obterAvaliacaoPorId(id));
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoSintomasResponse> buscarPorPaciente(Long pacienteId) {
        return avaliacaoSintomasRepository.findByPacienteId(pacienteId)
                .stream()
                .map(AvaliacaoSintomasMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoSintomasResponse> buscarPorGravidade(Gravidade gravidade) {
        return avaliacaoSintomasRepository.findByGravidade(gravidade)
                .stream()
                .map(AvaliacaoSintomasMapper::toResponse)
                .toList();
    }

    @Transactional
    public AvaliacaoSintomasResponse criar(AvaliacaoSintomasRequest request) {
        Paciente paciente = obterPacientePorId(request.getPacienteId());
        AvaliacaoSintomas avaliacao = AvaliacaoSintomasMapper.toEntity(request, paciente);
        avaliacao.setRecomendacao(resolverRecomendacao(request.getRecomendacao(), request.getGravidade()));

        AvaliacaoSintomas savedAvaliacao = avaliacaoSintomasRepository.save(avaliacao);
        return AvaliacaoSintomasMapper.toResponse(savedAvaliacao);
    }

    @Transactional
    public AvaliacaoSintomasResponse atualizar(Long id, AvaliacaoSintomasRequest request) {
        AvaliacaoSintomas avaliacao = obterAvaliacaoPorId(id);
        Paciente paciente = obterPacientePorId(request.getPacienteId());

        AvaliacaoSintomasMapper.updateEntity(avaliacao, request, paciente);
        avaliacao.setRecomendacao(resolverRecomendacao(request.getRecomendacao(), request.getGravidade()));

        AvaliacaoSintomas updatedAvaliacao = avaliacaoSintomasRepository.save(avaliacao);
        return AvaliacaoSintomasMapper.toResponse(updatedAvaliacao);
    }

    @Transactional
    public void deletar(Long id) {
        AvaliacaoSintomas avaliacao = obterAvaliacaoPorId(id);
        avaliacaoSintomasRepository.delete(avaliacao);
    }

    private AvaliacaoSintomas obterAvaliacaoPorId(Long id) {
        return avaliacaoSintomasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliacao de sintomas nao encontrada com o id informado."));
    }

    private Paciente obterPacientePorId(Long pacienteId) {
        return pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado com o id informado."));
    }

    private String resolverRecomendacao(String recomendacao, Gravidade gravidade) {
        if (recomendacao != null && !recomendacao.isBlank()) {
            return recomendacao;
        }

        return switch (gravidade) {
            case BAIXA -> "Sintomas leves. Recomenda-se repouso, hidratacao e acompanhamento.";
            case MEDIA -> "Sintomas moderados. Recomenda-se consulta medica nao urgente.";
            case ALTA -> "Sintomas graves. Recomenda-se encaminhamento medico prioritario.";
            case CRITICA -> "Estado critico. Recomenda-se atendimento medico urgente.";
        };
    }
}
