package flor_de_lotus.endereco;

import flor_de_lotus.endereco.dto.EnderecoMapper;
import flor_de_lotus.endereco.dto.EnderecoResponsePatch;
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
        Optional<ViaCepResponse> enderecoEncontrado = enderecoFeign.buscaEnderecoCep(cep);
        if (enderecoEncontrado.isPresent()){
            return new EnderecoResponse(enderecoEncontrado.get());
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

    public EnderecoResponsePatch atualizarParcial(Integer id, EnderecoPatchRequestBody dto){
        Endereco enderecoSavo = buscarPorIdOrThrow(id);

        if (dto.getCep() != null){
           EnderecoResponse cepBusca = buscarCEP(dto.getCep());
            System.out.println(cepBusca.getCidade());
           enderecoSavo.setCep(cepBusca.getCep());
           enderecoSavo.setLogradouro(cepBusca.getLogradouro());
           enderecoSavo.setBairro(cepBusca.getBairro());
           enderecoSavo.setCidade(cepBusca.getCidade());
           enderecoSavo.setEstado(cepBusca.getEstado());
        }

        if (dto.getLogradouro() != null) enderecoSavo.setLogradouro(dto.getLogradouro());
        if (dto.getBairro() != null) enderecoSavo.setBairro(dto.getBairro());
        if (dto.getCidade() != null) enderecoSavo.setCidade(dto.getCidade());
        if (dto.getEstado() != null) enderecoSavo.setEstado(dto.getEstado());


        if (dto.getComplemento() != null) enderecoSavo.setComplemento(dto.getComplemento());
        if (dto.getNumero() != null) enderecoSavo.setNumero(dto.getNumero());
        
        Endereco endereco = repository.save(enderecoSavo);
        return EnderecoMapper.toResponse(endereco);

    }


}
