package com.grupoiv.triagemmedica.controller;

import com.grupoiv.triagemmedica.dto.MedicoRequest;
import com.grupoiv.triagemmedica.dto.MedicoResponse;
import com.grupoiv.triagemmedica.service.MedicoService;
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
@RequestMapping("/api/medico")
@RequiredArgsConstructor
@Tag(name = "Medicos", description = "Endpoints para gestao de medicos")
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    @Operation(summary = "Listar todos os medicos")
    public ResponseEntity<List<MedicoResponse>> listarTodos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/especialidade/{especialidade}")
    @Operation(summary = "Buscar medicos por especialidade")
    public ResponseEntity<List<MedicoResponse>> buscarPorEspecialidade(@PathVariable String especialidade) {
        return ResponseEntity.ok(medicoService.buscarPorEspecialidade(especialidade));
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar medicos disponiveis")
    public ResponseEntity<List<MedicoResponse>> listarDisponiveis() {
        return ResponseEntity.ok(medicoService.listarDisponiveis());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medico por id")
    public ResponseEntity<MedicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar medico")
    public ResponseEntity<MedicoResponse> criar(@Valid @RequestBody MedicoRequest request) {
        MedicoResponse response = medicoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medico")
    public ResponseEntity<MedicoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequest request) {
        return ResponseEntity.ok(medicoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar medico")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
