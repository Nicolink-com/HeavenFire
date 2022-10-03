package senac.senacfx.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Classe implements Serializable {
    private static final long serialVersionUId = 1L;
    private Integer Id;
    private String Nome;
    private Integer forca;
    
    private Integer Resistencia;
    private Integer Destreza;

    private Integer HP;

    private  Integer Magia;

    private Raca raca;

    public Classe() {
    }

    public Classe(Integer Id, String Nome, Integer forca, Integer Resistencia, Integer Destreza, Integer HP, Integer Magia, Raca raca) {
        this.Id = Id;
        this.Nome = Nome;
        this.forca = forca;
        this.Resistencia = Resistencia;
        this.Destreza = Destreza;
        this.raca = raca;
        this.HP = HP;
        this.Magia = Magia;
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

    public Integer getResistencia() {
        return Resistencia;
    }

    public void setResistencia(Integer Resistencia) {
        this.Resistencia = Resistencia;
    }

    public Integer getDestreza() {
        return Destreza;
    }

    public void setDestreza(Integer Destreza) {
        this.Destreza = Destreza;
    }

    public Integer getHP() {
        return HP;
    }

    public void setHP(Integer HP) {
        this.HP = HP;
    }

    public Integer getMagia() {
        return Magia;
    }

    public void setMagia(Integer magia) {
        Magia = magia;
    }

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
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
                ", Resistencia=" + Resistencia +
                ", Destreza=" + Destreza +
                ", raca=" + raca +
                '}';
    }
}
