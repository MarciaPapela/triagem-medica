package com.grupoiv.triagemmedica.dto;

import jakarta.validation.constraints.NotBlank;
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
public class MedicoRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String apelido;

    @NotBlank
    private String especialidade;

    @NotBlank
    private String numeroOrdem;

    @NotBlank
    private String celular;

    private Boolean disponibilidade;
}
