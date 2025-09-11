package flor_de_lotus.Repository;

import flor_de_lotus.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmail (String email);
    boolean existsByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);


}
