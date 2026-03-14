package flor_de_lotus.avaliacao;

import flor_de_lotus.avaliacao.dto.GraficoAvaliacaoPorConsulta;
import flor_de_lotus.avaliacao.dto.GraficoAvaliacaoPorFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    @Query(value = "SELECT cinco_estrelas, quatro_estrelas,tres_estrelas,duas_estrelas,uma_estrela, zero_estrela FROM grafico_avaliacoes_por_consulta", nativeQuery = true)
    List<GraficoAvaliacaoPorConsulta> graficoAvaliacaoPorConsulta();
    @Query(value = "SELECT nome,cinco_estrelas, quatro_estrelas,tres_estrelas,duas_estrelas,uma_estrela, zero_estrelas FROM grafico_avaliacoes_por_funcionario", nativeQuery = true)
    List<GraficoAvaliacaoPorFuncionario> graficoAvaliacaoPorFuncionario();
}