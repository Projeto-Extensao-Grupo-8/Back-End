package flor_de_lotus.consulta.dto.dashFinanceiro;

import lombok.Getter;

@Getter
public class GraficoConsultaMes {
    private Integer mes;
    private Long qtd;

    public GraficoConsultaMes(Integer mes, Long qtd) {
        this.mes = mes;
        this.qtd = qtd;
    }
}
