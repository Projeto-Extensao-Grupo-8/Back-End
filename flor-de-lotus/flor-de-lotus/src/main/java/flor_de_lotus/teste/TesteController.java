package flor_de_lotus.teste;

import flor_de_lotus.teste.dto.TestePatchRequestBody;
import flor_de_lotus.teste.dto.TestePostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testes")
@RequiredArgsConstructor
public class TesteController {
    private final TesteService service;

    @PostMapping
    public ResponseEntity<Teste> cadastrar(@RequestBody TestePostRequestBody body){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teste> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.findByIdOrThrow(id));
    }

    @GetMapping
    public ResponseEntity<List<Teste>> listarTodos(){
        return ResponseEntity.status(200).body(service.listarTodos());
    }

    @GetMapping("/{categoria}")
    public ResponseEntity<List<Teste>> listarPorCategoria(@PathVariable String categoria){
        List<Teste> listaTodos = service.listarPorCategoria(categoria);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaTodos);
    }

    @GetMapping("/{tipo}")
    public ResponseEntity<List<Teste>> listarPorTipo (@PathVariable String tipo){
        List<Teste> listaTodos = service.listarPorTipo(tipo);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaTodos);
    }

    @GetMapping("/validade")
    public ResponseEntity<Teste> buscarPorValidade(){
        Teste testeEncontrado = service.buscarPorValidade();
        return ResponseEntity.status(200).body(service.buscarPorValidade());
    }

    @GetMapping("/quantidade")
    public ResponseEntity<Teste> buscarPorQuantidade(){
        return ResponseEntity.status(200).body(service.buscarPorQtd());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Teste> atualizarParcial(@PathVariable Integer id, @RequestBody TestePatchRequestBody body){
        return ResponseEntity.status(200).body(service.atualizarParcial(id,body));
    }








}
