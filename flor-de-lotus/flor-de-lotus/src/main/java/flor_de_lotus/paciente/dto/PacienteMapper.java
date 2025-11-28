package flor_de_lotus.paciente.dto;

import flor_de_lotus.paciente.Paciente;
import flor_de_lotus.usuario.Usuario;

public class PacienteMapper {

    public static Paciente of(PacientePostRequestBody dto, Usuario usuario) {
        Paciente paciente = new Paciente();
        paciente.setFkUsuario(usuario);
        paciente.setAtivo('S');
        return paciente;
    }

    public static Paciente of(PacientePostRequestBody dto) {
        Paciente paciente = new Paciente();
        paciente.setAtivo('S');
        return paciente;
    }

    public static PacienteResponseBody of(Paciente paciente) {
        PacienteResponseBody response = new PacienteResponseBody();
        response.setIdPaciente(paciente.getIdPaciente());
        response.setAtivo(paciente.getAtivo());
        if (paciente.getFkUsuario() != null) {
            response.setIdUsuario(paciente.getFkUsuario().getIdUsuario());
            response.setNomeUsuario(paciente.getFkUsuario().getNome());
        }
        return response;
    }
}
