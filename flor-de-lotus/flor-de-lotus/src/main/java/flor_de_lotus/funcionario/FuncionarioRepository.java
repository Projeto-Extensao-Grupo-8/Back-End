package flor_de_lotus.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Boolean existsByCrp(String crp);
    long countByAtivoTrue();
    long countByAtivoFalse();

    @Query("SELECT COUNT(DISTINCT f.especialidade) FROM Funcionario f WHERE f.especialidade IS NOT NULL")
    long countDistinctEspecialidades();
}
