package flor_de_lotus.paciente.dto;

public class ViewTotalPacientes {
    private Long totalPacientesAtivos;

    public ViewTotalPacientes(Long totalPacientesAtivos) {
        this.totalPacientesAtivos = totalPacientesAtivos;
    }

    public Long getTotalPacientesAtivos() {
        return totalPacientesAtivos;
    }
}
