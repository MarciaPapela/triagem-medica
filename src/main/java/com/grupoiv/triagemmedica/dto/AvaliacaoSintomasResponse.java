package com.grupoiv.triagemmedica.dto;

import com.grupoiv.triagemmedica.enums.Gravidade;
import java.math.BigDecimal;
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
public class AvaliacaoSintomasResponse {

    private Long id;

    private LocalDateTime dataAvaliacao;

    private Long pacienteId;

    private String pacienteNome;

    private String pacienteNid;

    private String descricaoSintomas;

    private BigDecimal temperatura;

    private Gravidade gravidade;

    private String recomendacao;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
