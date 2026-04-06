package flor_de_lotus.avaliacao;

import flor_de_lotus.avaliacao.dto.AvaliacaoMapper;
import flor_de_lotus.avaliacao.dto.AvaliacaoRequest;
import flor_de_lotus.avaliacao.dto.GraficoAvaliacaoPorConsulta;
import flor_de_lotus.avaliacao.dto.GraficoAvaliacaoPorFuncionario;
import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.consulta.ConsultaService;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository repository;
    private final ConsultaService consultaService;
    private final FuncionarioService funcionarioService;

    public Avaliacao cadastrar(AvaliacaoRequest request){
        Consulta consulta = null;
        Funcionario funcionario = null;

        if (request.getFkConsulta() != null && request.getFkFuncionario() != null) {
            throw new EntidadeConflitoException("Avaliação não pode ser para consulta e funcionário ao mesmo tempo");
        }

        if (request.getFkConsulta() == null && request.getFkFuncionario() == null) {
            throw new EntidadeConflitoException("Avaliação deve ser para consulta ou funcionário");
        }

        if (request.getFkConsulta() != null) {
            consulta = consultaService.buscarPorIdOuThrow(request.getFkConsulta());
            if (repository.existsByFkConsulta_IdConsulta(request.getFkConsulta())) {
                throw new EntidadeConflitoException("Já existe avaliação para esta consulta");
            }
        }

        if (request.getFkFuncionario() != null) {
            funcionario = funcionarioService.buscarPorIdOuThrow(request.getFkFuncionario());
        }

        Avaliacao avaliacao = AvaliacaoMapper.toEntity(request, consulta, funcionario);
        avaliacao.setDataAvaliacao(LocalDateTime.now());

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

    public List<GraficoAvaliacaoPorConsulta> graficoAvaliacaoPorConsulta() {
        return repository.graficoAvaliacaoPorConsulta();
    }

    public List<GraficoAvaliacaoPorFuncionario> graficoAvaliacaoPorFuncionario() {
        return repository.graficoAvaliacaoPorFuncionario();
    }
}
