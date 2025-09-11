package flor_de_lotus.Repository;

import flor_de_lotus.Domain.Teste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TesteRepository extends JpaRepository<Teste, Integer> {
    boolean existsByLinkOrCodigo(String link, String codigo);
    List<Teste> findByTipo(String tipo);
    List<Teste> findByCategoria(String categoria);
    Optional<Teste> findTop1ByValidadeAfterOrderByValidadeAsc(LocalDate hoje);
    Optional<Teste> findTop1ByOrderByEstoqueAtualAsc();


}
