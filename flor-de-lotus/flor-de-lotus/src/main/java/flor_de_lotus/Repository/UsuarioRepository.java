package flor_de_lotus.Repository;

import flor_de_lotus.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmailandCPF (String email, String cpf);


}
