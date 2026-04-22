package flor_de_lotus.funcionario;

import flor_de_lotus.consulta.dto.ConsultaResponseBody;
import flor_de_lotus.funcionario.dto.*;
import flor_de_lotus.funcionario.mapper.FuncionarioMapper;
import flor_de_lotus.usuario.Usuario;
import flor_de_lotus.usuario.dto.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Funcionários", description = "Endpoints para gerenciamento de funcionários")
@RequiredArgsConstructor
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    public final FuncionarioService service;

    @Operation(summary = "Cadastrar funcionário", description = "Cria um novo registro de funcionário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = FuncionarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody @Valid FuncionarioPostRequestBody body){
        Usuario usuario = UsuarioMapper.toEntityUsuario(body);
        Funcionario funcionario = FuncionarioMapper.toEntity(body);
        Funcionario cadastrado = service.cadastrar(funcionario, usuario);
        return ResponseEntity.status(201).body(FuncionarioMapper.toResponse(cadastrado));
    }

    @Operation(summary = "Listar todos os funcionários com paginação", description = "Retorna uma lista paginada de todos os funcionários cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FuncionarioResponse.class)))),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário encontrado", content = @Content)
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE', 'USUARIO')")
    //http://localhost:8080/funcionarios?pagina=1&tamanho=5
    public ResponseEntity<List<FuncionarioResponse>> listarTodos(@RequestParam int pagina, @RequestParam int tamanho){
        List<Funcionario> listaTodos = service.buscarTodosOffset(pagina, tamanho);
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponseList(listaTodos));
    }
    
    @Operation(summary = "Listar todos os funcionários em formato de card (resumo)", description = "Retorna uma lista resumida paginada de todos os funcionários cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FuncionarioCardResponse.class)))),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário encontrado", content = @Content)
    })
    @GetMapping("/cards")
    public ResponseEntity<List<FuncionarioCardResponse>> listarTodosCards(){
        List<Funcionario> listaTodos = service.listarTodos();
        if (listaTodos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(FuncionarioMapper.toCardResponseList(listaTodos));
    }

    @Operation(summary = "Buscar funcionário por ID", description = "Busca um funcionário específico pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado",
                    content = @Content(schema = @Schema(implementation = FuncionarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE', 'USUARIO')")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(service.buscarPorIdOuThrow(id)));
    }

    @Operation(summary = "Deletar funcionário", description = "Remove permanentemente um funcionário do sistema pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(summary = "Desativar funcionário", description = "Desativa um funcionário, tornando-o inativo no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @PatchMapping("/desativar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> desativarFuncionario(@PathVariable Integer id){
        service.desativarProfissional(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(summary = "Ativar funcionário", description = "Reativa um funcionário previamente desativado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @PatchMapping("/ativar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> ativarFuncionario(@PathVariable Integer id){
        service.ativarProfissional(id);
        return ResponseEntity.status(200).build();
    }

    @Operation(summary = "Atualizar parcialmente funcionário", description = "Atualiza parcialmente os dados de um funcionário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = FuncionarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('FUNCIONARIO', 'ADMIN')")
    public ResponseEntity<FuncionarioResponse> atualizarParcial(@PathVariable Integer id, @RequestBody FuncionarioPatchRequestBody body ){
        Funcionario atualizado = service.atualizarParcial(id, body);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(atualizado));
    }

    @Operation(summary = "Kpis da Página de Gestão de Funcionários", description = "Retorna os KPIs relacionados à gestão de funcionários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "qtd buscada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConsultaResponseBody.class)))),
    })
    @GetMapping("/kpisGestaoFuncionarios")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<KpisGestaoFuncionarioResponse> kpisDashboardFinanceiro() {
        long totaisFuncionariosAtivos = service.totalFuncionariosAtivos();
        long totaisFuncionarios = service.totalFuncionarios();
        long totalFuncionariosDesativados = service.totalFuncionariosInativos();
        long totalEspecialidades = service.qtdEspecialidades();


        KpisGestaoFuncionarioResponse response = new KpisGestaoFuncionarioResponse(totaisFuncionariosAtivos, totaisFuncionarios, totalFuncionariosDesativados, totalEspecialidades);

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/buscarPorTermo/{termo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE', 'USUARIO')")
    public ResponseEntity<List<FuncionarioResponse>> buscarPorTermo(@PathVariable String termo){

        List<FuncionarioResponse> response = FuncionarioMapper.toResponseList(service.buscarPorTermo(termo));
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/buscarPorStatus")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE', 'USUARIO')")
    public ResponseEntity<List<FuncionarioResponse>> buscarPorStatus(@RequestParam boolean ativo) {
        List<FuncionarioResponse> response = FuncionarioMapper.toResponseList(service.buscarPorStatus(ativo));
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/buscarPorTipoAtendimento/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE', 'USUARIO')")
    public ResponseEntity<List<FuncionarioResponse>> buscarPorTipo(@PathVariable TipoAtendimento tipo) {
        return ResponseEntity.ok(FuncionarioMapper.toResponseList(service.buscarPorTipoAtendimento(tipo)));
    }

    @GetMapping("/especialidades")
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'PACIENTE', 'USUARIO')")
    public ResponseEntity<List<Especialidade>> buscarEspecialidades() {
        return ResponseEntity.ok(service.buscarEspecialidades());
    }


}
