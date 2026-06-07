package com.grupoiv.triagemmedica.dto;

import com.grupoiv.triagemmedica.enums.EstadoEncaminhamento;
import com.grupoiv.triagemmedica.enums.Prioridade;
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
public class EncaminhamentoResponse {

    private Long id;

    private LocalDateTime dataEncaminhamento;

    private Long avaliacaoId;

    private String pacienteNome;

    private Long medicoId;

    private String medicoNome;

    private String especialidade;

    private Prioridade prioridade;

    private String observacoes;

    private EstadoEncaminhamento estado;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
