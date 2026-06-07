package com.grupoiv.triagemmedica.config;

import com.grupoiv.triagemmedica.entity.AvaliacaoSintomas;
import com.grupoiv.triagemmedica.entity.Encaminhamento;
import com.grupoiv.triagemmedica.entity.Medico;
import com.grupoiv.triagemmedica.entity.Paciente;
import com.grupoiv.triagemmedica.entity.Role;
import com.grupoiv.triagemmedica.entity.Usuario;
import com.grupoiv.triagemmedica.enums.EstadoEncaminhamento;
import com.grupoiv.triagemmedica.enums.Genero;
import com.grupoiv.triagemmedica.enums.Gravidade;
import com.grupoiv.triagemmedica.enums.GrupoSanguineo;
import com.grupoiv.triagemmedica.enums.Prioridade;
import com.grupoiv.triagemmedica.enums.RoleName;
import com.grupoiv.triagemmedica.repository.AvaliacaoSintomasRepository;
import com.grupoiv.triagemmedica.repository.EncaminhamentoRepository;
import com.grupoiv.triagemmedica.repository.MedicoRepository;
import com.grupoiv.triagemmedica.repository.PacienteRepository;
import com.grupoiv.triagemmedica.repository.RoleRepository;
import com.grupoiv.triagemmedica.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final AvaliacaoSintomasRepository avaliacaoSintomasRepository;
    private final EncaminhamentoRepository encaminhamentoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        seedRoles();
        seedUsuarios();
        seedDadosMedicos();
    }

    private void seedRoles() {
        criarRoleSeNaoExistir(RoleName.ADMIN);
        criarRoleSeNaoExistir(RoleName.PACIENTE);
        criarRoleSeNaoExistir(RoleName.MEDICO);
    }

    private void criarRoleSeNaoExistir(RoleName roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name(roleName)
                                .build()
                ));
    }

    private void seedUsuarios() {
        criarUsuarioSeNaoExistir(
                "Marcia Papela",
                "marcia@triagem.co.mz",
                "Admin@123",
                RoleName.ADMIN
        );

        criarUsuarioSeNaoExistir(
                "Herminia Teste",
                "herminia@triagem.co.mz",
                "Medico@123",
                RoleName.MEDICO
        );

        criarUsuarioSeNaoExistir(
                "Jacinto Teste",
                "jacinto@triagem.co.mz",
                "Paciente@123",
                RoleName.PACIENTE
        );
    }

    private void criarUsuarioSeNaoExistir(String nome, String email, String password, RoleName roleName) {
        if (usuarioRepository.existsByEmail(email)) {
            return;
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalStateException("Role não encontrada: " + roleName));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        Usuario usuario = Usuario.builder()
                .nome(nome)
                .email(email)
                .password(passwordEncoder.encode(password))
                .enabled(true)
                .roles(roles)
                .build();

        usuarioRepository.save(usuario);
    }

    private void seedDadosMedicos() {
        Paciente paciente = pacienteRepository.findByNid("123456789A")
                .orElseGet(() -> pacienteRepository.save(
                        Paciente.builder()
                                .nome("Jacinto")
                                .apelido("Teste")
                                .nid("123456789A")
                                .genero(Genero.MASCULINO)
                                .dataNascimento(LocalDate.of(1998, 5, 10))
                                .grupoSanguineo(GrupoSanguineo.O_POSITIVO)
                                .alergias("Nenhuma")
                                .build()
                ));

        Medico medico = medicoRepository.findByNumeroOrdem("OM-001")
                .orElseGet(() -> medicoRepository.save(
                        Medico.builder()
                                .nome("Herminia")
                                .apelido("Teste")
                                .especialidade("Clínica Geral")
                                .numeroOrdem("OM-001")
                                .celular("840000001")
                                .disponibilidade(true)
                                .build()
                ));

        AvaliacaoSintomas avaliacao = criarAvaliacaoSeNaoExistir(paciente);

        criarEncaminhamentoSeNaoExistir(avaliacao, medico);
    }

    private AvaliacaoSintomas criarAvaliacaoSeNaoExistir(Paciente paciente) {
        return avaliacaoSintomasRepository.findByPacienteId(paciente.getId())
                .stream()
                .findFirst()
                .orElseGet(() -> avaliacaoSintomasRepository.save(
                        AvaliacaoSintomas.builder()
                                .paciente(paciente)
                                .dataAvaliacao(LocalDateTime.now())
                                .descricaoSintomas("Febre, dor de cabeça e tosse")
                                .temperatura(new BigDecimal("38.5"))
                                .gravidade(Gravidade.MEDIA)
                                .recomendacao("Consulta médica recomendada")
                                .build()
                ));
    }

    private void criarEncaminhamentoSeNaoExistir(AvaliacaoSintomas avaliacao, Medico medico) {
        if (encaminhamentoRepository.findByAvaliacaoId(avaliacao.getId()).isPresent()) {
            return;
        }

        Encaminhamento encaminhamento = Encaminhamento.builder()
                .avaliacao(avaliacao)
                .medico(medico)
                .dataEncaminhamento(LocalDateTime.now())
                .especialidade("Clínica Geral")
                .prioridade(Prioridade.MEDIA)
                .observacoes("Encaminhamento inicial para consulta geral")
                .estado(EstadoEncaminhamento.PENDENTE)
                .build();

        encaminhamentoRepository.save(encaminhamento);
    }
}