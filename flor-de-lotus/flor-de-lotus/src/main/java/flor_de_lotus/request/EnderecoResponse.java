package flor_de_lotus.request;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class EnderecoResponse {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String estado;

}
