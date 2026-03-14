package flor_de_lotus.consulta.dto.dashAgendamento;

import lombok.Getter;

@Getter
public class KpiCancelamentos {
    private Long qtdCanceladas;
    private String percentual;

    public KpiCancelamentos(Long qtdCanceladas, String percentual) {
        this.qtdCanceladas = qtdCanceladas;
        this.percentual = percentual;
    }
}
