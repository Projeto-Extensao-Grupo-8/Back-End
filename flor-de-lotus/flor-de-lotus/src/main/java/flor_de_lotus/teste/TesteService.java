package flor_de_lotus.teste;

import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Teste não encontrado"));
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

    public Integer buscarQtdTesteComValidadeProxima() {
        LocalDate hoje = LocalDate.now();
        LocalDate primeiroDiaDoMes = hoje.withDayOfMonth(1);
        LocalDate ultimoDiaDoMes = hoje.withDayOfMonth(hoje.lengthOfMonth());
        return repository.countByValidadeBetween(primeiroDiaDoMes, ultimoDiaDoMes);
    }

}
