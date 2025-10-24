package flor_de_lotus.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Boolean existsByCrp(String crp);
}
