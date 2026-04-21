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

    public Integer buscarQtdValidadeProxima90Dias() {
        LocalDate hoje = LocalDate.now();
        LocalDate daqui90Dias = hoje.plusDays(90);

        return repository.countByValidadeBetween(hoje, daqui90Dias);
    }

    public Integer buscarQtdEstoqueCritico() {
        return repository.countTestesEstoqueCritico();
    }

    public Integer buscarTotalUnidadesFisicas() {
        return repository.sumUnidadesFisicas();
    }

    public Integer buscarTotalUnidadesDigitais() {
        return repository.sumUnidadesDigitais();
    }



    public Double buscarValorTotalEstoque() {
        return repository.sumValorTotalEstoque();
    }

    public Integer buscarTotalUnidadesAoTodo() {
        return repository.sumTotalUnidades();
    }

    public Long buscarTotalTiposTestes() {
        return repository.count();
    }

    public List<Teste> buscarAlertasDeEstoque() {
        List<Teste> testesCriticos = repository.findByStatusEstoqueOrderByQtdAsc(StatusEstoque.CRITICO);
        return testesCriticos;
    }

    public Teste atualizarParcial(Integer id, flor_de_lotus.teste.dto.TestePatchRequest dto) {
        Teste teste = findByIdOrThrow(id);
        if (dto.getCodigo() != null) teste.setCodigo(dto.getCodigo());
        if (dto.getNome() != null) teste.setNome(dto.getNome());
        if (dto.getCategoria() != null) teste.setCategoria(dto.getCategoria());
        if (dto.getSubCategoria() != null) teste.setSubCategoria(dto.getSubCategoria());
        if (dto.getEditora() != null) teste.setEditora(dto.getEditora());
        if (dto.getTipo() != null) teste.setTipo(dto.getTipo());
        if (dto.getPreco() != null) teste.setPreco(dto.getPreco());
        if (dto.getEstoqueMinimo() != null) teste.setEstoqueMinimo(dto.getEstoqueMinimo());
        if (dto.getValidade() != null) teste.setValidade(dto.getValidade());
        if (dto.getQtd() != null) teste.setQtd(dto.getQtd());
        return repository.save(teste);
    }

}
