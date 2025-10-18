package flor_de_lotus.teste.estoqueTeste;

import flor_de_lotus.teste.TesteService;
import flor_de_lotus.teste.dto.TesteResponse;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteRequest;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueTesteController {
    private final EstoqueTesteService service;

    @PostMapping
    public ResponseEntity<EstoqueTesteResponse> cadastrar(@RequestBody EstoqueTesteRequest body ){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EstoqueTesteResponse> atualizar(@PathVariable Integer id,@RequestBody @Valid EstoqueTesteRequest body){
        return ResponseEntity.status(201).body(service.atualizarParcial(id, body));
    }

    @GetMapping
    public ResponseEntity<List<EstoqueTesteResponse>> buscarTodos(){
        List<EstoqueTesteResponse> listaResponse = service.listarTodos();
        if (listaResponse.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueTesteResponse> buscarID(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.findByIdOrThrow(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/quantidade")
    public ResponseEntity<EstoqueTesteResponse> buscarPorQuantidade(){
        return ResponseEntity.status(200).body(service.buscarPorQtd());
    }

}
