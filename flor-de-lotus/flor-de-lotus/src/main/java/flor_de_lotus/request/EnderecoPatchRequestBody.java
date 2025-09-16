package flor_de_lotus.request;

import lombok.Getter;

@Getter
public class EnderecoPatchRequestBody {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
