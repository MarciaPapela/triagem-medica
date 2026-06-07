package com.grupoiv.triagemmedica.dto;

import com.grupoiv.triagemmedica.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
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
public class EncaminhamentoRequest {

    @NotNull
    private Long avaliacaoId;

    private Long medicoId;

    @NotBlank
    private String especialidade;

    @NotNull
    private Prioridade prioridade;

    private String observacoes;
}
