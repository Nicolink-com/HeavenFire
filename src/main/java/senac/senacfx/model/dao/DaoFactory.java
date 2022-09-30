package senac.senacfx.model.dao;

import senac.senacfx.db.DB;
import senac.senacfx.model.dao.impl.RacaDaoJDBC;
import senac.senacfx.model.dao.impl.ClasseDaoJDBC;

public class DaoFactory {

    public static ClasseDao createSellerDao(){
        return new ClasseDaoJDBC(DB.getConnection());
    }

    public static RacaDao createDepartmentDao(){
        return new RacaDaoJDBC(DB.getConnection());
    }

}
