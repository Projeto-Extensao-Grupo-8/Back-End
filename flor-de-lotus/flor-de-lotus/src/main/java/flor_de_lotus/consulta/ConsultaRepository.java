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
    @Query("SELECT c FROM Consulta c WHERE c.fkFuncionario.idFuncionario = :idFuncionario AND c.data >= CURRENT_DATE ORDER BY c.data ASC")
    List<Consulta> findTop4ProximasConsultasDoFuncionario(@Param("idFuncionario") Integer idFuncionario);
}
