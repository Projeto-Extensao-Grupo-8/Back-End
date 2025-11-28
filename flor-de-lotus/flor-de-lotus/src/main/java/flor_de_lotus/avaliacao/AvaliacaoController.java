package flor_de_lotus.avaliacao;

import flor_de_lotus.avaliacao.dto.AvaliacaoRequest;
import flor_de_lotus.avaliacao.dto.AvaliacaoResponse;
import flor_de_lotus.avaliacao.dto.AvaliacaoMapper;
import flor_de_lotus.funcionario.Funcionario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    @Operation(summary = "Registrar avaliação", description = "Cria uma nova avaliação da consulta no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Avaliação registrada com sucesso",
                    content = @Content(schema = @Schema(implementation = Funcionario.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AvaliacaoResponse> cadastrar(@RequestBody @Valid AvaliacaoRequest body){

        Avaliacao avaliacao = AvaliacaoMapper.toEntity(body);

        Avaliacao avaliacaoCadastrada = service.cadastrar(avaliacao, body.getFkConsulta());

        AvaliacaoResponse response = AvaliacaoMapper.toDto(avaliacaoCadastrada);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar todos as avaliações", description = "Retorna uma lista de todas as avaliações registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Funcionario.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<AvaliacaoResponse>> listarTodos(){
        List<Avaliacao> listaTodos = service.listarTodos();

        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<AvaliacaoResponse> response = AvaliacaoMapper.toDto(listaTodos);

        return ResponseEntity.status(200).body(response);
    }

}

