package flor_de_lotus.consulta.dto.dashAgendamento;

import lombok.Getter;

@Getter
public class KpiConsultasRealizadas {
    private Long qtd;

    public KpiConsultasRealizadas(Long qtd) {
        this.qtd = qtd;
    }
}
