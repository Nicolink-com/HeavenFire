package senac.senacfx.model.services;

import senac.senacfx.model.dao.DaoFactory;
import senac.senacfx.model.dao.DepartmentDao;
import senac.senacfx.model.entities.Raca;

import java.util.List;

public class DepartmentService {

    //dependencia injetada usando padrao factory
    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Raca> findAll() {
        return dao.findAll();

        //Dados MOCK (fake) so para testar, sem puxar do banco por hora
//        List<Raca> list = new ArrayList<>();
//        list.add(new Raca(1,"Computadores"));
//        list.add(new Raca(2,"Alimentação"));
//        list.add(new Raca(3,"Financeiro"));
//        return list;

    }
    public void saveOrUpdate(Raca obj){
        if (obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
            }
        }

        public void remove(Raca obj){
            dao.deleteById(obj.getId());
        }
    }

