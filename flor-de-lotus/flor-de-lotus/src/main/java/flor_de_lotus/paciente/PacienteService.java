package flor_de_lotus.paciente;

import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.paciente.dto.PacienteMapper;
import flor_de_lotus.paciente.dto.PacienteResponseBody;
import flor_de_lotus.usuario.Usuario;
import flor_de_lotus.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;
    private final UsuarioService usuarioService;

    public Paciente cadastrar(Paciente entity, Integer idUsuario) {
        Usuario usuario = usuarioService.buscarEntidadePorIdOuThrow(idUsuario);

        entity.setFkUsuario(usuario);

        Paciente salvo = repository.save(entity);

        return salvo;

    }

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Paciente buscarPorIdOuThrow(Integer id) {
        Optional<Paciente> pacienteOpt = repository.findById(id);
        if (pacienteOpt.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Paciente não encontrado.");
        }
        return pacienteOpt.get();
    }

    public Paciente atualizarParcial(Integer id, Paciente entity, Integer idUsuario) {
        Paciente paciente = buscarPorIdOuThrow(id);
        if (entity.getFkUsuario() != null) {
            Usuario usuario = usuarioService.buscarEntidadePorIdOuThrow(idUsuario);
            paciente.setFkUsuario(usuario);
        }
        Paciente atualizado = repository.save(paciente);

        return atualizado;

    }

    public void deletarPorId(Integer id) {
        repository.delete(buscarPorIdOuThrow(id));
    }

}
