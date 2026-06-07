package com.grupoiv.triagemmedica.dto;

import com.grupoiv.triagemmedica.enums.Genero;
import com.grupoiv.triagemmedica.enums.GrupoSanguineo;
import java.time.LocalDate;
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
public class PacienteResponse {

    private Long id;

    private String nome;

    private String apelido;

    private String nid;

    private Genero genero;

    private LocalDate dataNascimento;

    private GrupoSanguineo grupoSanguineo;

    private String alergias;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
