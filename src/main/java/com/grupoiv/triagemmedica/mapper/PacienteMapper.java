package com.grupoiv.triagemmedica.mapper;

import com.grupoiv.triagemmedica.dto.PacienteRequest;
import com.grupoiv.triagemmedica.dto.PacienteResponse;
import com.grupoiv.triagemmedica.entity.Paciente;

public final class PacienteMapper {

    private PacienteMapper() {
    }

    public static Paciente toEntity(PacienteRequest request) {
        if (request == null) {
            return null;
        }

        return Paciente.builder()
                .nome(request.getNome())
                .apelido(request.getApelido())
                .nid(request.getNid())
                .genero(request.getGenero())
                .dataNascimento(request.getDataNascimento())
                .grupoSanguineo(request.getGrupoSanguineo())
                .alergias(request.getAlergias())
                .build();
    }

    public static PacienteResponse toResponse(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        return PacienteResponse.builder()
                .id(paciente.getId())
                .nome(paciente.getNome())
                .apelido(paciente.getApelido())
                .nid(paciente.getNid())
                .genero(paciente.getGenero())
                .dataNascimento(paciente.getDataNascimento())
                .grupoSanguineo(paciente.getGrupoSanguineo())
                .alergias(paciente.getAlergias())
                .createdAt(paciente.getCreatedAt())
                .updatedAt(paciente.getUpdatedAt())
                .build();
    }

    public static void updateEntity(Paciente paciente, PacienteRequest request) {
        if (paciente == null || request == null) {
            return;
        }

        paciente.setNome(request.getNome());
        paciente.setApelido(request.getApelido());
        paciente.setNid(request.getNid());
        paciente.setGenero(request.getGenero());
        paciente.setDataNascimento(request.getDataNascimento());
        paciente.setGrupoSanguineo(request.getGrupoSanguineo());
        paciente.setAlergias(request.getAlergias());
    }
}
