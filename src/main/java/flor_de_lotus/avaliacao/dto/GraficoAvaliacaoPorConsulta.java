package flor_de_lotus.avaliacao.dto;

import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class GraficoAvaliacaoPorConsulta {
    private BigDecimal cincoEstrelas;
    private BigDecimal quatroEstrelas;
    private BigDecimal tresEstrelas;
    private BigDecimal duasEstrelas;
    private BigDecimal umaEstrela;
    private BigDecimal zeroEstrela;

    public GraficoAvaliacaoPorConsulta(BigDecimal cincoEstrelas, BigDecimal quatroEstrelas, BigDecimal tresEstrelas, BigDecimal duasEstrelas, BigDecimal umaEstrela, BigDecimal zeroEstrela) {
        this.cincoEstrelas = cincoEstrelas;
        this.quatroEstrelas = quatroEstrelas;
        this.tresEstrelas = tresEstrelas;
        this.duasEstrelas = duasEstrelas;
        this.umaEstrela = umaEstrela;
        this.zeroEstrela = zeroEstrela;
    }
}
