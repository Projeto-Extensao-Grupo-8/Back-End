package flor_de_lotus.avaliacao.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GraficoAvaliacaoPorFuncionario {
    private String nome;
    private BigDecimal cincoEstrelas;
    private BigDecimal quatroEstrelas;
    private BigDecimal tresEstrelas;
    private BigDecimal duasEstrelas;
    private BigDecimal umaEstrela;
    private BigDecimal zeroEstrelas;

    public GraficoAvaliacaoPorFuncionario(String nome, BigDecimal cincoEstrelas, BigDecimal quatroEstrelas, BigDecimal tresEstrelas, BigDecimal duasEstrelas, BigDecimal umaEstrela, BigDecimal zeroEstrelas) {
        this.nome = nome;
        this.cincoEstrelas = cincoEstrelas;
        this.quatroEstrelas = quatroEstrelas;
        this.tresEstrelas = tresEstrelas;
        this.duasEstrelas = duasEstrelas;
        this.umaEstrela = umaEstrela;
        this.zeroEstrelas = zeroEstrelas;
    }
}
