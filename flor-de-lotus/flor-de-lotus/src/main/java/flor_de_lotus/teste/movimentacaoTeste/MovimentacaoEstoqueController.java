package flor_de_lotus.teste.movimentacaoTeste;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimentacao")
@RequiredArgsConstructor
public class MovimentacaoEstoqueController {
    public final MovimentacaoEstoqueService service;

    @PostMapping
    public ResponseEntity<4> cadastrar(@RequestBody MovimentacaoEstoqueRequest body){

    }
}
