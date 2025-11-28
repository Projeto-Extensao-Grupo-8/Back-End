package flor_de_lotus.teste.estoqueTeste;

import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.teste.TesteService;
import flor_de_lotus.teste.dto.TesteResponse;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteMapper;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteRequest;
import flor_de_lotus.teste.estoqueTeste.dto.EstoqueTesteResponse;
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
@RequestMapping("/estoques")
@Tag(name = "Estoque Teste", description = "Endpoints utilizados para gerenciar o Estoque de Testes")
@RequiredArgsConstructor
public class EstoqueTesteController {
    private final EstoqueTesteService service;

    @Operation(summary = "Cadastrar novos estoques de testes no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estoque Registrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = TesteResponse.class))
            })
    })
    @PostMapping
    public ResponseEntity<EstoqueTesteResponse> cadastrar(@RequestBody EstoqueTesteRequest body ){

        EstoqueTeste cadastrar = EstoqueTesteMapper.toEntity(body);

        EstoqueTesteResponse response = EstoqueTesteMapper.toResponse(service.cadastrar(cadastrar, body.getFkTeste()));

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Atualizar testes no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque Atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = TesteResponse.class))
            })
    })
    @PatchMapping("/{id}")
    public ResponseEntity<EstoqueTesteResponse> atualizar(@PathVariable Integer id,@RequestBody @Valid EstoqueTesteRequest body){

        EstoqueTeste entity = EstoqueTesteMapper.toEntity(body);

        EstoqueTesteResponse response = EstoqueTesteMapper.toResponse(service.atualizarParcial(id, entity, body.getFkTeste()));

        return ResponseEntity.status(200).body(response);

    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping
    public ResponseEntity<List<EstoqueTesteResponse>> buscarTodos(){
        List<EstoqueTesteResponse> todosResponse = EstoqueTesteMapper.toResponseList(service.listarTodos());

        if (todosResponse.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(todosResponse);
    }

    @Operation(summary = "Buscar estoque via ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buscado com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Estoque não encontrado no sistema")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstoqueTesteResponse> buscarID(@PathVariable Integer id){

        EstoqueTesteResponse response = EstoqueTesteMapper.toResponse(service.findByIdOrThrow(id));

        return ResponseEntity.status(200).body(response);

    }

    @Operation(summary = "Deletar Estoque teste via ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Estoque não encontrado no sistema",
                    content = @Content(schema = @Schema(implementation = EntidadeConflitoException.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(summary = "Buscar o teste cadastrado no Sistema com menor quantidade ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teste buscada com Sucesso")
    })
    @GetMapping("/quantidade")
    public ResponseEntity<EstoqueTesteResponse> buscarPorQuantidade(){
        EstoqueTesteResponse estoqueTeste = EstoqueTesteMapper.toResponse(service.buscarPorQtd());
        if (estoqueTeste == null){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(estoqueTeste);
    }

}
