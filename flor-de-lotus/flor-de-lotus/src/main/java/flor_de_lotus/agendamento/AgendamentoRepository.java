package flor_de_lotus.agendamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
    List<Agendamento> findByFkFuncionario_IdFuncionarioAndDataDia(Integer idFuncionario, LocalDate dataDia);
}
