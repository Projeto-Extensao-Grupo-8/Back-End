package flor_de_lotus.teste;

import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.teste.dto.TesteMapper;
import flor_de_lotus.teste.dto.TestePostRequest;
import flor_de_lotus.teste.dto.TesteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TesteService {
    public final TesteRepository repository;

    public TesteResponse cadastrar(TestePostRequest dto){
        if (repository.existsByCodigo(dto.getCodigo())){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Teste já cadastrado");
        }
        Teste teste = TesteMapper.toEntity(dto);
        Teste cadastrado = repository.save(teste);
        TesteResponse testeResponse = TesteMapper.toResponse(cadastrado);
        return  testeResponse;
    }

    public TesteResponse findByIdOrThrow(Integer id){
        Optional<Teste> userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.valueOf(404), "Teste não encontrado");
        }
        Teste teste =  userOpt.get();
        TesteResponse testeResponse = TesteMapper.toResponse(teste);
        return  testeResponse;
    }

    public void deletarPorId(Integer id){
        Teste teste = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(404), "Teste não encontrado"));
        repository.delete(teste);

    }

    public List<TesteResponse> listarTodos(){
        List<Teste> testesLista = repository.findAll();
        List<TesteResponse> listaResponse = TesteMapper.toResponseList(testesLista);
        return listaResponse;
    }

    public List<TesteResponse> listarPorCategoria(String categoria){
        List<Teste> lista = repository.findByCategoria(categoria);
        List<TesteResponse> listaResponse= TesteMapper.toResponseList(lista);
        return listaResponse;
    }

    public List<TesteResponse> listarPorTipo(String tipo){
        List<Teste> lista = repository.findByTipo(tipo);
        List<TesteResponse> listaResponse = TesteMapper.toResponseList(lista);
        return listaResponse ;
    }

    public TesteResponse buscarPorValidade(){
      Optional<Teste> testeOpt = repository.findTop1ByValidadeAfterOrderByValidadeAsc(LocalDate.now());
        if (testeOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("Não foi encontrado");
        }

        Teste teste = testeOpt.get();
        TesteResponse testeResponse = TesteMapper.toResponse(teste);

        return testeResponse;
    }

    public TesteResponse buscarPorQtd(){
//        Optional<Teste> testeOpt =  repository.findTop1ByOrderByEstoqueAtualAsc();
//        if (testeOpt.isEmpty()) {
//            throw new EntidadeNaoEncontradoException("Teste não encontrado");
//        }
//        Teste teste = testeOpt.get();
//        TesteResponse testeResponse = TesteMapper.toResponse(teste);

        return new TesteResponse();
    }





}
