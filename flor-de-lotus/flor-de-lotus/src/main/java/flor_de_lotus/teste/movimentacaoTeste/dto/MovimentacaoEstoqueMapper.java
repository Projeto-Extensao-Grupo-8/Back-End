package flor_de_lotus.teste.movimentacaoTeste.dto;

import flor_de_lotus.teste.movimentacaoTeste.MovimentacaoEstoque;

public class MovimentacaoEstoqueMapper {

    public static MovimentacaoEstoque toEntity (MovimentacaoEstoqueRequest body){
        if (body == null){
            return null;
        }

        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setQtd(body.getQtd());
        movimentacaoEstoque.setDescricao(body.getDescricao());

        return movimentacaoEstoque;
    }
}
