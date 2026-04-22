package flor_de_lotus.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Boolean existsByCrp(String crp);
    long countByAtivoTrue();
    long countByAtivoFalse();

    @Query(value = "SELECT COUNT(DISTINCT especialidade) FROM funcionario_especialidade", nativeQuery = true)
    long countDistinctEspecialidades();

    @Query("SELECT f FROM Funcionario f JOIN f.fkUsuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(f.crp) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Funcionario> findByTermo(@Param("termo") String termo);

    @Query("SELECT p FROM Funcionario p WHERE p.ativo = :ativo")
    List<Funcionario> findByAtivo(@Param("ativo") boolean ativo);

    @Query("SELECT f FROM Funcionario f JOIN f.tiposAtendimento t WHERE t.tipoAtendimento = :tipo AND f.ativo = true")
    List<Funcionario> findByTipoAtendimento(@Param("tipo") TipoAtendimento tipo);

    @Query(value = "SELECT * FROM funcionario ORDER BY id_funcionario LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Funcionario> findAllWithPagination(int limit, int offset);

    Optional<Funcionario> findByFkUsuario_IdUsuario(Integer idUsuario);
    Boolean existsByFkUsuario_IdUsuario(Integer idUsuario);

    @Query(value = "SELECT DISTINCT especialidade FROM funcionario_especialidade", nativeQuery = true)
    List<Especialidade> findAllEspecialidades();
}
