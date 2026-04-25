package flor_de_lotus.teste;

import flor_de_lotus.teste.projection.AlertaEstoqueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface TesteRepository extends JpaRepository<Teste, Integer> {
    boolean existsByCodigo(String codigo);
    Optional<Teste> findByCodigo(String codigo);
    List<Teste> findByTipo(String tipo);
    List<Teste> findByCategoria(String categoria);
    Integer countByValidadeBetween(LocalDate dataInicial, LocalDate dataFinal);

    @Query("SELECT COUNT(t) FROM Teste t WHERE t.qtd <= t.estoqueMinimo")
    Integer countTestesEstoqueCritico();

    @Query("SELECT COALESCE(SUM(t.qtd), 0) FROM Teste t WHERE UPPER(t.tipo) LIKE '%FÍSICO%' OR UPPER(t.tipo) LIKE '%FISICO%'")
    Integer sumUnidadesFisicas();


    @Query("SELECT COALESCE(SUM(t.qtd), 0) FROM Teste t WHERE UPPER(t.tipo) LIKE '%DIGITAL%'")
    Integer sumUnidadesDigitais();

    @Query("SELECT COALESCE(SUM(t.qtd * t.preco), 0.0) FROM Teste t")
    Double sumValorTotalEstoque();

    @Query("SELECT COALESCE(SUM(t.qtd), 0) FROM Teste t")
    Integer sumTotalUnidades();

    List<Teste> findByStatusEstoqueOrderByQtdAsc(StatusEstoque statusEstoque);

    @Query(value = "SELECT nome, qtd, estoque_minimo AS estoqueMinimo FROM alerta_estoque_testes", nativeQuery = true)
    List<AlertaEstoqueProjection> getAlertasDeEstoque();

}
