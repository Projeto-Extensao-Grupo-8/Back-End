package flor_de_lotus.avaliacao.dto;

import lombok.Data;

@Data
public class AvaliacaoRequest {
    private String descricao;
    private Integer estrelas;
    private Integer fkConsulta;
}
