package flor_de_lotus.service;

import flor_de_lotus.entity.Funcionario;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.mapper.FuncionarioMapper;
import flor_de_lotus.repository.FuncionarioRepository;
import flor_de_lotus.repository.UsuarioRepository;
import flor_de_lotus.request.FuncionarioPatchRequestBody;
import flor_de_lotus.request.FuncionarioPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private FuncionarioRepository repository;
    private UsuarioRepository Usuariorepository;


    public Funcionario cadastrar(FuncionarioPostRequestBody dto){
        if (repository.existsByCrp(dto.getCrp())){
            throw new EntidadeConflitoException("Conflito no campo CRP");
        }

        Funcionario funcionario = FuncionarioMapper.toEntity(dto);

        return repository.save(funcionario);
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
        }

    }

    public void ativarProfissional(Integer id){
        Funcionario funcionario = buscarPorIdOuThrow(id);
        if (!funcionario.isAtivo()){
            funcionario.setAtivo(true);
        }

    }

    public Funcionario atualizarParcial(Integer id, FuncionarioPatchRequestBody dto){
       Funcionario funcionario = buscarPorIdOuThrow(id);
       if (dto.getCrp() != null) funcionario.setCrp(dto.getCrp());
       if (dto.getEspecialidade() != null) funcionario.setEspecialidade(dto.getEspecialidade());

       return repository.save(funcionario);
    }



}
