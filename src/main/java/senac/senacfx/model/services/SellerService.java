package senac.senacfx.model.services;

import senac.senacfx.model.dao.DaoFactory;
import senac.senacfx.model.dao.SellerDao;
import senac.senacfx.model.entities.Classe;

import java.util.List;

public class SellerService {

    //dependencia injetada usando padrao factory
    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Classe> findAll() {
        return dao.findAll();

        //Dados MOCK (fake) so para testar, sem puxar do banco por hora
//        List<Classe> list = new ArrayList<>();
//        list.add(new Classe(1,"Computadores"));
//        list.add(new Classe(2,"Alimentação"));
//        list.add(new Classe(3,"Financeiro"));
//        return list;

    }
    public void saveOrUpdate(Classe obj){
        if (obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
            }
        }

        public void remove(Classe obj){
            dao.deleteById(obj.getId());
        }
    }

