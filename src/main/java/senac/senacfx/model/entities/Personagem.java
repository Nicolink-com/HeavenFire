package senac.senacfx.model.entities;

import java.io.Serializable;

public class Personagem implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nome;
    private String sexo;

    private Integer idade;


    public Personagem() {
    }

    public Personagem(Integer id, String nome, String sexo, Integer idade) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }

    public String getsexo() {
        return sexo;
    }

    public void setsexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Personagem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                ",idade='" + idade + '\'' +
                '}';
    }
}
