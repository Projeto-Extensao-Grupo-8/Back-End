package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.consulta.ConsultaService;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.paciente.PacienteService;
import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.TesteRepository;
import flor_de_lotus.teste.TesteService;
import flor_de_lotus.teste.movimentacaoTeste.dto.UltimoTestePacienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class    MovimentacaoEstoqueService {
    private final MovimentacaoEstoqueRepository repository;
    private final TesteService testeService;
    private final ConsultaService consultaService;
    private final PacienteService pacienteService;
    private final TesteRepository testeRepository;

    public MovimentacaoEstoque cadastrar(MovimentacaoEstoque entity, Integer idTeste, Integer idConsulta) {
        Teste teste = testeService.findByIdOrThrow(idTeste);
        Consulta consulta = consultaService.buscarPorIdOuThrow(idConsulta);

        int estoqueAtual = teste.getQtd() != null ? teste.getQtd() : 0;
        int qtdSolicitada = entity.getQtd() != null ? entity.getQtd() : 0;

        if (qtdSolicitada > estoqueAtual) {
            throw new EntidadeConflitoException(
                    "Estoque insuficiente para o teste: " + teste.getNome() +
                            ". Solicitado: " + qtdSolicitada + ", Disponível: " + estoqueAtual
            );
        }
        teste.setQtd(estoqueAtual - qtdSolicitada);
        testeRepository.save(teste);
        entity.setConsulta(consulta);
        entity.setTeste(teste);
        entity.setDataMovimentacao(consulta.getData());

        return repository.save(entity);
    }

    public List<MovimentacaoEstoque> listar(){
        return repository.findAll();
    }

    public MovimentacaoEstoque buscarPorIdOuThrow(Integer id) {
        Optional<MovimentacaoEstoque> MoviEstoqueOpt = repository.findById(id);
        if (MoviEstoqueOpt.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Paciente não encontrado.");
        }
        return MoviEstoqueOpt.get();
    }

    public List<MovimentacaoEstoque> listarPorPaciente(Integer idPaciente) {
        List<Consulta> listaConsulta = consultaService.listarConsultasPorPaciente(idPaciente);
        List<MovimentacaoEstoque> movimentacoes = new ArrayList<>();

        for (Consulta c:listaConsulta){
            List<MovimentacaoEstoque> moviConsult = repository.findAllByConsultaIdConsulta(c.getIdConsulta());
            movimentacoes.addAll(moviConsult);
        }

        return movimentacoes;

    }

    public List<MovimentacaoEstoque> listarPorFuncionario(Integer idFuncionario) {
        List<Consulta> listaConsulta = consultaService.listarConsultasPorFuncionario(idFuncionario);
        List<MovimentacaoEstoque> movimentacoes = new ArrayList<>();

        for (Consulta c:listaConsulta){
            List<MovimentacaoEstoque> moviConsult = repository.findAllByConsultaIdConsulta(c.getIdConsulta());

            movimentacoes.addAll(moviConsult);
        }

        return movimentacoes;
    }

    public MovimentacaoEstoque atualizarParcial(Integer idMovi, MovimentacaoEstoque entity, Integer idConsulta, Integer idTeste) {
        MovimentacaoEstoque movimentacaoEstoque = buscarPorIdOuThrow(idMovi);
        if (idTeste != null){
            Teste fkTeste = testeService.findByIdOrThrow(idTeste);
            movimentacaoEstoque.setTeste(fkTeste);
        }

        if (idConsulta != null){
            Consulta fkConsulta = consultaService.buscarPorIdOuThrow(idConsulta);
            movimentacaoEstoque.setConsulta(fkConsulta);
        }

        if(entity.getQtd() != null) movimentacaoEstoque.setQtd(entity.getQtd());
        if(entity.getDescricao() != null) movimentacaoEstoque.setDescricao(entity.getDescricao());

        return repository.save(movimentacaoEstoque);

    }

    public UltimoTestePacienteDTO buscarUltimoTeste(Integer idPaciente) {
        pacienteService.buscarPorIdOuThrow(idPaciente);
        Optional<MovimentacaoEstoque> ultimaMov = repository
                .findFirstByConsulta_FkPaciente_IdPacienteOrderByDataMovimentacaoDesc(idPaciente);

        if (ultimaMov.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Nenhum teste encontrado para o histórico deste paciente.");
        }

        MovimentacaoEstoque mov = ultimaMov.get();

        return new UltimoTestePacienteDTO(
                mov.getTeste().getCodigo(), mov.getTeste().getNome(), mov.getTeste().getCategoria(),
                mov.getDataMovimentacao(),
                mov.getQtd(), mov.getDescricao()
        );
    }



}