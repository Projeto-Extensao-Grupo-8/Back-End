package flor_de_lotus.teste.estoqueTeste;

import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.TesteService;
import flor_de_lotus.teste.dto.TesteMapper;
import flor_de_lotus.teste.dto.TesteResponse;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteMapper;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteRequest;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstoqueTesteService {
    private final EstoqueTesteRespository respository;
    private final TesteService testeservice;

    public EstoqueTesteResponse cadastrar(@RequestBody @Valid EstoqueTesteRequest body){
        TesteResponse testeResponse = testeservice.findByIdOrThrow(body.getFkTeste());
        Teste teste = TesteMapper.toEntity(testeResponse);
        teste.setIdTeste(body.getFkTeste());

        System.out.println(teste);

        EstoqueTeste estoqueTeste = EstoqueTesteMapper.toEntity(body);
        estoqueTeste.setDtReferencia(LocalDate.now());
        estoqueTeste.setFkTeste(teste);

        EstoqueTeste estoqueSalvo = respository.save(estoqueTeste);
        EstoqueTesteResponse estoqueTesteResponse = EstoqueTesteMapper.toResponse(estoqueSalvo);

        return estoqueTesteResponse;
    }

    public EstoqueTesteResponse findByIdOrThrow(Integer id){
        Optional<EstoqueTeste> estoqueTesteOpt = respository.findById(id);

        if (estoqueTesteOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("EstoqueTeste não encontrado");
        }
        EstoqueTeste estoqueTeste =  estoqueTesteOpt.get();

        EstoqueTesteResponse estoqueTesteResponse = EstoqueTesteMapper.toResponse(estoqueTeste);
        return  estoqueTesteResponse;
    }

    public EstoqueTesteResponse atualizarParcial(Integer id,EstoqueTesteRequest body){
        EstoqueTesteResponse estoqueTesteResponse = findByIdOrThrow(id);
        EstoqueTeste estTeste = EstoqueTesteMapper.toEntity(estoqueTesteResponse);

        Teste teste = TesteMapper.toEntity(testeservice.findByIdOrThrow(body.getFkTeste()));
        teste.setIdTeste(body.getFkTeste());
        estTeste.setFkTeste(teste);

        if (body.getQtdAtual() != null) estTeste.setQtdAtual(body.getQtdAtual());
        respository.save(estTeste);

        EstoqueTesteResponse estTesteResponse = EstoqueTesteMapper.toResponse(estTeste);
        return  estTesteResponse;
    }

    public void deletar(Integer id){
       EstoqueTesteResponse encontrarEstoqueTeste = findByIdOrThrow(id);
       EstoqueTeste estoqueTeste = EstoqueTesteMapper.toEntity(encontrarEstoqueTeste);
        respository.delete(estoqueTeste);
    }


    public List<EstoqueTesteResponse> listarTodos (){
        List<EstoqueTeste>  todos =  respository.findAll();
        List<EstoqueTesteResponse> todosResponse = EstoqueTesteMapper.toResponseList(todos);

        return todosResponse;
    }

    public EstoqueTesteResponse buscarPorQtd(){
        Optional<EstoqueTeste> testeEncontrado =  respository.findTop1ByOrderByQtdAtualAsc();

        EstoqueTeste teste = testeEncontrado.get();
        EstoqueTesteResponse testeResponse = EstoqueTesteMapper.toResponse(teste);

        return testeResponse;
    }

}
