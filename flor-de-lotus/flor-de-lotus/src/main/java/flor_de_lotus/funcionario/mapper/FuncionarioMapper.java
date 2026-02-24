package flor_de_lotus.funcionario.mapper;

import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.dto.FuncionarioPostRequestBody;
import flor_de_lotus.funcionario.dto.FuncionarioListResponse;
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
                dto.getEspecialidade(),
                LocalDate.now(),
                true,
                entity
                );
    }

    public static FuncionarioResponse toResponse(Funcionario entity) {
        if (entity == null) {
            return null;
        }

        return new FuncionarioResponse(
                entity.getIdFuncionario(),
                entity.getCrp(),
                entity.getEspecialidade(),
                entity.getDtAdmissao(),
                entity.isAtivo(),
                entity.getIdUsuario(),
                entity.getNome()
        );
    }

    public static FuncionarioListResponse toListagemDto(Funcionario entity) {
        if (entity == null) {
            return null;
        }

        return new FuncionarioListResponse(
                entity.getIdFuncionario(),
                entity.getNome(),
                entity.getCrp(),
                entity.getEspecialidade(),
                entity.getDtAdmissao(),
                entity.isAtivo()
            );

    }

    public static List<FuncionarioListResponse> toListagemDtos(List<Funcionario> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(FuncionarioMapper::toListagemDto)
                .toList();
    }

    public static List<FuncionarioResponse> toResponseList(List<Funcionario> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(FuncionarioMapper::toResponse)
                .toList();
    }
}
