package flor_de_lotus.usuario;

import flor_de_lotus.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Nome do usuário", example = "Luíza Oliveira")
    private String nome;

    @Column(unique = true )
    @Schema(description = "Email do usuário", example = "luiza@oliveira.com")
    private String email;

    @Column(unique = true )
    @Schema(description = "Número do usuário", example = "119xxxxxxxx")
    private String telefone;

    @Column(unique = true )
    @Schema(description = "CPF do usuário", example = "04531164438")
    private String cpf;

    @Schema(description = "Senha do usuário para cadastro, deve conter ao menos 8 caracteres e no máximo 32, 2 letras maiúsculas e uma minúscula, 1 dígito e apenas letras e números", example = "P4sSwOrdExAmPl3")
    private String senha;

    @Schema(description = "Nível de permissão do usuário", example = "1")
    private String nivelPermissao;

    @OneToOne
    @Schema(description = "Fk relacionado ao endereço", example = "1")
    private Endereco fkEndereco;

    public Usuario(String nome, String email, String telefone, String cpf, String senha, String nivelPermissao) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.senha = senha;
        this.nivelPermissao = nivelPermissao;
    }
}
