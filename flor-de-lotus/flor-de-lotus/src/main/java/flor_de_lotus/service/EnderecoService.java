package flor_de_lotus.service;

import flor_de_lotus.EnderecoFeign;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.request.EnderecoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EnderecoService {
    private final EnderecoFeign enderecoFeign;

    public EnderecoResponse buscarCEP(String cep){
        Optional<EnderecoResponse> enderecoEncontrado = enderecoFeign.buscaEnderecoCep(cep);
        if (enderecoEncontrado.isPresent()){
            return enderecoEncontrado.get();
        }

        throw new EntidadeNaoEncontradoException("CEP não encontrado");

    }


}
