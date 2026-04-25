package flor_de_lotus.paciente;

import flor_de_lotus.paciente.dto.dashPaciente.GraficoNovosPacientesPorMes;
import flor_de_lotus.paciente.dto.dashPaciente.GraficoTaxaRetencaoMes;
import flor_de_lotus.paciente.dto.dashPaciente.ViewTop5paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    Boolean existsByFkUsuario_IdUsuario(Integer id);

    Optional<Paciente> findByFkUsuario_IdUsuario(Integer idUsuario);

    @Query(value = "SELECT DISTINCT p.* FROM paciente p INNER JOIN consulta c ON p.id_paciente = c.fk_paciente WHERE c.fk_funcionario = :idFuncionario", nativeQuery = true)
    List<Paciente> findPacientesByFkFuncionario_IdFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT DISTINCT p.* FROM paciente p INNER JOIN consulta c ON p.id_paciente = c.fk_paciente WHERE c.fk_funcionario = ?1 ORDER BY p.id_paciente LIMIT ?2 OFFSET ?3", nativeQuery = true)
    List<Paciente> findPacientesByFkFuncionario_IdFuncionarioWithPagination(Integer idFuncionario, int limit, int offset);

    @Query(value = "SELECT COUNT(*) FROM paciente WHERE ativo = 1", nativeQuery = true)
    Long totalPacientesAtivos();

    @Query(value = "SELECT COUNT(DISTINCT fk_paciente) FROM consulta WHERE YEAR(data) = :ano AND status = 'REALIZADA'", nativeQuery = true)
    Long totalPacientesNoAno(@Param("ano") Integer ano);

    @Query(value = """
            SELECT u.nome AS nome_paciente,
                   COUNT(c.id_consulta) AS consultas,
                   COALESCE(SUM(c.valor), 0.0) AS valor,
                   p.ativo
            FROM paciente p
            INNER JOIN usuario u ON p.fk_usuario = u.id_usuario
            LEFT JOIN consulta c ON p.id_paciente = c.fk_paciente AND c.status = 'REALIZADA'
            GROUP BY p.id_paciente, u.nome, p.ativo
            ORDER BY consultas DESC
            LIMIT 5
            """, nativeQuery = true)
    List<ViewTop5paciente> top5Pacientes();

    @Query(value = "SELECT DISTINCT p.* FROM paciente p INNER JOIN consulta c ON p.id_paciente = c.fk_paciente WHERE c.fk_funcionario = :idFuncionario AND p.ativo = 1", nativeQuery = true)
    List<Paciente> findPacientesAtivosByFkFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = """
            SELECT ROUND(
                SUM(CASE WHEN sessoes > 1 THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(*), 0),
            2)
            FROM (
                SELECT fk_paciente, COUNT(*) AS sessoes
                FROM consulta
                WHERE status = 'REALIZADA'
                GROUP BY fk_paciente
            ) sub
            """, nativeQuery = true)
    BigDecimal kpiTaxaRetencao();

    @Query(value = """
            SELECT MONTH(primeira_consulta) AS mes, COUNT(*) AS novos_pacientes
            FROM (
                SELECT fk_paciente, MIN(data) AS primeira_consulta
                FROM consulta
                WHERE status = 'REALIZADA'
                GROUP BY fk_paciente
            ) primeiras
            WHERE YEAR(primeira_consulta) = YEAR(CURDATE())
            GROUP BY MONTH(primeira_consulta)
            ORDER BY mes
            """, nativeQuery = true)
    List<GraficoNovosPacientesPorMes> graficoNovosPacientesPorMes();

    @Query(value = """
            SELECT
                ELT(mes_atual, 'Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez') AS mes,
                ROUND(
                    SUM(CASE WHEN voltou = 1 THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(*), 0),
                2) AS percentual_retencao
            FROM (
                SELECT
                    MONTH(c1.data) AS mes_atual,
                    c1.fk_paciente,
                    MAX(CASE WHEN c2.id_consulta IS NOT NULL THEN 1 ELSE 0 END) AS voltou
                FROM consulta c1
                LEFT JOIN consulta c2
                    ON  c1.fk_paciente = c2.fk_paciente
                    AND c2.status      = 'REALIZADA'
                    AND YEAR(c2.data)  = YEAR(c1.data)
                    AND MONTH(c2.data) = MONTH(c1.data) + 1
                WHERE c1.status     = 'REALIZADA'
                  AND YEAR(c1.data) = YEAR(CURDATE())
                GROUP BY MONTH(c1.data), c1.fk_paciente
            ) mensal
            GROUP BY mes_atual
            ORDER BY mes_atual
            """, nativeQuery = true)
    List<GraficoTaxaRetencaoMes> graficoTaxaRetencaoMes();

    @Query("SELECT p FROM Paciente p JOIN p.fkUsuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(u.cpf) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Paciente> findByTermo(@Param("termo") String termo);

    @Query("SELECT p FROM Paciente p WHERE p.ativo = :ativo")
    List<Paciente> findByAtivo(@Param("ativo") boolean ativo);
}
