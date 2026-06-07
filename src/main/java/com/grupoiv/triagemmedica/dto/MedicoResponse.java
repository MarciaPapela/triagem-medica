package com.grupoiv.triagemmedica.dto;

import java.time.LocalDateTime;
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
public class MedicoResponse {

    private Long id;

    private String nome;

    private String apelido;

    private String especialidade;

    private String numeroOrdem;

    private String celular;

    private Boolean disponibilidade;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
