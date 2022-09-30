package senac.senacfx.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Classe implements Serializable {
    private static final long serialVersionUId = 1L;
    private Integer Id;
    private String Nome;
    private Integer forca;
    
    private Integer Agilidade;
    private Integer Destreza;

    private Raca raca;

    public Classe() {
    }

    public Classe(Integer Id, String Nome, Integer forca, Integer Agilidade, Integer Destreza, Raca raca) {
        this.Id = Id;
        this.Nome = Nome;
        this.forca = forca;
        this.Agilidade = Agilidade;
        this.Destreza = Destreza;
        this.raca = raca;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public Integer getforca() {
        return forca;
    }

    public void setforca(Integer forca) {
        this.forca = forca;
    }

    public Integer getAgilidade() {
        return Agilidade;
    }

    public void setAgilidade(Integer Agilidade) {
        this.Agilidade = Agilidade;
    }

    public Integer getDestreza() {
        return Destreza;
    }

    public void setDestreza(Integer Destreza) {
        this.Destreza = Destreza;
    }

    public Raca getDepartment() {
        return raca;
    }

    public void setDepartment(Raca raca) {
        this.raca = raca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Classe other = (Classe) o;
        if (Id == null){
            if (other.Id != null)
                return false;
        } else if (!Id.equals(other.Id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Id == null) ? 0 : Id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Classe{" +
                "Id=" + Id +
                ", Nome='" + Nome + '\'' +
                ", forca='" + forca + '\'' +
                ", Agilidade=" + Agilidade +
                ", Destreza=" + Destreza +
                ", raca=" + raca +
                '}';
    }
}
