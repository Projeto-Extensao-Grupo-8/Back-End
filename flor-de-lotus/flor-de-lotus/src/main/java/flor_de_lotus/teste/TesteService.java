package flor_de_lotus.teste;

import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.teste.dto.TesteMapper;
import flor_de_lotus.teste.dto.TesteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TesteService {
    public final TesteRepository repository;

    public Teste cadastrar(Teste entity){
        if (repository.existsByCodigo(entity.getCodigo())){
            throw new EntidadeConflitoException("Teste já cadastrado");
        }
        Teste cadastrado = repository.save(entity);

        return cadastrado;

    }

    public Teste findByIdOrThrowResponse(Integer id){
        Optional<Teste> testeOpt = repository.findById(id);
        if (testeOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("Teste não encontrado");
        }
        Teste teste =  testeOpt.get();
        return teste;
    }

    public Teste findByIdOrThrow(Integer id){
        Optional<Teste> testeOpt = repository.findById(id);
        if (testeOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("Teste não encontrado");
        }
        Teste teste =  testeOpt.get();
        return  teste;
    }

    public void deletarPorId(Integer id){
        Teste teste = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(404), "Teste não encontrado"));
        repository.delete(teste);

    }

    public List<Teste> listarTodos(){
        List<Teste> testesLista = repository.findAll();
        return testesLista;
    }

    public List<Teste> listarPorCategoria(String categoria){
        List<Teste> lista = repository.findByCategoria(categoria);
        return lista;
    }

    public List<Teste> listarPorTipo(String tipo){
        List<Teste> lista = repository.findByTipo(tipo);
        return lista;
    }

    public Teste buscarPorValidade(){
      Optional<Teste> testeOpt = repository.findTop1ByValidadeAfterOrderByValidadeAsc(LocalDate.now());

        Teste teste = testeOpt.get();

        return teste;
    }

    public Teste buscarPorValidade(LocalDate dataPersonalizada){
        Optional<Teste> testeOpt = repository.findTop1ByValidadeAfterOrderByValidadeAsc(dataPersonalizada);

        Teste teste = testeOpt.get();

        return teste;
    }

}
