package flor_de_lotus.mapper;

import flor_de_lotus.entity.Funcionario;
import flor_de_lotus.request.FuncionarioListResponse;
import flor_de_lotus.request.FuncionarioPostRequestBody;

import java.time.LocalDate;
import java.util.List;

public class FuncionarioMapper {

    public static Funcionario toEntity(FuncionarioPostRequestBody dto) {
        if(dto == null) {
            return null;
        }

        return new Funcionario(
                null,
                dto.getCrp(),
                dto.getEspecialidade(),
                LocalDate.now(),
                true,
                dto.getFkUsuario()
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
