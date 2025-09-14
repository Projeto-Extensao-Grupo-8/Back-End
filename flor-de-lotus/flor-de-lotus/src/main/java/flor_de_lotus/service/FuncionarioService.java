package flor_de_lotus.service;

import flor_de_lotus.entity.Funcionario;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.repository.FuncionarioRepository;
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

    public Funcionario cadastrar(FuncionarioPostRequestBody dto){
        if (repository.existsByCrp(dto.getCrp())){
            throw new EntidadeConflitoException("Conflito no campo CRP");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setCrp(dto.getCrp());
        funcionario.setEspecialidade(dto.getEspecialidade());
        funcionario.setAtivo(dto.isAtivo());
        funcionario.setDtAdmissao(LocalDate.now());
        funcionario.setFkUsuario(dto.getFkUsuario());

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

        throw new EntidadeConflitoException("Funcionario não encontrado");

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

    public Funcionario atualizar



}
