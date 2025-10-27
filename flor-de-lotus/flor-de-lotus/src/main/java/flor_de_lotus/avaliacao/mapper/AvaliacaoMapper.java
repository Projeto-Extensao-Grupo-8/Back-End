package flor_de_lotus.avaliacao.mapper;

import flor_de_lotus.avaliacao.Avaliacao;
import flor_de_lotus.avaliacao.dto.AvaliacaoListResponse;
import flor_de_lotus.avaliacao.dto.AvaliacaoRequest;
import flor_de_lotus.consulta.Consulta;

import java.util.List;

public class AvaliacaoMapper {
        public static Avaliacao toEntity(AvaliacaoRequest dto, Consulta entity) {
            if(dto == null) {
                return null;
            }

            return new Avaliacao(null, dto.getDescricao(), dto.getEstrelas(), entity);
        }

        public static AvaliacaoListResponse toListagemDto(Avaliacao entity) {
            if (entity == null) {
                return null;
            }

            return new AvaliacaoListResponse(
                    entity.getDescricao(),
                    entity.getEstrelas(),
                    entity.getFkConsulta());
        }

        public static List<AvaliacaoListResponse> toListagemDtos(List<Avaliacao> entities) {
            if (entities == null) {
                return null;
            }

            return entities.stream()
                    .map(flor_de_lotus.avaliacao.mapper.AvaliacaoMapper::toListagemDto)
                    .toList();
        }
}
