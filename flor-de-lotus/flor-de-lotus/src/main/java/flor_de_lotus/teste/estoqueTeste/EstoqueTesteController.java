package flor_de_lotus.teste.estoqueTeste;

import flor_de_lotus.teste.TesteService;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteRequest;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueTesteController {
    private final EstoqueTesteService service;


    @PostMapping
    public ResponseEntity<EstoqueTesteResponse> cadastrar(@RequestBody EstoqueTesteRequest body ){

    }
}
