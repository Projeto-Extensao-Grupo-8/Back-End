package flor_de_lotus.teste.estoqueTeste.dto;

import flor_de_lotus.teste.estoqueTeste.EstoqueTeste;

public class EstoqueTesteMapper {

    public static EstoqueTeste toEntity(EstoqueTesteRequest dto){
        if (dto == null){
            return null;
        }

        EstoqueTeste estoqueTeste = new EstoqueTeste();
        estoqueTeste.setQtdAtual(dto.getQtdAtual());
        estoqueTeste.setDtReferencia(dto.getDtReferencia());

        return estoqueTeste;
    }

    public static EstoqueTesteResponse toResponse(EstoqueTeste entiy){
        if (entiy == null){
            return null;
        }

        EstoqueTesteResponse response = new EstoqueTesteResponse();
        response.setQtdAtual(entiy.getQtdAtual());
        response.setDtReferencia(entiy.getDtReferencia());
        response.setFkTeste(entiy.getFkTeste());

        return response;

    }


}
