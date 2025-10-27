package flor_de_lotus.artigo;

import flor_de_lotus.artigo.dto.ArtigoMapper;
import flor_de_lotus.artigo.dto.ArtigoPostRequest;
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

    public List<ArtigoResponse> pesquisar(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return ArtigoMapper.toResponseList(artigoRepository.findAll());
        }
        return ArtigoMapper.toResponseList(artigoRepository.pesquisar(termo));
    }

    @Transactional
    public ArtigoResponse cadastrar(ArtigoPostRequest dto) {
        Funcionario autor = funcionarioRepository.findById(dto.getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Artigo artigo = ArtigoMapper.toEntity(dto);
        artigo.setFkFuncionario(autor);

        Artigo salvo = artigoRepository.save(artigo);

        publisher.publishEvent(new ArtigoCreatedEvent(salvo));

        return ArtigoMapper.toResponse(salvo);
    }

    public List<ArtigoResponse> listarTodos() {
        return ArtigoMapper.toResponseList(artigoRepository.findAll());
    }

    public Optional<ArtigoResponse> buscarPorId(Integer id) {
        return artigoRepository.findById(id).map(ArtigoMapper::toResponse);
    }

    public Optional<ArtigoResponse> atualizar(Integer id, ArtigoPostRequest dto) {
        Funcionario autor = funcionarioRepository.findById(dto.getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        return artigoRepository.findById(id)
                .map(existing -> {
                    existing.setTitulo(dto.getTitulo());
                    existing.setDescricao(dto.getDescricao());
                    existing.setDtPublicacao(dto.getDtPublicacao());
                    existing.setFkFuncionario(autor);

                    Artigo salvo = artigoRepository.save(existing);
                    return ArtigoMapper.toResponse(salvo);
                });
    }

    public void deletar(Integer id) {
        artigoRepository.deleteById(id);
    }
}
