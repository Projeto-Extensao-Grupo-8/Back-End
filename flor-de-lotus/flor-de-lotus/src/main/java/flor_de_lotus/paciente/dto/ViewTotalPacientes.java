package flor_de_lotus.paciente.dto;

public class ViewTotalPacientes {
    private Integer totalPacientesAtivos;

    public ViewTotalPacientes(Integer totalPacientesAtivos) {
        this.totalPacientesAtivos = totalPacientesAtivos;
    }

    public Integer getTotalPacientesAtivos() {
        return totalPacientesAtivos;
    }
}
