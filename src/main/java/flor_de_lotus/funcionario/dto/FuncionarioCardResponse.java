package flor_de_lotus.funcionario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import flor_de_lotus.funcionario.Especialidade;
import flor_de_lotus.funcionario.Modalidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)

@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioCardResponse {
    private Integer idFuncionario;
    private String nomeUsuario;
    private List<Especialidade> especialidades;
    private Modalidade modalidade;

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
}
