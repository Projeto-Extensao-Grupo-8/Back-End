package flor_de_lotus.consulta.dto.dashAgendamento;

import lombok.Getter;

@Getter
public class GraficoDistribuicaoHorario {
    private Long quantidade;
    private String periodo;


    public GraficoDistribuicaoHorario(Number quantidade, String periodo) {
        this.quantidade = quantidade != null ? quantidade.longValue() : 0L;
        this.periodo = periodo;
    }
}
