package flor_de_lotus.endereco;

import flor_de_lotus.endereco.dto.EnderecoPatchRequestBody;
import flor_de_lotus.endereco.dto.EnderecoResponse;
import flor_de_lotus.endereco.dto.EnderecoResponsePatch;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/endereco")
@Tag(name = "Endereços", description = "Endpoints utilizados para gerenciar os endereços")
public class EnderecoController {
    private final EnderecoService service;

    @Operation(summary = "Buscar endereço pelo cep via Api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CEP encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class)))
    })
    @GetMapping("/consulta")
    public ResponseEntity<EnderecoResponse> consultaCep(@RequestBody String cep){
        return ResponseEntity.ok(service.buscarCEP(cep));
    }


    @Operation(summary = "Editar endereço castrado no sistema via ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CEP encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<EnderecoResponsePatch> atualizarParcial(@PathVariable Integer id, @RequestBody EnderecoPatchRequestBody body){
        return ResponseEntity.status(200).body(service.atualizarParcial(id, body));
    }

}
