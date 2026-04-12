package flor_de_lotus.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Boolean existsByCrp(String crp);
    long countByAtivoTrue();
    long countByAtivoFalse();

    @Query("SELECT COUNT(DISTINCT f.especialidade) FROM Funcionario f WHERE f.especialidade IS NOT NULL")
    long countDistinctEspecialidades();

    @Query("SELECT f FROM Funcionario f JOIN f.fkUsuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(f.crp) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Funcionario> findByTermo(@Param("termo") String termo);

    @Query("SELECT p FROM Funcionario p WHERE p.ativo = :ativo")
    List<Funcionario> findByAtivo(@Param("ativo") boolean ativo);

    @Query("SELECT f FROM Funcionario f JOIN f.tiposAtendimento t WHERE t.tipoAtendimento = :tipo AND f.ativo = true")
    List<Funcionario> findByTipoAtendimento(@Param("tipo") TipoAtendimento tipo);

    @Query(value = "SELECT * FROM funcionario ORDER BY id_funcionario LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Funcionario> findAllWithPagination(int limit, int offset);
}
