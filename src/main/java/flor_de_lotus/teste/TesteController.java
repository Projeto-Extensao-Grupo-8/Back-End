package flor_de_lotus.teste;

import flor_de_lotus.consulta.dto.ConsultaResponseBody;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.teste.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<TesteResponse> cadastrar(@RequestBody @Valid TestePostRequest body){

        Teste teste = TesteMapper.toEntity(body);

        Teste cadastrado = service.cadastrar(teste);

        TesteResponse testeResponse = TesteMapper.toResponse(cadastrado);

        return ResponseEntity.status(201).body(testeResponse);


    }

    @Operation(summary = "Deletar teste via ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Teste não encontrado no sistema",
                    content = @Content(schema = @Schema(implementation = EntidadeConflitoException.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<TesteResponse> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(TesteMapper.toResponse(service.findByIdOrThrow(id)));
    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<TesteResponse>> listarTodos(){

        List<Teste> lista = service.listarTodos();

        if (lista.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<TesteResponse> listaResponse = TesteMapper.toResponseList(lista);

        return ResponseEntity.status(200).body(listaResponse);

    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema com categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados com essa categoria")
    })
    @GetMapping("/{categoria}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<TesteResponse>> listarPorCategoria(@PathVariable String categoria){
        List<Teste> listaTodos = service.listarPorCategoria(categoria);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<TesteResponse> listaResponse = TesteMapper.toResponseList(listaTodos);

        return ResponseEntity.status(200).body(listaResponse);

    }

    @Operation(summary = "Buscar todos os testes cadastrado no Sistema com categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes cadastrados com esse tipo")
    })
    @GetMapping("/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<TesteResponse>> listarPorTipo (@PathVariable String tipo){
        List<Teste> listaTodos = service.listarPorTipo(tipo);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<TesteResponse> listaResponse = TesteMapper.toResponseList(listaTodos);

        return ResponseEntity.status(200).body(listaResponse);

    }

    @Operation(summary = "Buscar o testes cadastrado no Sistema com validade mais próxima")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Testes buscada com Sucesso"),
            @ApiResponse(responseCode = "204", description = "Não possui testes próximo da validade")

    })
    @GetMapping("/validade")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<Integer> buscarPorValidade(){
        Integer qtdTesteEncontrado = service.buscarQtdTesteComValidadeProxima();
        if (qtdTesteEncontrado == null){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(qtdTesteEncontrado);
    }

    @Operation(summary = "Kpi da página gestão de testes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/kpisGestaoEstoque")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<KpisGestaoEstoqueResponse> kpisGestaoEstoque() {
        Integer buscarQtdValidadeProxima90Dias = service.buscarQtdValidadeProxima90Dias();
        Integer buscarQtdEstoqueCritico = service.buscarQtdEstoqueCritico();
        Integer buscarTotalUnidadesFisicas = service.buscarTotalUnidadesFisicas();
        Integer buscarTotalUnidadesDigitais = service.buscarTotalUnidadesDigitais();

        KpisGestaoEstoqueResponse response = new KpisGestaoEstoqueResponse( buscarQtdValidadeProxima90Dias, buscarQtdEstoqueCritico, buscarTotalUnidadesFisicas, buscarTotalUnidadesDigitais);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Kpi de resumo do estoque gestao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/kpisResumoEstoque")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<KpisResumoEstoqueResponse> kpisResumoEstoque() {
        Double buscarValorTotalEstoque = service.buscarValorTotalEstoque();
        Integer buscarTotalUnidadesAoTodo = service.buscarTotalUnidadesAoTodo();
        Long buscarTotalTiposTestes = service.buscarTotalTiposTestes();

        KpisResumoEstoqueResponse response = new KpisResumoEstoqueResponse( buscarValorTotalEstoque, buscarTotalUnidadesAoTodo, buscarTotalTiposTestes);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Listar alertas de estoque crítico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/alertasEstoqueCritico")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    public ResponseEntity<List<TesteAlertaResponseDTO>> listarAlertas() {

        List<Teste> testesCriticos = service.buscarAlertasDeEstoque();

        List<TesteAlertaResponseDTO> alertas = testesCriticos.stream()
                .map(teste -> new TesteAlertaResponseDTO(
                        teste.getNome(),
                        teste.getQtd(),
                        teste.getEstoqueMinimo()
                ))
                .toList();

        return ResponseEntity.status(200).body(alertas);
    }

}
