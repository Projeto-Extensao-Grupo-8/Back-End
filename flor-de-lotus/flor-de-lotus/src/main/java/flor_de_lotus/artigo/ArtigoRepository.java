package flor_de_lotus.artigo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtigoRepository extends JpaRepository<Artigo, Integer> {

    @Query("SELECT a FROM Artigo a " +
            "WHERE LOWER(a.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(a.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Artigo> pesquisar(@Param("termo") String termo);
}