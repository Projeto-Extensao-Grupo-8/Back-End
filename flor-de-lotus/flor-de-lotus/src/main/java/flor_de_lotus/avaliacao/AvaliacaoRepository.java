package flor_de_lotus.avaliacao;

import flor_de_lotus.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
}