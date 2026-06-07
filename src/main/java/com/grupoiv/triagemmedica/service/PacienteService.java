package com.grupoiv.triagemmedica.service;

import com.grupoiv.triagemmedica.dto.PacienteRequest;
import com.grupoiv.triagemmedica.dto.PacienteResponse;
import com.grupoiv.triagemmedica.entity.Paciente;
import com.grupoiv.triagemmedica.exception.DuplicateResourceException;
import com.grupoiv.triagemmedica.exception.ResourceNotFoundException;
import com.grupoiv.triagemmedica.mapper.PacienteMapper;
import com.grupoiv.triagemmedica.repository.PacienteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public List<PacienteResponse> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscarPorId(Long id) {
        return PacienteMapper.toResponse(obterPacientePorId(id));
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscarPorNid(String nid) {
        return PacienteMapper.toResponse(obterPacientePorNid(nid));
    }

    @Transactional(readOnly = true)
    public List<PacienteResponse> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(PacienteMapper::toResponse)
                .toList();
    }

    @Transactional
    public PacienteResponse criar(PacienteRequest request) {
        if (pacienteRepository.existsByNid(request.getNid())) {
            throw new DuplicateResourceException("Ja existe um paciente com o NID informado.");
        }

        Paciente paciente = PacienteMapper.toEntity(request);
        Paciente savedPaciente = pacienteRepository.save(paciente);
        return PacienteMapper.toResponse(savedPaciente);
    }

    @Transactional
    public PacienteResponse atualizar(Long id, PacienteRequest request) {
        Paciente paciente = obterPacientePorId(id);

        if (!paciente.getNid().equals(request.getNid()) && pacienteRepository.existsByNid(request.getNid())) {
            throw new DuplicateResourceException("Ja existe um paciente com o NID informado.");
        }

        PacienteMapper.updateEntity(paciente, request);
        Paciente updatedPaciente = pacienteRepository.save(paciente);
        return PacienteMapper.toResponse(updatedPaciente);
    }

    @Transactional
    public void deletar(Long id) {
        Paciente paciente = obterPacientePorId(id);
        pacienteRepository.delete(paciente);
    }

    private Paciente obterPacientePorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado com o id informado."));
    }

    private Paciente obterPacientePorNid(String nid) {
        return pacienteRepository.findByNid(nid)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente nao encontrado com o NID informado."));
    }
}
