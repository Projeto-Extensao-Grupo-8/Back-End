package flor_de_lotus.controller;

import flor_de_lotus.request.EnderecoResponse;
import flor_de_lotus.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private final EnderecoService service;

    @GetMapping("/consulta")
    public ResponseEntity<EnderecoResponse> consultaCep(@RequestBody String cep){
        return ResponseEntity.ok(service.buscarCEP(cep));
    }


}
