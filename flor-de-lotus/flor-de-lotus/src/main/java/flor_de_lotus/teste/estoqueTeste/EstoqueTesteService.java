package flor_de_lotus.teste.estoqueTeste;

import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.TesteService;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteMapper;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstoqueTesteService {
    private final EstoqueTesteRespository respository;
    private final TesteService testeservice;

    public EstoqueTeste cadastrar(@RequestBody @Valid EstoqueTeste entity, Integer idTeste){
        Teste teste = testeservice.findByIdOrThrow(idTeste);

        entity.setDtReferencia(LocalDate.now());
        entity.setFkTeste(teste);

        EstoqueTeste estoqueSalvo = respository.save(entity);

        return estoqueSalvo;
    }

    public EstoqueTeste findByIdOrThrow(Integer id){
        Optional<EstoqueTeste> estoqueTesteOpt = respository.findById(id);

        if (estoqueTesteOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("EstoqueTeste não encontrado");
        }
        EstoqueTeste estoqueTeste =  estoqueTesteOpt.get();

        return  estoqueTeste;
    }

    public EstoqueTeste atualizarParcial(Integer id,EstoqueTeste entity, Integer idTeste){
        EstoqueTeste estoqueTeste = findByIdOrThrow(id);

        Teste teste = testeservice.findByIdOrThrow(idTeste);
        estoqueTeste.setFkTeste(teste);

        if (entity.getQtdAtual() != null) estoqueTeste.setQtdAtual(entity.getQtdAtual());

        return respository.save(estoqueTeste);

    }


    public List<EstoqueTeste> listarTodos (){
        List<EstoqueTeste>  todos =  respository.findAll();

        return todos;
    }

    public EstoqueTeste buscarPorQtd(){
        Optional<EstoqueTeste> testeEncontrado =  respository.findTop1ByOrderByQtdAtualAsc();

        EstoqueTeste teste = testeEncontrado.get();

        return teste;
    }

//    public void deletar(Integer id){
////        respository.delete(findByIdOrThrow(id));
////    }

}
