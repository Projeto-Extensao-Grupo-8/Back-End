package flor_de_lotus.teste.movimentacaoTeste.dto;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.movimentacaoTeste.MovimentacaoEstoque;

import java.util.ArrayList;
import java.util.List;



public class MovimentacaoEstoqueMapper {

    public static MovimentacaoEstoque toEntity (MovimentacaoEstoqueRequest dto, Teste teste, Consulta consulta){
        if (dto == null){
            return null;
        }

        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setQtd(dto.getQtd());
        movimentacaoEstoque.setDescricao(dto.getDescricao());
        movimentacaoEstoque.setDataMovimentacao(consulta.getDataConsulta());
        movimentacaoEstoque.setConsulta(consulta);
        movimentacaoEstoque.setTeste(teste);

        return movimentacaoEstoque;
    }

    public static MovimentacaoEstoqueResponse toResponse (MovimentacaoEstoque dto){
        if (dto == null){
            return null;
        }

        MovimentacaoEstoqueResponse moviEstoResponse = new MovimentacaoEstoqueResponse();
        moviEstoResponse.setIdMovimentacaoEstoque(dto.getIdMovimentacaoEstoque());
        moviEstoResponse.setQtd(dto.getQtd());
        moviEstoResponse.setDataMovimentacao(dto.getDataMovimentacao());
        moviEstoResponse.setDescricao(dto.getDescricao());
        moviEstoResponse.setFkTeste(dto.getTeste());
        moviEstoResponse.setFkConsulta(dto.getConsulta());

        return moviEstoResponse;
    }

    public static List<MovimentacaoEstoqueResponse> toResponseList(List<MovimentacaoEstoque> dto) {
        if (dto == null){
            return null;
        }

        List<MovimentacaoEstoqueResponse> listaResponse = new ArrayList<>();
        for (MovimentacaoEstoque i: dto){
            listaResponse.add(toResponse(i));
        }

        return listaResponse;
    }

    public static List<MoviEstoqueResponseGet> toResponseListGet(List<MovimentacaoEstoque> dto){
        if (dto == null){
            return null;
        }

        List<MoviEstoqueResponseGet> listaGetResponse = new ArrayList<>();
        for (MovimentacaoEstoque m: dto){
            listaGetResponse.add(toResponseGet(m));
        }

        return listaGetResponse;

    }


    public static MoviEstoqueResponseGet toResponseGet(MovimentacaoEstoque dto){
        if (dto == null){
            return null;
        }

        MoviEstoqueResponseGet moviReponseGet = new MoviEstoqueResponseGet();
        moviReponseGet.setIdMovimentacaoEstoque(dto.getIdMovimentacaoEstoque());
        moviReponseGet.setDataMovimentacao(dto.getDataMovimentacao());
        moviReponseGet.setQtd(dto.getQtd());
        moviReponseGet.setDescricao(dto.getDescricao());
        moviReponseGet.setNomeTeste(dto.getTeste().getNome());
        moviReponseGet.setFkConsulta(dto.getConsulta());

        return moviReponseGet;

    }

    public static MoviEstoqueResponseGetFunc toResponseGetFunc(MovimentacaoEstoque dto){
        if (dto == null){
            return null;
        }

        MoviEstoqueResponseGetFunc moviReponseGet = new MoviEstoqueResponseGetFunc();
        moviReponseGet.setIdMovimentacaoEstoque(dto.getIdMovimentacaoEstoque());
        moviReponseGet.setDataMovimentacao(dto.getDataMovimentacao());
        moviReponseGet.setQtd(dto.getQtd());
        moviReponseGet.setDescricao(dto.getDescricao());
        moviReponseGet.setNomeTeste(dto.getTeste().getNome());
        moviReponseGet.setNomePaciente(dto.getConsulta().getFkPaciente().getNome());
        moviReponseGet.setFkConsulta(dto.getConsulta());

        return moviReponseGet;

    }

    public static List<MoviEstoqueResponseGetFunc> toResponseListGetFunc(List<MovimentacaoEstoque> dto) {
        if (dto == null){
            return null;
        }

        List<MoviEstoqueResponseGetFunc> listaFunc = new ArrayList<>();
        for (MovimentacaoEstoque e:dto){
            listaFunc.add(toResponseGetFunc(e));
        }

        return listaFunc;
    }
}
