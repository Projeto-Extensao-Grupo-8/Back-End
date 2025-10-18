package flor_de_lotus.teste.estoqueTeste;

import flor_de_lotus.teste.Teste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueTesteRespository extends JpaRepository<EstoqueTeste, Integer> {
    Optional<EstoqueTeste> findTop1ByOrderByqtdAtualAsc();
}
