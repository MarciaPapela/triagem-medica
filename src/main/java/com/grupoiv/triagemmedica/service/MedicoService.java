package com.grupoiv.triagemmedica.service;

import com.grupoiv.triagemmedica.dto.MedicoRequest;
import com.grupoiv.triagemmedica.dto.MedicoResponse;
import com.grupoiv.triagemmedica.entity.Medico;
import com.grupoiv.triagemmedica.exception.DuplicateResourceException;
import com.grupoiv.triagemmedica.exception.ResourceNotFoundException;
import com.grupoiv.triagemmedica.mapper.MedicoMapper;
import com.grupoiv.triagemmedica.repository.MedicoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;

    @Transactional(readOnly = true)
    public List<MedicoResponse> listarTodos() {
        return medicoRepository.findAll()
                .stream()
                .map(MedicoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MedicoResponse buscarPorId(Long id) {
        return MedicoMapper.toResponse(obterMedicoPorId(id));
    }

    @Transactional(readOnly = true)
    public List<MedicoResponse> buscarPorEspecialidade(String especialidade) {
        return medicoRepository.findByEspecialidadeContainingIgnoreCase(especialidade)
                .stream()
                .map(MedicoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MedicoResponse> listarDisponiveis() {
        return medicoRepository.findByDisponibilidadeTrue()
                .stream()
                .map(MedicoMapper::toResponse)
                .toList();
    }

    @Transactional
    public MedicoResponse criar(MedicoRequest request) {
        if (medicoRepository.existsByNumeroOrdem(request.getNumeroOrdem())) {
            throw new DuplicateResourceException("Ja existe um medico com o numero de ordem informado.");
        }

        Medico medico = MedicoMapper.toEntity(request);
        Medico savedMedico = medicoRepository.save(medico);
        return MedicoMapper.toResponse(savedMedico);
    }

    @Transactional
    public MedicoResponse atualizar(Long id, MedicoRequest request) {
        Medico medico = obterMedicoPorId(id);

        if (!medico.getNumeroOrdem().equals(request.getNumeroOrdem())
                && medicoRepository.existsByNumeroOrdem(request.getNumeroOrdem())) {
            throw new DuplicateResourceException("Ja existe um medico com o numero de ordem informado.");
        }

        MedicoMapper.updateEntity(medico, request);
        Medico updatedMedico = medicoRepository.save(medico);
        return MedicoMapper.toResponse(updatedMedico);
    }

    @Transactional
    public void deletar(Long id) {
        Medico medico = obterMedicoPorId(id);
        medicoRepository.delete(medico);
    }

    private Medico obterMedicoPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico nao encontrado com o id informado."));
    }
}
