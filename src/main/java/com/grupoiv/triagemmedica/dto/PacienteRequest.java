package com.grupoiv.triagemmedica.dto;

import com.grupoiv.triagemmedica.enums.Genero;
import com.grupoiv.triagemmedica.enums.GrupoSanguineo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
public class PacienteRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String apelido;

    @NotBlank
    private String nid;

    @NotNull
    private Genero genero;

    @NotNull
    private LocalDate dataNascimento;

    private GrupoSanguineo grupoSanguineo;

    private String alergias;
}
