package flor_de_lotus.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;
    private String nome;
    @Column(unique = true )
    private String cpf;
    @Column(unique = true )
    private String crp;
    private String especialidade;
    private LocalDate dtAdmissao;
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean ativo;
    private Integer fkUsuario;

}
