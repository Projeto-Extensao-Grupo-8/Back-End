package flor_de_lotus.entity;

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
    private Integer idUsuario;
    private String nome;
    @Column(unique = true )
    private String email;
    @Column(unique = true )
    private String telefone;
    @Column(unique = true )
    private String cpf;
    private String senha;
    private String nivelPermissao;
    @OneToOne
    private Endereco fkEndereco;

}
