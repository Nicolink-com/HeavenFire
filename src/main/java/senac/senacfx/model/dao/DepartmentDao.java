package senac.senacfx.model.dao;

import senac.senacfx.model.entities.Raca;

import java.util.List;

public interface DepartmentDao {

    void insert(Raca obj);
    void update(Raca obj);
    void deleteById(Integer id);
    Raca findById(Integer id);
    List<Raca> findAll();

}
