package flor_de_lotus.avaliacao.dto;

import flor_de_lotus.avaliacao.Avaliacao;
import flor_de_lotus.consulta.Consulta;
import flor_de_lotus.funcionario.Funcionario;

import java.time.LocalDateTime;
import java.util.List;

public class AvaliacaoMapper {
        public static Avaliacao toEntity(AvaliacaoRequest dto, Consulta consulta, Funcionario funcionario) {
            if(dto == null) {
                return null;
            }

            return new Avaliacao(null, dto.getDescricao(), dto.getEstrelas(), null, consulta, funcionario);
        }

        public static Avaliacao toEntity(AvaliacaoRequest dto) {
            if(dto == null) {
                return null;
            }

            return new Avaliacao(null, dto.getDescricao(), dto.getEstrelas(), null, null, null);
        }

        public static AvaliacaoResponse toResponse(Avaliacao entity) {
            if (entity == null) {
                return null;
            }

            String descricao = entity.getDescricao();
            Integer estrelas = entity.getEstrelas();
            LocalDateTime dataAvaliacao = entity.getDataAvaliacao();

            Integer idFuncionario = null;
            String nomeFuncionario = null;
            String emailFuncionario = null;
            Integer idConsulta = null;
            LocalDateTime dataConsulta = null;

            if (entity.getFkConsulta() != null) {
                Consulta consulta = entity.getFkConsulta();
                idConsulta = consulta.getIdConsulta();
                dataConsulta = consulta.getData();
                if (consulta.getFkFuncionario() != null) {
                    Funcionario func = consulta.getFkFuncionario();
                    idFuncionario = func.getIdFuncionario();
                    nomeFuncionario = func.getNome();
                    emailFuncionario = func.getFkUsuario() != null ? func.getFkUsuario().getEmail() : null;
                }
            } else if (entity.getFkFuncionario() != null) {
                Funcionario func = entity.getFkFuncionario();
                idFuncionario = func.getIdFuncionario();
                nomeFuncionario = func.getNome();
                emailFuncionario = func.getFkUsuario() != null ? func.getFkUsuario().getEmail() : null;
            }

            return new AvaliacaoResponse(descricao, estrelas, dataAvaliacao, idFuncionario, nomeFuncionario, emailFuncionario, idConsulta, dataConsulta);
        }

        public static List<AvaliacaoResponse> toResponseList(List<Avaliacao> entities) {
            if (entities == null) {
                return null;
            }

            return entities.stream()
                    .map(AvaliacaoMapper::toResponse)
                    .toList();
        }
}
