package flor_de_lotus.consulta.dto;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.paciente.Paciente;

import java.util.List;

public class ConsultaMapper {

    public static Consulta toEntity(ConsultaPostRequestBody dto, Funcionario funcionario, Paciente paciente) {
        Consulta entity = new Consulta();

        entity.setData(dto.getDataConsulta());
        entity.setValor(dto.getValorConsulta());
        entity.setEspecialidade(dto.getEspecialidade());
        entity.setStatus(dto.getStatus());
        entity.setTipo(dto.getTipo());
        entity.setFkFuncionario(funcionario);
        entity.setFkPaciente(paciente);

        return entity;
    }

    public static Consulta toEntity(ConsultaPostRequestBody dto) {
        Consulta entity = new Consulta();

        entity.setData(dto.getDataConsulta());
        entity.setValor(dto.getValorConsulta());
        entity.setEspecialidade(dto.getEspecialidade());
        entity.setStatus(dto.getStatus());
        entity.setTipo(dto.getTipo());
        entity.setFkFuncionario(null);
        entity.setFkPaciente(null);

        return entity;
    }

    public static ConsultaResponseBody toResponse(Consulta consulta) {
        ConsultaResponseBody response = new ConsultaResponseBody();

        response.setIdConsulta(consulta.getIdConsulta());
        response.setDataConsulta(consulta.getData());
        response.setValorConsulta(consulta.getValor());
        response.setEspecialidade(consulta.getEspecialidade());
        response.setTipo(consulta.getTipo());
        response.setStatus(consulta.getStatus());
        response.setNomeFuncionario(
                consulta.getFkFuncionario() != null ? consulta.getFkFuncionario().getNome() : null
        );
        response.setNomePaciente(
                consulta.getFkPaciente() != null ? consulta.getFkPaciente().getNome() : null
        );

        response.setIdFuncionario(
                consulta.getFkFuncionario() != null ? consulta.getFkFuncionario().getIdFuncionario() : null
        );

        response.setIdPaciente(consulta.getFkPaciente().getIdPaciente());
        return response;
    }

    public static List<ConsultaResponseBody> toResponseList(List<Consulta> consultas) {
        if (consultas == null) {
            return null;
        }
        return consultas.stream()
                .map(ConsultaMapper::toResponse)
                .toList();
    }
}
