package flor_de_lotus.funcionario.mapper;

import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.dto.FuncionarioCardResponse;
import flor_de_lotus.funcionario.dto.FuncionarioPostRequestBody;
import flor_de_lotus.funcionario.dto.FuncionarioResponse;
import flor_de_lotus.usuario.Usuario;

import java.time.LocalDate;
import java.util.List;

public class FuncionarioMapper {
    public static Funcionario toEntity(FuncionarioPostRequestBody dto, Usuario entity) {
        if(dto == null) {
            return null;
        }

        return new Funcionario(
                null,
                dto.getCrp(),
                LocalDate.now(),
                dto.getDescricao(),
                dto.getFormacaoAcademica(),
                dto.getIdiomasAtendidos(),
                dto.getModalidade(),
                true,
                entity,
                dto.getTiposAtendimento(),
                dto.getEspecialidades()
        );
    }

    public static Funcionario toEntity(FuncionarioPostRequestBody dto) {
        if(dto == null) {
            return null;
        }

        return new Funcionario(
                null,
                dto.getCrp(),
                LocalDate.now(),
                dto.getDescricao(),
                dto.getFormacaoAcademica(),
                dto.getIdiomasAtendidos(),
                dto.getModalidade(),
                true,
                null,
                dto.getTiposAtendimento(),
                dto.getEspecialidades()
        );
    }

    public static FuncionarioResponse toResponse(Funcionario entity) {
        if (entity == null) {
            return null;
        }

        return new FuncionarioResponse(
                entity.getIdFuncionario(),
                entity.getCrp(),
                entity.getDtAdmissao(),
                entity.isAtivo(),
                entity.getIdUsuario(),
                entity.getNome(),
                entity.getFkUsuario().getEmail(),
                entity.getTiposAtendimento(),
                entity.getEspecialidades(),
                entity.getDescricao(),
                entity.getFormacaoAcademica(),
                entity.getIdiomasAtendidos(),
                entity.getModalidade()
        );
    }
    
    public static FuncionarioCardResponse toCardResponse(Funcionario entity) {
        if (entity == null) {
            return null;
        }

        return new FuncionarioCardResponse(
                entity.getNome(),
                entity.getEspecialidades(),
                entity.getModalidade()
        );
    }

    public static List<FuncionarioResponse> toResponseList(List<Funcionario> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(FuncionarioMapper::toResponse)
                .toList();
    }
    
    public static List<FuncionarioCardResponse> toCardResponseList(List<Funcionario> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(FuncionarioMapper::toCardResponse)
                .toList();
    }
}
