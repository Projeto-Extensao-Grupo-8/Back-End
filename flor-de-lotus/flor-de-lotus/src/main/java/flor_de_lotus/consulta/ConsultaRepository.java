package flor_de_lotus.consulta;

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
}
