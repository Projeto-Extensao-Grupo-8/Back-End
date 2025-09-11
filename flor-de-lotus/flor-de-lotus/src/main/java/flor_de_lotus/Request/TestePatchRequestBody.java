package flor_de_lotus.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TestePatchRequestBody {
    private Double preco;
    private Integer estoqueMinimo;
    private Integer estoqueAtual;
    private String link;
}
