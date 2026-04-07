package flor_de_lotus.consulta.dto.dashAgendamento;

import lombok.Getter;

@Getter
public class GraficoDesempenhoSemanal {
    private String status;
    private Long quantidade;

    public GraficoDesempenhoSemanal(String status, Number quantidade) {
            this.status = status;
            this.quantidade = quantidade != null ? quantidade.longValue() : 0L;
    }

}
