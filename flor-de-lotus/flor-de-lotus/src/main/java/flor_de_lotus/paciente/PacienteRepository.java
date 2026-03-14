package flor_de_lotus.paciente;

import flor_de_lotus.paciente.dto.dashPaciente.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Boolean existsByFkUsuario_IdUsuario(Integer id);
    Optional<Paciente> findByFkUsuario_IdUsuario(Integer idUsuario);

    @Query(value = "SELECT DISTINCT p.* FROM paciente p INNER JOIN consulta c ON p.id_paciente = c.fk_paciente WHERE c.fk_funcionario = :idFuncionario", nativeQuery = true)
    List<Paciente> findPacientesByFkFuncionario_IdFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT total_pacientes_ativos FROM total_pacientes", nativeQuery = true)
    KpiTotalPacientes totalPacientes();

    @Query(value = "SELECT qtd FROM pacientes_ativos_no_ano WHERE ano_consulta = :ano", nativeQuery = true)
    KpiTotalPacientesPorAno totalPacientesNoAno(@Param("ano") Integer ano);

    @Query(value = "SELECT nome_paciente, consultas, valor, ativo FROM top_5_pacientes", nativeQuery = true)
    List<ViewTop5paciente> top5Pacientes();

    @Query(value = "SELECT DISTINCT p.* FROM paciente p INNER JOIN consulta c ON p.id_paciente = c.fk_paciente WHERE c.fk_funcionario = :idFuncionario AND p.ativo = 1", nativeQuery = true)
    List<Paciente> findPacientesAtivosByFkFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT taxa_percentual FROM taxa_retencao", nativeQuery = true)
    KpiTaxaRetencao kpiTaxaRetencao();

    @Query(value = "SELECT mes, novos_pacientes FROM novos_pacientes_por_mes", nativeQuery = true )
    List<GraficoNovosPacientesPorMes> graficoNovosPacientesPorMes();

    @Query(value = "SELECT mes, percentual_retencao FROM taxa_retencao_mes", nativeQuery = true )
    List<GraficoTaxaRetencaoMes> graficoTaxaRetencaoMes();






}

