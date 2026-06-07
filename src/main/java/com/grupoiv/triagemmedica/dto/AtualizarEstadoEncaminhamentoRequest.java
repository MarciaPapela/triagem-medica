package com.grupoiv.triagemmedica.dto;

import com.grupoiv.triagemmedica.enums.EstadoEncaminhamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtualizarEstadoEncaminhamentoRequest {

    @NotNull
    private EstadoEncaminhamento estado;

    private String observacoes;
}
