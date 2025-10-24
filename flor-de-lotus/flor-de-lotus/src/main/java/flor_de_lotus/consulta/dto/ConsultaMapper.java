package flor_de_lotus.consulta.dto;

import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.paciente.Paciente;

public class ConsultaMapper {

    public static Consulta of(ConsultaPostRequestBody dto, Funcionario funcionario, Paciente paciente) {
        Consulta entity = new Consulta();

        entity.setDataConsulta(dto.getDataConsulta());
        entity.setValorConsulta(dto.getValorConsulta());
        entity.setEspecialidade(dto.getEspecialidade());
        entity.setFkFuncionario(funcionario);
        entity.setFkPaciente(paciente);

        return entity;
    }

    public static ConsultaResponseBody of(Consulta consulta) {
        ConsultaResponseBody response = new ConsultaResponseBody();

        response.setIdConsulta(consulta.getIdConsulta());
        response.setDataConsulta(consulta.getDataConsulta());
        response.setValorConsulta(consulta.getValorConsulta());
        response.setEspecialidade(consulta.getEspecialidade());
        response.setNomeFuncionario(
                consulta.getFkFuncionario() != null ? consulta.getFkFuncionario().getNome() : null
        );
        response.setNomePaciente(
                consulta.getFkPaciente() != null ? consulta.getFkPaciente().getNome() : null
        );

        return response;
    }
}
