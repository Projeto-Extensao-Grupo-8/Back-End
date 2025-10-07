package flor_de_lotus.endereco;

import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.endereco.dto.EnderecoPatchRequestBody;
import flor_de_lotus.endereco.dto.EnderecoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EnderecoService {
    private final EnderecoFeign enderecoFeign;
    private final EnderecoRepository repository;

    public EnderecoResponse buscarCEP(String cep){
        Optional<EnderecoResponse> enderecoEncontrado = enderecoFeign.buscaEnderecoCep(cep);
        if (enderecoEncontrado.isPresent()){
            return enderecoEncontrado.get();
        }

        throw new EntidadeNaoEncontradoException("CEP não encontrado");

    }

    public Endereco buscarPorIdOrThrow(Integer id){
        Optional<Endereco> endEncontrado = repository.findById(id);
        if (endEncontrado.isPresent()){
            return endEncontrado.get();
        }

        throw new EntidadeNaoEncontradoException("Endereço não encontrado");
    }

    public Endereco atualizarParcial(Integer id, EnderecoPatchRequestBody dto){
        Endereco enderecoSavo = buscarPorIdOrThrow(id);
        if (dto.getCep() != null){
           EnderecoResponse enderecoResponse = buscarCEP(dto.getCep());
           enderecoSavo.setCep(enderecoResponse.getCep());
           enderecoSavo.setCidade(enderecoResponse.getLocalidade());
           enderecoSavo.setNumero(enderecoResponse.getBairro());
           enderecoSavo.setLogradouro(enderecoResponse.getLogradouro());
           enderecoSavo.setBairro(enderecoResponse.getBairro());
           return repository.save(enderecoSavo);
        }

        if (dto.getComplemento() != null) enderecoSavo.setComplemento(dto.getComplemento());
        if (dto.getNumero() != null) enderecoSavo.setNumero(dto.getNumero());
        return repository.save(enderecoSavo);


    }


}
