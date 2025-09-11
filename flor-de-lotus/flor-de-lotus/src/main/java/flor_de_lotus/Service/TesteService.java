package flor_de_lotus.Service;

import flor_de_lotus.Domain.Teste;
import flor_de_lotus.Domain.Usuario;
import flor_de_lotus.Repository.TesteRepository;
import flor_de_lotus.Request.TestePatchRequestBody;
import flor_de_lotus.Request.TestePostRequestBody;
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

    public Teste cadastrar(TestePostRequestBody dto){
        if (repository.existsByLinkOrCodigo(dto.getLink(), dto.getCodigo())){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Teste já cadastrado");
        }

        Teste teste = new Teste();
        teste.setCategoria(dto.getCategoria());
        teste.setCodigo(dto.getCodigo());
        teste.setEditora(dto.getEditora());
        teste.setTipo(dto.getTipo());
        teste.setPreco(dto.getPreco());
        teste.setEstoqueMinimo(dto.getEstoqueMinimo());
        teste.setEstoqueAtual(dto.getEstoqueAtual());
        teste.setValidade(dto.getValidade());
        teste.setFkClinica(dto.getFkClinica());

        return repository.save(teste);
    }

    public Teste findByIdOrThrow(Integer id){
        Optional<Teste> userOpt = repository.findById(id);
        if (userOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.valueOf(404), "Teste não encontrado");
        }
        return userOpt.get();
    }

    public void deletarPorId(Integer id){
        repository.delete(findByIdOrThrow(id));
    }

    public List<Teste> listarTodos(){
        return repository.findAll();
    }

    public List<Teste> listarPorCategoria(String categoria){
        if (repository.findByCategoria(categoria).isEmpty()){
            throw new ResponseStatusException(HttpStatus.valueOf(204), "Lista vazia");
        }

        return repository.findByCategoria(categoria);
    }

    public List<Teste> listarPorTipo(String tipo){
        if (repository.findByTipo(tipo).isEmpty()){
            throw new ResponseStatusException(HttpStatus.valueOf(204), "Lista vazia");
        }

        return repository.findByCategoria(tipo);
    }

    public Teste buscarPorValidade(){
      Optional<Teste> testeOpt = repository.findTop1ByValidadeAfterOrderByValidadeAsc(LocalDate.now());
        if (testeOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204), "Não possui testes");
        }

        return testeOpt.get();
    }

    public Teste buscarPorQtd(){
        return repository.findTop1ByOrderByEstoqueAtualAsc()
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(204), "Não possui testes"));
    }

    public Teste atualizarParcial(Integer id, TestePatchRequestBody dto){
        Teste teste = findByIdOrThrow(id);

        if (dto.getEstoqueAtual() != null) teste.setEstoqueAtual(dto.getEstoqueAtual());
        if (dto.getEstoqueMinimo() != null) teste.setEstoqueMinimo(dto.getEstoqueMinimo());
        if (dto.getPreco() != null) teste.setPreco(dto.getPreco());
        if (dto.getLink() != null) teste.setLink(dto.getLink());

        return repository.save(teste);
    }



}
