package flor_de_lotus.usuario;

import flor_de_lotus.exception.BadRequestException;
import flor_de_lotus.exception.EntidadeConflitoException;
import flor_de_lotus.exception.EntidadeNaoEncontradoException;
import flor_de_lotus.exception.UnauthorizedException;
import flor_de_lotus.usuario.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints utilizados para gerenciar os usuários")
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping("/cadastro")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Recebe dados de um novo usuário e o armazena, retornando o usuário criado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseBody.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida (Dados de entrada mal-formatados ou falha na validação do corpo).",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Usuário já existente com este e-mail ou CPF.",
                    content = @Content(schema = @Schema(implementation = EntidadeConflitoException.class))
            )
    })
    public ResponseEntity<UsuarioResponseBody> cadastrar(@RequestBody @Valid UsuarioPostRequestBody body){
        return ResponseEntity.status(201).body(service.cadastrar(body));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Fazer login",
            description = "Recebe dados de um usuário previamente cadastrado e devolve um token permitindo acessar outras partes."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário logado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioTokenResponseBody.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida (Dados de entrada mal-formatados ou falha na validação do corpo).",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class))
            )
    })
    public ResponseEntity<UsuarioTokenResponseBody> login(@RequestBody @Valid UsuarioLoginRequestBody body){
        return ResponseEntity.status(200).body(service.login(body));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar um usuário",
            description = "Recebe o id de um usuário previamente cadastrado e o remove do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT não validado ou nível de permissão insuficiente",
                    content = @Content(schema = @Schema(implementation = UnauthorizedException.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class))
            )

    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletePorId(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar um usuário",
            description = "Recebe o id de um usuário previamente cadastrado e atualiza suas informações"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseBody.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT não validado ou nível de permissão insuficiente",
                    content = @Content(schema = @Schema(implementation = UnauthorizedException.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class))
            )

    })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseBody> atualizarParcial(@PathVariable Integer id,@RequestBody @Valid UsuarioReplaceRequestBody body){
        return ResponseEntity.status(200).body(service.atulizarParcial(id,body));
    }

    @Operation(
            summary = "Atualizar um usuário",
            description = "Recebe o id de um usuário previamente cadastrado e atualiza suas informações"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseBody.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT não validado ou nível de permissão insuficiente",
                    content = @Content(schema = @Schema(implementation = UnauthorizedException.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = EntidadeNaoEncontradoException.class))
            )

    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseBody> buscarUsuario(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.buscarPorIdOuThrow(id));
    }

    @Operation(
            summary = "Listar os usuários",
            description = "Lista todos os usuários cadastrados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseBody.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token JWT não validado ou nível de permissão insuficiente",
                    content = @Content(schema = @Schema(implementation = UnauthorizedException.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseBody>> listarTodos(){

        List<UsuarioResponseBody> lista = service.listarTodos();
        return ResponseEntity.status(200).body(lista);

    }

}