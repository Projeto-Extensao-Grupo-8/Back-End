package flor_de_lotus.avaliacao;

import flor_de_lotus.avaliacao.dto.AvaliacaoMapper;
import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.consulta.ConsultaService;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository repository;
    private final ConsultaService consultaService;

    public Avaliacao cadastrar(Avaliacao entity, Integer idConsulta){
        Consulta consulta = consultaService.buscarPorIdOuThrow(idConsulta);
        if (repository.existsById(idConsulta)){
            throw new EntidadeConflitoException("Conflito no campo consulta");
        }

        entity.setFkConsulta(consulta);

        return repository.save(entity);
    }

    public List<Avaliacao> listarTodos(){
        return repository.findAll();
    }

    public Avaliacao buscarPorIdOuThrow(Integer id){
        Optional<Avaliacao> avalEncontrada = repository.findById(id);
        if (avalEncontrada.isPresent()){
            return avalEncontrada.get();
        }

        throw new EntidadeNaoEncontradoException("Avaliacao não encontrada");
    }

}
