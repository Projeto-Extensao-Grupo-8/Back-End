package flor_de_lotus.funcionario.mapper;

import flor_de_lotus.funcionario.Funcionario;
import flor_de_lotus.funcionario.dto.FuncionarioPostRequestBody;
import flor_de_lotus.funcionario.dto.FuncionarioListResponse;
import flor_de_lotus.usuario.Usuario;
import flor_de_lotus.usuario.UsuarioService;

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

    public static FuncionarioListResponse toListagemDto(Funcionario entity) {
        if (entity == null) {
            return null;
        }

        return new FuncionarioListResponse(
                entity.getCrp(),
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
}
