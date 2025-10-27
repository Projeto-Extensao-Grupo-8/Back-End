package flor_de_lotus.avaliacao;

import flor_de_lotus.avaliacao.dto.AvaliacaoRequest;
import flor_de_lotus.avaliacao.mapper.AvaliacaoMapper;
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

    public Avaliacao cadastrar(AvaliacaoRequest dto){
        Consulta consulta = consultaService.buscarPorIdOuThrow(dto.getFkConsulta());
        if (repository.existsById(dto.getFkConsulta())){
            throw new EntidadeConflitoException("Conflito no campo consulta");
        }

        Avaliacao avaliacao = AvaliacaoMapper.toEntity(dto, consulta);

        return repository.save(avaliacao);
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
