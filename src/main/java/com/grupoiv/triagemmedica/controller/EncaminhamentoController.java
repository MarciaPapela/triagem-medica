package com.grupoiv.triagemmedica.controller;

import com.grupoiv.triagemmedica.dto.AtualizarEstadoEncaminhamentoRequest;
import com.grupoiv.triagemmedica.dto.EncaminhamentoRequest;
import com.grupoiv.triagemmedica.dto.EncaminhamentoResponse;
import com.grupoiv.triagemmedica.enums.EstadoEncaminhamento;
import com.grupoiv.triagemmedica.enums.Prioridade;
import com.grupoiv.triagemmedica.service.EncaminhamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/encaminhamento")
@RequiredArgsConstructor
@Tag(name = "Encaminhamentos", description = "Endpoints para gestao de encaminhamentos medicos")
public class EncaminhamentoController {

    private final EncaminhamentoService encaminhamentoService;

    @GetMapping
    @Operation(summary = "Listar todos os encaminhamentos")
    public ResponseEntity<List<EncaminhamentoResponse>> listarTodos() {
        return ResponseEntity.ok(encaminhamentoService.listarTodos());
    }

    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Buscar encaminhamentos por medico")
    public ResponseEntity<List<EncaminhamentoResponse>> buscarPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(encaminhamentoService.buscarPorMedico(medicoId));
    }

    @GetMapping("/especialidade/{especialidade}")
    @Operation(summary = "Buscar encaminhamentos por especialidade")
    public ResponseEntity<List<EncaminhamentoResponse>> buscarPorEspecialidade(@PathVariable String especialidade) {
        return ResponseEntity.ok(encaminhamentoService.buscarPorEspecialidade(especialidade));
    }

    @GetMapping("/prioridade/{prioridade}")
    @Operation(summary = "Buscar encaminhamentos por prioridade")
    public ResponseEntity<List<EncaminhamentoResponse>> buscarPorPrioridade(@PathVariable Prioridade prioridade) {
        return ResponseEntity.ok(encaminhamentoService.buscarPorPrioridade(prioridade));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar encaminhamentos por estado")
    public ResponseEntity<List<EncaminhamentoResponse>> buscarPorEstado(
            @PathVariable EstadoEncaminhamento estado) {
        return ResponseEntity.ok(encaminhamentoService.buscarPorEstado(estado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar encaminhamento por id")
    public ResponseEntity<EncaminhamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(encaminhamentoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar encaminhamento")
    public ResponseEntity<EncaminhamentoResponse> criar(@Valid @RequestBody EncaminhamentoRequest request) {
        EncaminhamentoResponse response = encaminhamentoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar encaminhamento")
    public ResponseEntity<EncaminhamentoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EncaminhamentoRequest request) {
        return ResponseEntity.ok(encaminhamentoService.atualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Atualizar apenas o estado do encaminhamento")
    public ResponseEntity<EncaminhamentoResponse> atualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarEstadoEncaminhamentoRequest request) {
        return ResponseEntity.ok(encaminhamentoService.atualizarEstado(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar encaminhamento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        encaminhamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
