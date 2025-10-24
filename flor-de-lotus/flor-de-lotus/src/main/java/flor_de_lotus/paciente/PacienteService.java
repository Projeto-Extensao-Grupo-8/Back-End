package flor_de_lotus.paciente;

import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.paciente.dto.PacienteMapper;
import flor_de_lotus.paciente.dto.PacientePostRequestBody;
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

    public PacienteResponseBody cadastrar(PacientePostRequestBody dto) {
        Usuario usuario = usuarioService.buscarPorIdOuThrow(dto.getFkUsuario());
        Paciente paciente = PacienteMapper.of(dto, usuario);
        Paciente salvo = repository.save(paciente);
        return PacienteMapper.of(salvo);
    }

    public List<PacienteResponseBody> listarTodos() {
        return repository.findAll().stream().map(PacienteMapper::of).toList();
    }

    public Paciente buscarPorIdOuThrow(Integer id) {
        Optional<Paciente> pacienteOpt = repository.findById(id);
        if (pacienteOpt.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Paciente não encontrado.");
        }
        return pacienteOpt.get();
    }

    public PacienteResponseBody atualizarParcial(Integer id, PacientePostRequestBody dto) {
        Paciente paciente = buscarPorIdOuThrow(id);
        if (dto.getFkUsuario() != null) {
            Usuario usuario = usuarioService.buscarPorIdOuThrow(dto.getFkUsuario());
            paciente.setFkUsuario(usuario);
        }
        Paciente atualizado = repository.save(paciente);
        return PacienteMapper.of(atualizado);
    }

    public void deletarPorId(Integer id) {
        repository.delete(buscarPorIdOuThrow(id));
    }
}
