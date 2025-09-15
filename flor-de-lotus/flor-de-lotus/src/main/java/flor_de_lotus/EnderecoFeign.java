package flor_de_lotus;

import flor_de_lotus.request.EnderecoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(url= "https://viacep.com.br/ws/" , name = "viacep" )
public interface EnderecoFeign {

    @GetMapping("{cep}/json")
    Optional<EnderecoResponse> buscaEnderecoCep(@PathVariable("cep") String cep);

}
