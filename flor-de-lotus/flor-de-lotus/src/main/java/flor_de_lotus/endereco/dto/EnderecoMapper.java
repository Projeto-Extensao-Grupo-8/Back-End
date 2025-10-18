package flor_de_lotus.endereco.dto;

import flor_de_lotus.endereco.Endereco;

public class EnderecoMapper {
    public static Endereco toEntityPost(EnderecoResponse response, String numero, String complemento){
        if (response == null){
            return null;
        }

        Endereco endereco = new Endereco();

        endereco.setBairro(response.getBairro());
        endereco.setCep(response.getCep());
        endereco.setEstado(response.getEstado());
        endereco.setCidade(response.getCidade());
        endereco.setLogradouro(response.getLogradouro());
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);

        return endereco;
    }

    public static Endereco toEntityPatch(EnderecoPatchRequestBody request){
        if (request == null){
            return null;
        }

        Endereco endereco = new Endereco();

        endereco.setBairro(request.getBairro());
        endereco.setCep(request.getCep());
        endereco.setEstado(request.getEstado());
        endereco.setCidade(request.getCidade());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setNumero(request.getNumero());
        endereco.setComplemento(request.getComplemento());

        return endereco;
    }

    public static EnderecoResponsePatch toResponse(Endereco entity){
        if (entity == null){
            return null;
        }

        EnderecoResponsePatch endereco = new EnderecoResponsePatch();

        endereco.setBairro(entity.getBairro());
        endereco.setCep(entity.getCep());
        endereco.setEstado(entity.getEstado());
        endereco.setCidade(entity.getCidade());
        endereco.setLogradouro(entity.getLogradouro());
        endereco.setNumero(entity.getNumero());
        endereco.setComplemento(entity.getComplemento());

        return endereco;
    }

}
