package flor_de_lotus.paciente.dto;

import flor_de_lotus.paciente.Paciente;
import flor_de_lotus.usuario.Usuario;

import java.util.List;

public class PacienteMapper {

    public static Paciente toEntity(Usuario usuario) {
        Paciente paciente = new Paciente();
        paciente.setFkUsuario(usuario);
        paciente.setAtivo(true);
        return paciente;
    }

    public static Paciente toEntity(PacientePostRequestBody dto) {
        Paciente paciente = new Paciente();
        paciente.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        return paciente;
    }

    public static PacienteResponseBody toResponse(Paciente paciente) {
        PacienteResponseBody response = new PacienteResponseBody();
        response.setIdPaciente(paciente.getIdPaciente());
        response.setAtivo(paciente.isAtivo());
        if (paciente.getFkUsuario() != null) {
            response.setIdUsuario(paciente.getFkUsuario().getIdUsuario());
            response.setNomeUsuario(paciente.getFkUsuario().getNome());
        }
        return response;
    }

    public static List<PacienteResponseBody> toResponseList(List<Paciente> pacientes) {
        if (pacientes == null) {
            return null;
        }
        return pacientes.stream()
                .map(PacienteMapper::toResponse)
                .toList();
    }
}
