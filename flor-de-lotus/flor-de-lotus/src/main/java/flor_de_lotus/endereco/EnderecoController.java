package flor_de_lotus.endereco;

import flor_de_lotus.endereco.dto.EnderecoPatchRequestBody;
import flor_de_lotus.endereco.dto.EnderecoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private final EnderecoService service;

    @GetMapping("/consulta")
    public ResponseEntity<EnderecoResponse> consultaCep(@RequestBody String cep){
        return ResponseEntity.ok(service.buscarCEP(cep));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Endereco> atualizarParcial(@PathVariable Integer id, @RequestBody EnderecoPatchRequestBody body){
        return ResponseEntity.status(200).body(service.atualizarParcial(id, body));
    }

}
