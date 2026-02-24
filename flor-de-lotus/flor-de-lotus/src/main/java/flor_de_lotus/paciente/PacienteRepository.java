package flor_de_lotus.paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Boolean existsByFkUsuario_IdUsuario(Integer id);
    Optional<Paciente> findByFkUsuario_IdUsuario(Integer idUsuario);
}
