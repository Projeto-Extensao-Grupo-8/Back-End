package flor_de_lotus.avaliacao.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GraficoAvaliacaoPorFuncionario {
    private String nome;
    private BigDecimal mediaEstrelas;

    public GraficoAvaliacaoPorFuncionario(String nome, BigDecimal mediaEstrelas) {
        this.nome = nome;
        this.mediaEstrelas = mediaEstrelas;
    }
}
