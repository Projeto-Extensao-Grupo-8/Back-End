package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Integer> {
    List<MovimentacaoEstoque> findAllByConsultaIdConsulta(Integer idConsulta);

    Optional<MovimentacaoEstoque> findFirstByConsulta_FkPaciente_IdPacienteOrderByDataMovimentacaoDesc(Integer idPaciente);
}
