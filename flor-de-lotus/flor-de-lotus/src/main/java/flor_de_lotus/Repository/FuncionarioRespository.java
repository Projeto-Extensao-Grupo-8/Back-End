package flor_de_lotus.Repository;

import flor_de_lotus.Domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRespository extends JpaRepository<Funcionario, Integer> {
}
