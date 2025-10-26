package flor_de_lotus.teste.movimentacaoTeste;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.consulta.ConsultaService;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.TesteService;
import flor_de_lotus.teste.movimentacaoTeste.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovimentacaoEstoqueService {
    private final MovimentacaoEstoqueRepository repository;
    private final TesteService testeService;
    private final ConsultaService consultaService;



    public MovimentacaoEstoqueResponse cadastrar(MovimentacaoEstoqueRequest dto){
        Teste teste = testeService.findByIdOrThrow(dto.getFkTeste());
        Consulta consulta = consultaService.buscarPorIdOuThrow(dto.getFkConsulta());
        MovimentacaoEstoque moviEstoque = MovimentacaoEstoqueMapper.toEntity(dto, teste, consulta);
        repository.save(moviEstoque);

        MovimentacaoEstoqueResponse moviEstoqueResponse = MovimentacaoEstoqueMapper.toResponse(moviEstoque);

        return moviEstoqueResponse;

    }

    public List<MovimentacaoEstoqueResponse> listar(){
        List<MovimentacaoEstoque> listaTodos = repository.findAll();
        List<MovimentacaoEstoqueResponse> moviEstoqueResponse = MovimentacaoEstoqueMapper.toResponseList(listaTodos);

        return moviEstoqueResponse;
    }

    public MovimentacaoEstoque buscarPorIdOuThrow(Integer id) {
        Optional<MovimentacaoEstoque> MoviEstoqueOpt = repository.findById(id);
        if (MoviEstoqueOpt.isEmpty()) {
            throw new EntidadeNaoEncontradoException("Paciente não encontrado.");
        }
        return MoviEstoqueOpt.get();
    }


    public List<MoviEstoqueResponseGet> listarPorPaciente(Integer idPaciente) {
        List<Consulta> listaConsulta = consultaService.listarPorPaciente(idPaciente);
        List<MoviEstoqueResponseGet> movimentacoes = new ArrayList<>();

        for (Consulta c:listaConsulta){
            List<MovimentacaoEstoque> moviConsult = repository.findAllByConsultaIdConsulta(c.getIdConsulta());
            List<MoviEstoqueResponseGet> listaResponseGet = MovimentacaoEstoqueMapper.toResponseListGet(moviConsult);

            movimentacoes.addAll(listaResponseGet);
        }

        return movimentacoes;
    }

    public List<MoviEstoqueResponseGetFunc> listarPorFuncionario(Integer idFuncionario) {
        List<Consulta> listaConsulta = consultaService.listarPorFuncionario(idFuncionario);
        List<MoviEstoqueResponseGetFunc> movimentacoes = new ArrayList<>();

        for (Consulta c:listaConsulta){
            List<MovimentacaoEstoque> moviConsult = repository.findAllByConsultaIdConsulta(c.getIdConsulta());
            List<MoviEstoqueResponseGetFunc> listaResponseGet = MovimentacaoEstoqueMapper.toResponseListGetFunc(moviConsult);

            movimentacoes.addAll(listaResponseGet);
        }

        return movimentacoes;
    }

    public MovimentacaoEstoqueResponse atualizarParcial(Integer idMovi, MovimentacaoEstoqueRequest dto) {
        MovimentacaoEstoque movimentacaoEstoque = buscarPorIdOuThrow(idMovi);
        if (dto.getFkTeste() != null){
            Teste fkTeste = testeService.findByIdOrThrow(dto.getFkTeste());
            movimentacaoEstoque.setTeste(fkTeste);
        }

        if (dto.getFkConsulta() != null){
            Consulta fkConsulta = consultaService.buscarPorIdOuThrow(dto.getFkConsulta());
            movimentacaoEstoque.setConsulta(fkConsulta);
        }

        if(dto.getQtd() != null) movimentacaoEstoque.setQtd(dto.getQtd());
        if(dto.getDescricao() != null) movimentacaoEstoque.setDescricao(dto.getDescricao());

        repository.save(movimentacaoEstoque);

        return MovimentacaoEstoqueMapper.toResponse(movimentacaoEstoque);

    }
}
