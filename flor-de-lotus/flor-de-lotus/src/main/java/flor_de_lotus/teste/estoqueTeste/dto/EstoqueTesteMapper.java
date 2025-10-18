package flor_de_lotus.teste.estoqueTeste.dto;

import flor_de_lotus.teste.Teste;
import flor_de_lotus.teste.dto.TesteMapper;
import flor_de_lotus.teste.dto.TesteResponse;
import flor_de_lotus.teste.estoqueTeste.EstoqueTeste;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EstoqueTesteMapper {

    public static EstoqueTeste toEntity(EstoqueTesteRequest dto){
        if (dto == null){
            return null;
        }

        EstoqueTeste estoqueTeste = new EstoqueTeste();
        estoqueTeste.setQtdAtual(dto.getQtdAtual());
        LocalDate data = LocalDate.now();
        estoqueTeste.setDtReferencia(data);

        return estoqueTeste;
    }

    public static EstoqueTeste toEntity(EstoqueTesteResponse dto){
        if (dto == null){
            return null;
        }

        EstoqueTeste estoqueTeste = new EstoqueTeste();
        estoqueTeste.setQtdAtual(dto.getQtdAtual());
        LocalDate data = LocalDate.now();
        estoqueTeste.setDtReferencia(data);

        return estoqueTeste;
    }

    public static EstoqueTesteResponse toResponse(EstoqueTeste entiy){
        if (entiy == null){
            return null;
        }

        EstoqueTesteResponse response = new EstoqueTesteResponse();
        response.setIdEstoqueTeste(entiy.getIdEstoqueTeste());
        response.setQtdAtual(entiy.getQtdAtual());
        response.setDtReferencia(entiy.getDtReferencia());
        response.setFkTeste(entiy.getFkTeste());

        return response;
    }

    public static List<EstoqueTesteResponse> toResponseList(List<EstoqueTeste> lista){
        List<EstoqueTesteResponse> todosConsulta = new ArrayList<>();

        for (EstoqueTeste t: lista){
            EstoqueTesteResponse testeResponse = toResponse(t);
            todosConsulta.add(testeResponse);
        }

        return todosConsulta;
    }


}
