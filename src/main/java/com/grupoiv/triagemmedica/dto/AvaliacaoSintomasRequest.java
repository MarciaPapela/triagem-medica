package com.grupoiv.triagemmedica.dto;

import com.grupoiv.triagemmedica.enums.Gravidade;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
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
public class AvaliacaoSintomasRequest {

    @NotNull
    private Long pacienteId;

    @NotBlank
    private String descricaoSintomas;

    @DecimalMin(value = "30.0")
    @DecimalMax(value = "45.0")
    private BigDecimal temperatura;

    @NotNull
    private Gravidade gravidade;

    private String recomendacao;
}
