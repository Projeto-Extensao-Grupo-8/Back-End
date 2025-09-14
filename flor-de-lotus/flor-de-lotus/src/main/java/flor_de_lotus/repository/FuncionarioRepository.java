package flor_de_lotus.repository;

import flor_de_lotus.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Boolean existsByCrp(String crp);
    
}
