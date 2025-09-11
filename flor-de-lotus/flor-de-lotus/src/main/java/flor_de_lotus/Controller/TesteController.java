package flor_de_lotus.Controller;

import flor_de_lotus.Domain.Teste;
import flor_de_lotus.Domain.Usuario;
import flor_de_lotus.Request.TestePatchRequestBody;
import flor_de_lotus.Request.TestePostRequestBody;
import flor_de_lotus.Service.TesteService;
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
    public ResponseEntity<Teste> cadastrar(TestePostRequestBody body){
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
        return ResponseEntity.status(200).body(service.listarPorCategoria(categoria));
    }

    @GetMapping("/{tipo}")
    public ResponseEntity<List<Teste>> listarPorTipo (@PathVariable String tipo){
        return ResponseEntity.status(200).body(service.listarPorTipo(tipo));
    }

    @GetMapping("/validade")
    public ResponseEntity<Teste> buscarPorValidade(){
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
