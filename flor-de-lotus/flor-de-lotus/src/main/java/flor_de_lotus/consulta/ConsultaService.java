package flor_de_lotus.consulta;

import flor_de_lotus.consulta.dto.*;
import flor_de_lotus.consulta.dto.dashAgendamento.GraficoDesempenhoSemanal;
import flor_de_lotus.consulta.dto.dashAgendamento.GraficoDistribuicaoHorario;
import flor_de_lotus.consulta.dto.dashAgendamento.KpiCancelamentos;
import flor_de_lotus.consulta.dto.dashFinanceiro.GraficoComparacaoCustoReceita;
import flor_de_lotus.consulta.dto.dashFinanceiro.GraficoConsultaMes;
import flor_de_lotus.consulta.dto.dashFinanceiro.GraficoFaturamentoMensal;
import flor_de_lotus.consulta.dto.dashFinanceiro.KpiMelhorFaturamentoAno;
import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioService;
import flor_de_lotus.paciente.Paciente;
import flor_de_lotus.paciente.PacienteRepository;
import flor_de_lotus.paciente.PacienteService;
import flor_de_lotus.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

        checarData(entity.getData());
        checarValor(entity.getValor());

        entity.setFkFuncionario(funcionario);
        entity.setFkPaciente(paciente);

        return repository.save(entity);
    }

    private void checarData(LocalDateTime dataConsulta) {
        if (dataConsulta == null || dataConsulta.isBefore(LocalDateTime.now())) {
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

        if (entity.getData() != null) {
            checarData(entity.getData());
            consulta.setData(entity.getData());
        }

        if (entity.getValor() != null) {
            checarValor(entity.getValor());
            consulta.setValor(entity.getValor());
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

    public List<Consulta> listarPorPaciente(Integer idPaciente) {
        pacienteService.buscarPorIdOuThrow(idPaciente);
        return repository.findByFkPaciente_IdPaciente(idPaciente);
    }

    public List<Consulta> listarPorFuncionario(Integer idFuncionario) {
        funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.findByFkFuncionario_IdFuncionario(idFuncionario);
    }

    public List<Consulta> listarConsultasPorPaciente(Integer idPaciente) {
        pacienteService.buscarPorIdOuThrow(idPaciente);
        return repository.findByFkPaciente_IdPaciente(idPaciente);
    }

    public List<Consulta> listarConsultasPorFuncionario(Integer idFuncionario) {
        funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.findByFkFuncionario_IdFuncionario(idFuncionario);
    }

    public List<Consulta> listarProximasConsultasFuncionario(Integer idFuncionario) {
        funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.findTop4ProximasConsultasDoFuncionario(idFuncionario);
    }

  public Integer qtdSessoesRealizadasDoPaciente(Integer idPaciente) {
        pacienteService.buscarPorIdOuThrow(idPaciente);
        return repository.qtdSessoesRealizadasDoPaciente(idPaciente);
  }

  public Integer qtdSessoesRealizadasDoFuncionario(Integer idFuncionario) {
        funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.qtdSessoesRealizadasDoFuncionario(idFuncionario);

    }

    public List<Consulta> listarProximasConsultaPaciente(Integer idPaciente) {
        pacienteService.buscarPorIdOuThrow(idPaciente);
        return repository.findTop4ProximasConsultasDoPaciente(idPaciente);
    }

    public List<GraficoDistribuicaoHorario> graficoDistribuicaoHorario() {
        return repository.graficoDistribuicaoHorario();
    }

    public List<GraficoDesempenhoSemanal> graficoDesempenhoSemanal() {
        return repository.graficoDesempenhoSemanal();
    }

    public List<KpiCancelamentos> kpiCancelamentos() {
        return repository.kpiCancelamentos();
    }

    public Long kpiConsultasRealizadas() {
        return repository.kpiConsultasRealizadas().getQtd();
    }

    public String kpiTaxaComparecimento(){
        return repository.kpiTaxaCompareciemento().getPercentual();
    }

    public Long kpiAgendamentosSemana(){
        return repository.kpiAgendamentoSemana().getQtd();
    }

    public List<GraficoConsultaMes> graficoConsultaMes(){
        return repository.graficoConsultaMes();
    }

    public List<GraficoFaturamentoMensal> graficoFaturamentoMensal(){
        return repository.graficoFaturamentoMensal();
    }

    public Double kpiFaturamentoAno(){
        return repository.kpiFaturamentoAno().getFaturamento();
    }

    public Double kpiFaturamentoMes(){
        return repository.kpiFaturamentoMes().getFaturamento();
    }

    public List<KpiMelhorFaturamentoAno> kpiMelhorFaturamentoAno(){
        return repository.kpiFaturamentoMelhorMes();
    }

    public Integer qtdSessoesRealizadasDoFuncionarioEsteMes(Integer idFuncionario) {
        funcionarioService.buscarPorIdOuThrow(idFuncionario);
        return repository.qtdSessoesRealizadasDoFuncionarioEsteMes(idFuncionario);
    }

    public Integer qtdSessoesRealizadasEsteMes() {
        return repository.qtdSessoesRealizadasEsteMes();
    }

    public List<Consulta> consultasDoHoje(){
        return repository.buscarConsultasDeHoje();
    }

    public Integer qtdConsultasDeHoje() {
        return repository.qtdConsultasDeHoje();
    }

    public List<KardResumoFinanceiro> resumoFinanceiroMensal() {
        return repository.resumoFinanceiroMensal();
    }

    public List<GraficoComparacaoCustoReceita> graficoComparacaoCustoReceitas(){
        return repository.graficoComparacaoCustoReceita();
    }

    public Consulta atualizarStatus(Integer idConsulta, StatusConsulta novoStatus) {
        Consulta consulta = buscarPorIdOuThrow(idConsulta);

        switch (novoStatus) {
            case CONFIRMADA -> consulta.confirmar();
            case REALIZADA -> consulta.marcarComoRealizada();
            case CANCELADA -> consulta.cancelar();
            case PENDENTE -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é permitido alterar o status manualmente para PENDENTE."
            );
        }
        return repository.save(consulta);
    }





}
