package flor_de_lotus.paciente.dto.dashPaciente;

public class KpiTotalPacientesPorAno {
    private Long qtd;

    public KpiTotalPacientesPorAno(Long totalPacientesAtivos) {
        this.qtd = totalPacientesAtivos;
    }

    public Long getQtd() {
        return qtd;
    }
}
