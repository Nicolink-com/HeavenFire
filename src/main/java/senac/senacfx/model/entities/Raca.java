package senac.senacfx.model.entities;

import java.io.Serializable;

public class Raca implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nome;

    private Double altura;

    public Raca() {
    }

    public Raca(Integer id, String nome, Double altura) {
        this.id = id;
        this.nome = nome;
        this.altura = altura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Raca other = (Raca) o;
        if (id == null){
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "Raca{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", altura=" + altura +
                '}';
    }
}
