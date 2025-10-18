package flor_de_lotus.teste;

import flor_de_lotus.teste.dto.TestePostRequest;
import flor_de_lotus.teste.dto.TesteResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<TesteResponse> cadastrar(@RequestBody @Valid TestePostRequest body){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TesteResponse> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.findByIdOrThrow(id));
    }

    @GetMapping
    public ResponseEntity<List<TesteResponse>> listarTodos(){
        List<TesteResponse> lista = service.listarTodos();

        if (lista.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping("/{categoria}")
    public ResponseEntity<List<TesteResponse>> listarPorCategoria(@PathVariable String categoria){
        List<TesteResponse> listaTodos = service.listarPorCategoria(categoria);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaTodos);
    }

    @GetMapping("/{tipo}")
    public ResponseEntity<List<TesteResponse>> listarPorTipo (@PathVariable String tipo){
        List<TesteResponse> listaTodos = service.listarPorTipo(tipo);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaTodos);
    }

    @GetMapping("/validade")
    public ResponseEntity<TesteResponse> buscarPorValidade(){
        TesteResponse testeEncontrado = service.buscarPorValidade();
        return ResponseEntity.status(200).body(testeEncontrado);
    }





}
