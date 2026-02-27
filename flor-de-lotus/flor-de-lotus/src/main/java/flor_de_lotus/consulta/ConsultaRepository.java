package flor_de_lotus.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByFkPaciente_IdPaciente(Integer idPaciente);
    List<Consulta> findByFkFuncionario_IdFuncionario(Integer idFuncionario);
}
