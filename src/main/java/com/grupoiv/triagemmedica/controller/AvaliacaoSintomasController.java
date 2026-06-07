package com.grupoiv.triagemmedica.controller;

import com.grupoiv.triagemmedica.dto.AvaliacaoSintomasRequest;
import com.grupoiv.triagemmedica.dto.AvaliacaoSintomasResponse;
import com.grupoiv.triagemmedica.enums.Gravidade;
import com.grupoiv.triagemmedica.service.AvaliacaoSintomasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/avaliacao")
@RequiredArgsConstructor
@Tag(name = "Avaliacoes", description = "Endpoints para gestao de avaliacoes de sintomas")
public class AvaliacaoSintomasController {

    private final AvaliacaoSintomasService avaliacaoSintomasService;

    @GetMapping
    @Operation(summary = "Listar todas as avaliacoes")
    public ResponseEntity<List<AvaliacaoSintomasResponse>> listarTodas() {
        return ResponseEntity.ok(avaliacaoSintomasService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliacao por id")
    public ResponseEntity<AvaliacaoSintomasResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(avaliacaoSintomasService.buscarPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Buscar avaliacoes por paciente")
    public ResponseEntity<List<AvaliacaoSintomasResponse>> buscarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(avaliacaoSintomasService.buscarPorPaciente(pacienteId));
    }

    @GetMapping("/gravidade/{gravidade}")
    @Operation(summary = "Buscar avaliacoes por gravidade")
    public ResponseEntity<List<AvaliacaoSintomasResponse>> buscarPorGravidade(@PathVariable Gravidade gravidade) {
        return ResponseEntity.ok(avaliacaoSintomasService.buscarPorGravidade(gravidade));
    }

    @PostMapping
    @Operation(summary = "Criar avaliacao")
    public ResponseEntity<AvaliacaoSintomasResponse> criar(@Valid @RequestBody AvaliacaoSintomasRequest request) {
        AvaliacaoSintomasResponse response = avaliacaoSintomasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliacao")
    public ResponseEntity<AvaliacaoSintomasResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoSintomasRequest request) {
        return ResponseEntity.ok(avaliacaoSintomasService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar avaliacao")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoSintomasService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
