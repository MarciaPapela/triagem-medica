package com.grupoiv.triagemmedica.mapper;

import com.grupoiv.triagemmedica.dto.AvaliacaoSintomasRequest;
import com.grupoiv.triagemmedica.dto.AvaliacaoSintomasResponse;
import com.grupoiv.triagemmedica.entity.AvaliacaoSintomas;
import com.grupoiv.triagemmedica.entity.Paciente;

public final class AvaliacaoSintomasMapper {

    private AvaliacaoSintomasMapper() {
    }

    public static AvaliacaoSintomas toEntity(AvaliacaoSintomasRequest request, Paciente paciente) {
        if (request == null || paciente == null) {
            return null;
        }

        return AvaliacaoSintomas.builder()
                .paciente(paciente)
                .descricaoSintomas(request.getDescricaoSintomas())
                .temperatura(request.getTemperatura())
                .gravidade(request.getGravidade())
                .recomendacao(request.getRecomendacao())
                .build();
    }

    public static AvaliacaoSintomasResponse toResponse(AvaliacaoSintomas avaliacao) {
        if (avaliacao == null) {
            return null;
        }

        Paciente paciente = avaliacao.getPaciente();

        return AvaliacaoSintomasResponse.builder()
                .id(avaliacao.getId())
                .dataAvaliacao(avaliacao.getDataAvaliacao())
                .pacienteId(paciente != null ? paciente.getId() : null)
                .pacienteNome(buildPacienteNome(paciente))
                .pacienteNid(paciente != null ? paciente.getNid() : null)
                .descricaoSintomas(avaliacao.getDescricaoSintomas())
                .temperatura(avaliacao.getTemperatura())
                .gravidade(avaliacao.getGravidade())
                .recomendacao(avaliacao.getRecomendacao())
                .createdAt(avaliacao.getCreatedAt())
                .updatedAt(avaliacao.getUpdatedAt())
                .build();
    }

    public static void updateEntity(
            AvaliacaoSintomas avaliacao,
            AvaliacaoSintomasRequest request,
            Paciente paciente) {
        if (avaliacao == null || request == null || paciente == null) {
            return;
        }

        avaliacao.setPaciente(paciente);
        avaliacao.setDescricaoSintomas(request.getDescricaoSintomas());
        avaliacao.setTemperatura(request.getTemperatura());
        avaliacao.setGravidade(request.getGravidade());
        avaliacao.setRecomendacao(request.getRecomendacao());
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
}
