package flor_de_lotus.paciente.dto.dashPaciente;

import lombok.Getter;

@Getter
public class GraficoNovosPacientesPorMes {
    private Integer mes;
    private Long qtd;

    public GraficoNovosPacientesPorMes(Integer mes, Long qtd) {
        this.mes = mes;
        this.qtd = qtd;
    }
}
