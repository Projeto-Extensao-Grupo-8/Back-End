package flor_de_lotus.consulta;

import flor_de_lotus.consulta.dto.KardResumoFinanceiro;
import flor_de_lotus.consulta.dto.dashAgendamento.*;
import flor_de_lotus.consulta.dto.dashFinanceiro.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByFkPaciente_IdPaciente(Integer idPaciente);
    List<Consulta> findByFkFuncionario_IdFuncionario(Integer idFuncionario);
    @Query(value = "SELECT * FROM consulta WHERE fk_funcionario = :idFuncionario AND data >= CURRENT_DATE ORDER BY data ASC LIMIT 4", nativeQuery = true)
    List<Consulta> findTop4ProximasConsultasDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT COUNT(*) FROM consulta WHERE fk_paciente = :idPaciente AND status = 'REALIZADA'", nativeQuery = true)
    Integer qtdSessoesRealizadasDoPaciente(@Param("idPaciente") Integer idPaciente);

    @Query(value = "SELECT COUNT(*) FROM consulta WHERE fk_funcionario = :idFuncionario AND status = 'REALIZADA'", nativeQuery = true)
    Integer qtdSessoesRealizadasDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT * FROM consulta WHERE fk_paciente = :idPaciente AND data >= CURRENT_DATE ORDER BY data ASC LIMIT 4", nativeQuery = true)
    List<Consulta> findTop4ProximasConsultasDoPaciente(@Param("idPaciente") Integer idPaciente);

    @Query(value = "SELECT quantidade,periodo FROM grafico_distribuicao_horario", nativeQuery = true)
    List<GraficoDistribuicaoHorario> graficoDistribuicaoHorario();

    @Query(value = "SELECT status, quantidade FROM grafico_desempenho_semanal", nativeQuery = true)
    List<GraficoDesempenhoSemanal> graficoDesempenhoSemanal();

    @Query(value = "SELECT qtd_canceladas, percentual FROM kpi_cancelamentos", nativeQuery = true)
    List<KpiCancelamentos> kpiCancelamentos();

    @Query(value = "SELECT qtd FROM kpi_consultas_realizadas", nativeQuery = true)
    KpiConsultasRealizadas kpiConsultasRealizadas();

    @Query(value = "SELECT percentual FROM kpi_taxa_comparecimento", nativeQuery = true)
    KpiTaxaComparecimento kpiTaxaCompareciemento();

    @Query(value = "SELECT qtd FROM kpi_agendamentos_semana", nativeQuery = true)
    KpiAgendamentoSemana kpiAgendamentoSemana();

    @Query(value = "SELECT faturamento FROM kpi_faturamento_mes", nativeQuery = true)
    KpiFaturamentoMes kpiFaturamentoMes();

    @Query(value = "SELECT mes, total_mes FROM kpi_faturamento_melhor_mes", nativeQuery = true)
    List<KpiMelhorFaturamentoAno> kpiFaturamentoMelhorMes();

    @Query(value = "SELECT faturamento FROM kpi_faturamento_ano ", nativeQuery = true)
    KpiFaturamentoAno kpiFaturamentoAno();

    @Query(value = "SELECT mes, valor from grafico_faturamento_mensal", nativeQuery = true)
    List<GraficoFaturamentoMensal> graficoFaturamentoMensal();

    @Query(value = "SELECT mes, qtd_consultas from grafico_consultas_mes", nativeQuery = true)
    List<GraficoConsultaMes> graficoConsultaMes();

    @Query(value = "SELECT COUNT(*) FROM consulta WHERE fk_funcionario = :idFuncionario AND status = 'REALIZADA' AND MONTH(data) = MONTH(CURDATE()) AND YEAR(data) = YEAR(CURDATE())", nativeQuery = true)
    Integer qtdSessoesRealizadasDoFuncionarioEsteMes(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT COUNT(*) FROM consulta WHERE status = 'REALIZADA' AND MONTH(data) = MONTH(CURDATE()) AND YEAR(data) = YEAR(CURDATE())", nativeQuery = true)
    Integer qtdSessoesRealizadasEsteMes();

    @Query("SELECT c FROM Consulta c WHERE DATE(c.data) = CURRENT_DATE")
    List<Consulta> buscarConsultasDeHoje();

    @Query(value = "SELECT COUNT(*) FROM consulta WHERE DATE(data) = CURDATE()", nativeQuery = true)
    Integer qtdConsultasDeHoje();

    @Query(value = "SELECT faturamento_atual, faturamento_anterior, crescimento_percentual FROM resumo_financeiro_mes ", nativeQuery = true)
    List<KardResumoFinanceiro> resumoFinanceiroMensal();

}
