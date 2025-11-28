package flor_de_lotus.avaliacao.dto;

import flor_de_lotus.avaliacao.Avaliacao;
import flor_de_lotus.consulta.Consulta;

import java.util.List;

public class AvaliacaoMapper {
        public static Avaliacao toEntity(AvaliacaoRequest dto, Consulta entity) {
            if(dto == null) {
                return null;
            }

            return new Avaliacao(null, dto.getDescricao(), dto.getEstrelas(), entity);
        }

        public static Avaliacao toEntity(AvaliacaoRequest dto) {
            if(dto == null) {
                return null;
            }

            return new Avaliacao(null, dto.getDescricao(), dto.getEstrelas(), null);
        }

        public static AvaliacaoResponse toDto(Avaliacao entity) {
            if (entity == null) {
                return null;
            }

            return new AvaliacaoResponse(
                    entity.getDescricao(),
                    entity.getEstrelas(),
                    entity.getFkConsulta());
        }

        public static List<AvaliacaoResponse> toDto(List<Avaliacao> entities) {
            if (entities == null) {
                return null;
            }

            return entities.stream()
                    .map(AvaliacaoMapper::toDto)
                    .toList();
        }
}
