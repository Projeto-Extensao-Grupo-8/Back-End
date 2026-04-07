package flor_de_lotus.endereco.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
public class EnderecoResponse {
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;

}
