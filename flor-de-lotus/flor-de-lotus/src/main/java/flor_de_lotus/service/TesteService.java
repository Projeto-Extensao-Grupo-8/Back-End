package flor_de_lotus.service;

import flor_de_lotus.entity.Teste;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.repository.TesteRepository;
import flor_de_lotus.request.TestePatchRequestBody;
import flor_de_lotus.request.TestePostRequestBody;
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
        return repository.findByCategoria(categoria);
    }

    public List<Teste> listarPorTipo(String tipo){

        return repository.findByCategoria(tipo);
    }

    public Teste buscarPorValidade(){
      Optional<Teste> testeOpt = repository.findTop1ByValidadeAfterOrderByValidadeAsc(LocalDate.now());
        if (testeOpt.isEmpty()){
            throw new EntidadeNaoEncontradoException("Não foi encontrado");
        }

        return testeOpt.get();
    }

    public Teste buscarPorQtd(){
        Optional<Teste> testeOpt =  repository.findTop1ByOrderByEstoqueAtualAsc();
        if (testeOpt.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Teste não encontrado");
        }
        return testeOpt.get();
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
