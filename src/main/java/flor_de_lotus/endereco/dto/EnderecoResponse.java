package flor_de_lotus.endereco.dto;

import flor_de_lotus.endereco.ViaCepResponse;
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


    public EnderecoResponse(ViaCepResponse viaCep) {
        this.cep = viaCep.cep();
        this.logradouro = viaCep.logradouro();
        this.bairro = viaCep.bairro();
        this.cidade = viaCep.localidade();
        this.estado = viaCep.estado();
    }
}
