package flor_de_lotus.avaliacao.dto;

import java.math.BigDecimal;

public interface GraficoAvaliacaoPorConsulta {
    BigDecimal getCincoEstrelas();
    BigDecimal getQuatroEstrelas();
    BigDecimal getTresEstrelas();
    BigDecimal getDuasEstrelas();
    BigDecimal getUmaEstrela();
    BigDecimal getZeroEstrela();
}
