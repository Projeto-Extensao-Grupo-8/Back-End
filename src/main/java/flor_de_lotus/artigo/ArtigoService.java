package flor_de_lotus.artigo;

import flor_de_lotus.artigo.dto.ArtigoMapper;
import flor_de_lotus.artigo.dto.ArtigoResponse;
import flor_de_lotus.artigo.Event.ArtigoCreatedEvent;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ArtigoService {

    private final ArtigoRepository artigoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ApplicationEventPublisher publisher;

    public List<Artigo> pesquisar(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return artigoRepository.findAll();
        }
        return artigoRepository.pesquisar(termo);
    }

    @Transactional
    public Artigo cadastrar(Artigo entity, Integer idFunc) {
        Funcionario autor = funcionarioRepository.findById(idFunc)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        entity.setFkFuncionario(autor);

        Artigo salvo = artigoRepository.save(entity);

        publisher.publishEvent(new ArtigoCreatedEvent(salvo));

        return salvo;
    }

    public List<Artigo> listarTodos() {
        return artigoRepository.findAll();
    }

    public Optional<Artigo> buscarPorId(Integer id) {
        return artigoRepository.findById(id);
    }

    public Optional<Artigo> atualizar(Integer id, Artigo entity, Integer idFunc) {
        Funcionario autor = funcionarioRepository.findById(idFunc)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        return artigoRepository.findById(id)
                .map(existing -> {
                    existing.setTitulo(entity.getTitulo());
                    existing.setDescricao(entity.getDescricao());
                    existing.setDtPublicacao(entity.getDtPublicacao());
                    existing.setFkFuncionario(autor);

                    Artigo salvo = artigoRepository.save(existing);
                    return salvo;
                });
    }

    public void deletar(Integer id) {
        artigoRepository.deleteById(id);
    }
}
