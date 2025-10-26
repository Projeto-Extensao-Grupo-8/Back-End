package flor_de_lotus.consulta;

import flor_de_lotus.consulta.dto.ConsultaMapper;
import flor_de_lotus.consulta.dto.ConsultaPostRequestBody;
import flor_de_lotus.consulta.dto.ConsultaResponseBody;
import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioService;
import flor_de_lotus.paciente.Paciente;
import flor_de_lotus.paciente.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository repository;
    private final FuncionarioService funcionarioService;
    private final PacienteService pacienteService;


    public ConsultaResponseBody cadastrar(ConsultaPostRequestBody dto) {

        Funcionario funcionario = funcionarioService.buscarPorIdOuThrow(dto.getFkFuncionario());
        Paciente paciente = pacienteService.buscarPorIdOuThrow(dto.getFkPaciente());

        checarData(dto.getDataConsulta());
        checarValor(dto.getValorConsulta());

        Consulta consulta = ConsultaMapper.of(dto, funcionario, paciente);
        Consulta consultaSalva = repository.save(consulta);

        return ConsultaMapper.of(consultaSalva);
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

    public List<ConsultaResponseBody> listarTodas() {
        return repository.findAll().stream().map(ConsultaMapper::of).toList();
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

    public ConsultaResponseBody atualizarParcial(Integer id, ConsultaPostRequestBody body) {
        Consulta consulta = buscarPorIdOuThrow(id);

        if (body.getDataConsulta() != null) {
            checarData(body.getDataConsulta());
            consulta.setDataConsulta(body.getDataConsulta());
        }

        if (body.getValorConsulta() != null) {
            checarValor(body.getValorConsulta());
            consulta.setValorConsulta(body.getValorConsulta());
        }

        if (body.getEspecialidade() != null) {
            consulta.setEspecialidade(body.getEspecialidade());
        }

        if (body.getFkFuncionario() != null) {
            Funcionario funcionario = funcionarioService.buscarPorIdOuThrow(body.getFkFuncionario());
            consulta.setFkFuncionario(funcionario);
        }

        if (body.getFkPaciente() != null) {
            Paciente paciente = pacienteService.buscarPorIdOuThrow(body.getFkPaciente());
            consulta.setFkPaciente(paciente);
        }

        Consulta consultaAtualizada = repository.save(consulta);
        return ConsultaMapper.of(consultaAtualizada);
    }

    public List<ConsultaResponseBody> listarPorPacienteResponse(Integer idPaciente) {
        Paciente paciente = pacienteService.buscarPorIdOuThrow(idPaciente);
        return repository.findAll().stream()
                .filter(c -> c.getFkPaciente() != null && c.getFkPaciente().getIdPaciente().equals(paciente.getIdPaciente()))
                .map(ConsultaMapper::of)
                .toList();
    }

    public List<Consulta> listarPorPaciente(Integer idPaciente) {
        Paciente paciente = pacienteService.buscarPorIdOuThrow(idPaciente);
        return repository.findAll().stream()
                .filter(c -> c.getFkPaciente() != null && c.getFkPaciente().getIdPaciente().equals(paciente.getIdPaciente()))
                .toList();
    }



    public List<ConsultaResponseBody> listarPorFuncionarioResponse(Integer idFuncionario) {
        Funcionario funcionario = funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.findAll().stream()
                .filter(c -> c.getFkFuncionario() != null && c.getFkFuncionario().getIdFuncionario().equals(funcionario.getIdFuncionario()))
                .map(ConsultaMapper::of)
                .toList();
    }

    public List<Consulta> listarPorFuncionario(Integer idFuncionario) {
        Funcionario funcionario = funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.findAll().stream()
                .filter(c -> c.getFkFuncionario() != null && c.getFkFuncionario().getIdFuncionario().equals(funcionario.getIdFuncionario()))
                .toList();
    }


}
