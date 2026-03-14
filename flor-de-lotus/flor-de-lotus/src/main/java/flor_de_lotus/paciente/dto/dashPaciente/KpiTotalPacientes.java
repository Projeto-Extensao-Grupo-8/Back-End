package flor_de_lotus.paciente.dto.dashPaciente;

public class KpiTotalPacientes {
    private Long totalPacientesAtivos;

    public KpiTotalPacientes(Long totalPacientesAtivos) {
        this.totalPacientesAtivos = totalPacientesAtivos;
    }

    public Long getTotalPacientesAtivos() {
        return totalPacientesAtivos;
    }
}
