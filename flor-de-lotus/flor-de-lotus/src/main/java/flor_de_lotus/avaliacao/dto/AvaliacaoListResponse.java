package flor_de_lotus.avaliacao.dto;

import flor_de_lotus.consulta.Consulta;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvaliacaoListResponse {
    private String descricao;
    private Integer estrelas;
    private Consulta fkConsulta;
}
