package flor_de_lotus.funcionario;

import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.funcionario.dto.FuncionarioPatchRequestBody;
import flor_de_lotus.funcionario.dto.FuncionarioPostRequestBody;
import flor_de_lotus.funcionario.mapper.FuncionarioMapper;
import flor_de_lotus.usuario.Usuario;
import flor_de_lotus.usuario.UsuarioRepository;
import flor_de_lotus.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository repository;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public Funcionario cadastrar(Funcionario body, Integer idUsuario) {
        Usuario usuarioCa = usuarioService.buscarEntidadePorIdOuThrow(idUsuario);

        if (repository.existsByCrp(body.getCrp())) {
            throw new EntidadeConflitoException("Conflito no campo CRP");
        }

        usuarioCa.setNivelPermissao("3");

        usuarioRepository.save(usuarioCa);

        body.setFkUsuario(usuarioCa);
        return repository.save(body);
    }

    public List<Funcionario> listarTodos(){
        return repository.findAll();
    }

    public Funcionario buscarPorIdOuThrow(Integer id){
        Optional<Funcionario> funcEncontrado = repository.findById(id);
        if (funcEncontrado.isPresent()){
            return funcEncontrado.get();
        }

        throw new EntidadeNaoEncontradoException("Funcionario não encontrado");

    }

    public void deletarPorId(Integer id){
        repository.delete(buscarPorIdOuThrow(id));
    }

    public void desativarProfissional(Integer id){
        Funcionario funcionario = buscarPorIdOuThrow(id);
        if (funcionario.isAtivo()){
            funcionario.setAtivo(false);
            repository.save(funcionario);
        }
    }

    public void ativarProfissional(Integer id){
        Funcionario funcionario = buscarPorIdOuThrow(id);
        if (!funcionario.isAtivo()){
            funcionario.setAtivo(true);
            repository.save(funcionario);
        }

    }

    public Funcionario atualizarParcial(Integer id, FuncionarioPatchRequestBody dto){
       Funcionario funcionario = buscarPorIdOuThrow(id);

       if (dto.getCrp() != null) {
           // Validate CRP is not already in use by another funcionario
           if (repository.existsByCrp(dto.getCrp()) && !funcionario.getCrp().equals(dto.getCrp())) {
               throw new EntidadeConflitoException("CRP já está em uso");
           }
           funcionario.setCrp(dto.getCrp());
       }

       if (dto.getEspecialidade() != null) {
           funcionario.setEspecialidade(dto.getEspecialidade());
       }

       return repository.save(funcionario);
    }

}
