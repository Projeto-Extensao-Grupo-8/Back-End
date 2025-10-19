package flor_de_lotus.teste;

import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.teste.dto.TestePostRequest;
import flor_de_lotus.teste.dto.TesteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testes")
@RequiredArgsConstructor
@Tag(name = "Testes", description = "Endpoints utilizados para gerenciar os Testes")
public class TesteController {
    private final TesteService service;

    @Operation(summary = "Cadastrar novos testes no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Teste cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = TesteResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "Teste já cadastrado",
                    content = @Content(schema = @Schema(implementation = EntidadeConflitoException.class)))
    })
    @PostMapping
    public ResponseEntity<TesteResponse> cadastrar(@RequestBody @Valid TestePostRequest body){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @Operation(summary = "Deletar teste via ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Teste não encontrado no sistema",
                    content = @Content(schema = @Schema(implementation = EntidadeConflitoException.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(summary = "Buscar teste via ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buscado com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Teste não encontrado no sistema")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TesteResponse> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.findByIdOrThrow(id));
    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping
    public ResponseEntity<List<TesteResponse>> listarTodos(){
        List<TesteResponse> lista = service.listarTodos();

        if (lista.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema com categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados com essa categoria")
    })
    @GetMapping("/{categoria}")
    public ResponseEntity<List<TesteResponse>> listarPorCategoria(@PathVariable String categoria){
        List<TesteResponse> listaTodos = service.listarPorCategoria(categoria);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaTodos);
    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema com categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados com esse tipo")
    })
    @GetMapping("/{tipo}")
    public ResponseEntity<List<TesteResponse>> listarPorTipo (@PathVariable String tipo){
        List<TesteResponse> listaTodos = service.listarPorTipo(tipo);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaTodos);
    }

    @Operation(summary = "Buscar o testes cadastrado no Sistema com validade mais próxima")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes próximo da validade")

    })
    @GetMapping("/validade")
    public ResponseEntity<TesteResponse> buscarPorValidade(){
        TesteResponse testeEncontrado = service.buscarPorValidade();
        if (testeEncontrado == null){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(testeEncontrado);
    }





}
