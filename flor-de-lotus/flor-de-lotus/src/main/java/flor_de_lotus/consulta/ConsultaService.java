package flor_de_lotus.consulta;

import flor_de_lotus.consulta.dto.ConsultaMapper;
import flor_de_lotus.consulta.dto.ConsultaResponseBody;
import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioService;
import flor_de_lotus.paciente.Paciente;
import flor_de_lotus.paciente.PacienteRepository;
import flor_de_lotus.paciente.PacienteService;
import flor_de_lotus.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository repository;
    private final PacienteRepository repositoryPac;
    private final FuncionarioService funcionarioService;
    private final PacienteService pacienteService;
    private final UsuarioService usuarioService;

    public Consulta cadastrar(Consulta entity, Integer idUsuario, Integer idFuncionario) {
        Paciente paciente;
        Funcionario funcionario = funcionarioService.buscarPorIdOuThrow(idFuncionario);

        if (repositoryPac.existsByFkUsuario_IdUsuario(idUsuario)){
            paciente = repositoryPac.findByFkUsuario_IdUsuario(idUsuario).orElseThrow(() -> new EntidadeNaoEncontradoException("Paciente não encontrado para usuário informado."));
        }else {
            usuarioService.buscarEntidadePorIdOuThrow(idUsuario);
            paciente = pacienteService.cadastrar(idUsuario);
        }

        checarData(entity.getDataConsulta());
        checarValor(entity.getValorConsulta());

        entity.setFkFuncionario(funcionario);
        entity.setFkPaciente(paciente);

        return repository.save(entity);
    }

    private void checarData(LocalDate dataConsulta) {
        if (dataConsulta == null || dataConsulta.isBefore(LocalDate.now())) {
            throw new BadRequestException("A data da consulta deve ser igual ou posterior à data atual.");
        }
    }

    private void checarValor(Double valorConsulta) {
        if (valorConsulta == null || valorConsulta <= 0) {
            throw new BadRequestException("O valor da consulta deve ser maior que zero.");
        }
    }

    public List<Consulta> listarTodas() {
        return repository.findAll();
    }

    public Consulta buscarPorIdOuThrow(Integer id) {
        Optional<Consulta> consultaOpt = repository.findById(id);
        if (consultaOpt.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Consulta não encontrada.");
        }
        return consultaOpt.get();
    }

    public void deletarPorId(Integer id) {
        repository.delete(buscarPorIdOuThrow(id));
    }

    public Consulta atualizarParcial(Integer id, Consulta entity, Integer idFuncionario, Integer idPaciente) {

        Consulta consulta = buscarPorIdOuThrow(id);

        if (entity.getDataConsulta() != null) {
            checarData(entity.getDataConsulta());
            consulta.setDataConsulta(entity.getDataConsulta());
        }

        if (entity.getValorConsulta() != null) {
            checarValor(entity.getValorConsulta());
            consulta.setValorConsulta(entity.getValorConsulta());
        }

        if (entity.getEspecialidade() != null) {
            consulta.setEspecialidade(entity.getEspecialidade());
        }

        if (entity.getFkFuncionario() != null && idFuncionario != null) {
            Funcionario funcionario = funcionarioService.buscarPorIdOuThrow(idFuncionario);
            consulta.setFkFuncionario(funcionario);
        }

        if (entity.getFkPaciente() != null && idPaciente != null) {
            Paciente paciente = pacienteService.buscarPorIdOuThrow(idPaciente);
            consulta.setFkPaciente(paciente);
        }

        return repository.save(consulta);

    }

    public List<ConsultaResponseBody> listarPorPaciente(Integer idPaciente) {
        pacienteService.buscarPorIdOuThrow(idPaciente);
        return repository.findByFkPaciente_IdPaciente(idPaciente).stream()
                .map(ConsultaMapper::toResponse)
                .toList();
    }

    public List<ConsultaResponseBody> listarPorFuncionario(Integer idFuncionario) {
        funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.findByFkFuncionario_IdFuncionario(idFuncionario).stream()
                .map(ConsultaMapper::toResponse)
                .toList();
    }

    public List<Consulta> listarConsultasPorPaciente(Integer idPaciente) {
        pacienteService.buscarPorIdOuThrow(idPaciente);
        return repository.findByFkPaciente_IdPaciente(idPaciente);
    }

    public List<Consulta> listarConsultasPorFuncionario(Integer idFuncionario) {
        funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.findByFkFuncionario_IdFuncionario(idFuncionario);
    }

}
