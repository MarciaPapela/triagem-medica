package com.grupoiv.triagemmedica.mapper;

import com.grupoiv.triagemmedica.dto.EncaminhamentoRequest;
import com.grupoiv.triagemmedica.dto.EncaminhamentoResponse;
import com.grupoiv.triagemmedica.entity.AvaliacaoSintomas;
import com.grupoiv.triagemmedica.entity.Encaminhamento;
import com.grupoiv.triagemmedica.entity.Medico;
import com.grupoiv.triagemmedica.entity.Paciente;

public final class EncaminhamentoMapper {

    private EncaminhamentoMapper() {
    }

    public static Encaminhamento toEntity(
            EncaminhamentoRequest request,
            AvaliacaoSintomas avaliacao,
            Medico medico) {
        if (request == null || avaliacao == null) {
            return null;
        }

        return Encaminhamento.builder()
                .avaliacao(avaliacao)
                .medico(medico)
                .especialidade(request.getEspecialidade())
                .prioridade(request.getPrioridade())
                .observacoes(request.getObservacoes())
                .build();
    }

    public static EncaminhamentoResponse toResponse(Encaminhamento encaminhamento) {
        if (encaminhamento == null) {
            return null;
        }

        AvaliacaoSintomas avaliacao = encaminhamento.getAvaliacao();
        Medico medico = encaminhamento.getMedico();

        return EncaminhamentoResponse.builder()
                .id(encaminhamento.getId())
                .dataEncaminhamento(encaminhamento.getDataEncaminhamento())
                .avaliacaoId(avaliacao != null ? avaliacao.getId() : null)
                .pacienteNome(buildPacienteNome(avaliacao != null ? avaliacao.getPaciente() : null))
                .medicoId(medico != null ? medico.getId() : null)
                .medicoNome(buildMedicoNome(medico))
                .especialidade(encaminhamento.getEspecialidade())
                .prioridade(encaminhamento.getPrioridade())
                .observacoes(encaminhamento.getObservacoes())
                .estado(encaminhamento.getEstado())
                .createdAt(encaminhamento.getCreatedAt())
                .updatedAt(encaminhamento.getUpdatedAt())
                .build();
    }

    public static void updateEntity(
            Encaminhamento encaminhamento,
            EncaminhamentoRequest request,
            AvaliacaoSintomas avaliacao,
            Medico medico) {
        if (encaminhamento == null || request == null || avaliacao == null) {
            return;
        }

        encaminhamento.setAvaliacao(avaliacao);
        encaminhamento.setMedico(medico);
        encaminhamento.setEspecialidade(request.getEspecialidade());
        encaminhamento.setPrioridade(request.getPrioridade());
        encaminhamento.setObservacoes(request.getObservacoes());
    }

    private static String buildPacienteNome(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        String nome = paciente.getNome();
        String apelido = paciente.getApelido();

        if (nome != null && !nome.isBlank() && apelido != null && !apelido.isBlank()) {
            return nome + " " + apelido;
        }

        if (nome != null && !nome.isBlank()) {
            return nome;
        }

        if (apelido != null && !apelido.isBlank()) {
            return apelido;
        }

        return null;
    }

    private static String buildMedicoNome(Medico medico) {
        if (medico == null) {
            return null;
        }

        String nome = medico.getNome();
        String apelido = medico.getApelido();

        if (nome != null && !nome.isBlank() && apelido != null && !apelido.isBlank()) {
            return nome + " " + apelido;
        }

        if (nome != null && !nome.isBlank()) {
            return nome;
        }

        if (apelido != null && !apelido.isBlank()) {
            return apelido;
        }

        return null;
    }
}
