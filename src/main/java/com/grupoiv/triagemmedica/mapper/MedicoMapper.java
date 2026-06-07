package com.grupoiv.triagemmedica.mapper;

import com.grupoiv.triagemmedica.dto.MedicoRequest;
import com.grupoiv.triagemmedica.dto.MedicoResponse;
import com.grupoiv.triagemmedica.entity.Medico;

public final class MedicoMapper {

    private MedicoMapper() {
    }

    public static Medico toEntity(MedicoRequest request) {
        if (request == null) {
            return null;
        }

        return Medico.builder()
                .nome(request.getNome())
                .apelido(request.getApelido())
                .especialidade(request.getEspecialidade())
                .numeroOrdem(request.getNumeroOrdem())
                .celular(request.getCelular())
                .disponibilidade(request.getDisponibilidade() != null ? request.getDisponibilidade() : true)
                .build();
    }

    public static MedicoResponse toResponse(Medico medico) {
        if (medico == null) {
            return null;
        }

        return MedicoResponse.builder()
                .id(medico.getId())
                .nome(medico.getNome())
                .apelido(medico.getApelido())
                .especialidade(medico.getEspecialidade())
                .numeroOrdem(medico.getNumeroOrdem())
                .celular(medico.getCelular())
                .disponibilidade(medico.getDisponibilidade())
                .createdAt(medico.getCreatedAt())
                .updatedAt(medico.getUpdatedAt())
                .build();
    }

    public static void updateEntity(Medico medico, MedicoRequest request) {
        if (medico == null || request == null) {
            return;
        }

        medico.setNome(request.getNome());
        medico.setApelido(request.getApelido());
        medico.setEspecialidade(request.getEspecialidade());
        medico.setNumeroOrdem(request.getNumeroOrdem());
        medico.setCelular(request.getCelular());

        if (request.getDisponibilidade() != null) {
            medico.setDisponibilidade(request.getDisponibilidade());
        }
    }
}
