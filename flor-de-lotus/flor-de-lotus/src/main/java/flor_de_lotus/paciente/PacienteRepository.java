package flor_de_lotus.paciente;

import flor_de_lotus.paciente.dto.ViewTop5paciente;
import flor_de_lotus.paciente.dto.ViewTotalPacientes;
import flor_de_lotus.paciente.dto.ViewTotalPacientesPorAno;
import jdk.dynalink.linker.LinkerServices;
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

    @Query(value = "SELECT total_pacientes_ativos FROM total_pacientes", nativeQuery = true)
    ViewTotalPacientes totalPacientes();

    @Query(value = "SELECT qtd FROM pacientes_ativos_no_ano WHERE ano_consulta = :ano", nativeQuery = true)
    ViewTotalPacientesPorAno totalPacientesPorAno(@Param("ano") Integer ano);

    @Query(value = "SELECT nome_paciente, consultas, valor, ativo FROM top_5_pacientes", nativeQuery = true)
    List<ViewTop5paciente> top5Pacientes();
}
