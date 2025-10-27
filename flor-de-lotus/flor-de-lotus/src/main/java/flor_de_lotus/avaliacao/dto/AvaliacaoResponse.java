package flor_de_lotus.avaliacao.dto;

import flor_de_lotus.consulta.Consulta;
import lombok.Data;

@Data
public class AvaliacaoResponse {
    private String descricao;
    private Integer estrelas;
    private Consulta fkConsulta;
}
