package senac.senacfx.model.dao;

import senac.senacfx.model.entities.Raca;
import senac.senacfx.model.entities.Classe;
import java.util.List;

public interface ClasseDao {

    void insert(Classe obj);
    void update(Classe obj);


    void deleteById(Integer id);
    Classe findById(Integer id);
    List<Classe> findAll();
    List<Classe> findByDepartment(Raca raca);

}
