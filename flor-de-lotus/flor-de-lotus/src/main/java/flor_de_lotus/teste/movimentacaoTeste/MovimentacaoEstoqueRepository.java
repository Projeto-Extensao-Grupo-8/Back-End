package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Integer> {
    List<MovimentacaoEstoque> findAllByConsultaIdConsulta(Integer idConsulta);
}
