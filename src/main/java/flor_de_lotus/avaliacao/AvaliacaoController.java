package flor_de_lotus.avaliacao;

import flor_de_lotus.avaliacao.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
                    content = @Content(schema = @Schema(implementation = AvaliacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    public ResponseEntity<AvaliacaoResponse> cadastrar(@RequestBody @Valid AvaliacaoRequest body){

        Avaliacao avaliacaoCadastrada = service.cadastrar(body);

        AvaliacaoResponse response = AvaliacaoMapper.toResponse(avaliacaoCadastrada);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar todos as avaliações", description = "Retorna uma lista de todas as avaliações registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = AvaliacaoResponse.class))),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada", content = @Content)
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<AvaliacaoResponse>> listarTodos(){
        List<Avaliacao> listaTodos = service.listarTodos();

        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<AvaliacaoResponse> response = AvaliacaoMapper.toResponseList(listaTodos);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Listagem do gráfico de avalicao por consulta", description = "Retorna uma lista de todas as avaliações registradas por consulta.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = AvaliacaoResponse.class))),
    })
    @GetMapping("/graficoPorConsulta")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<GraficoAvaliacaoPorConsulta>> graficoAvaliacaoPorConsulta(){
        return ResponseEntity.status(200).body(service.graficoAvaliacaoPorConsulta());
    }


    @Operation(summary = "Listagem do gráfico de avalicao por funcionario", description = "Retorna uma lista de todas as avaliações registradas por funcinario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = AvaliacaoResponse.class))),
    })
    @GetMapping("/graficoPorFuncionario")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<GraficoAvaliacaoPorFuncionario>> graficoAvaliacaoPorFuncionario(){
        return ResponseEntity.status(200).body(service.graficoAvaliacaoPorFuncionario());
    }

}
