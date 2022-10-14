package senac.senacfx.model.dao.impl;

import senac.senacfx.db.DB;
import senac.senacfx.db.DbException;
import senac.senacfx.model.dao.ClasseDao;
import senac.senacfx.model.entities.Classe;
import senac.senacfx.model.entities.Raca;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClasseDaoJDBC implements ClasseDao {
    private Connection conn;

    public ClasseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Classe obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "insert into classe " +
                            "(Nome, Forca, Resistencia, Destreza, Hp, Magia) " +
                            "values (?, ?, ?, ?, ?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getforca());
            st.setInt(3,  obj.getResistencia());
            st.setInt(4, obj.getDestreza());
            st.setInt(5,obj.getHP());
            st.setInt(6,obj.getMagia());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Error! No rows affected!");
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Classe obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "update Calsse " +
                            "set Name = ?, forca = ?, Resistencia = ?, Destreza = ?, HP = ?, Magia = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getforca());
            st.setInt(3, obj.getResistencia());
            st.setInt(4, obj.getDestreza());
            st.setInt(5, obj.getHP());
            st.setInt(6,obj.getMagia());

            st.executeUpdate();

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("delete from classe where Id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("Classe inexistente!");
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Classe findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select classe.*, raca.Nome as DepName " +
                    "from classe inner join raca " +
                    "on classe.Id = raca.Id " +
                    "where classe.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){
                Raca dep = instantiateDepartment(rs);
                Classe obj = instantiateSeller(rs, dep);
                return obj;

            }
            return null;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Raca instantiateDepartment(ResultSet rs) throws SQLException {
        Raca dep = new Raca();
        dep.setId(rs.getInt("Id"));
        dep.setNome(rs.getString("Nome"));
        return dep;
    }

    public Classe instantiateSeller(ResultSet rs, Raca dep) throws SQLException{
        Classe obj = new Classe();
        obj.setId(rs.getInt("Id"));
        obj.setNome(rs.getString("Nome"));
        obj.setforca(rs.getInt("forca"));
        obj.setResistencia(rs.getInt("Resistencia"));
        obj.setDestreza(rs.getInt("Destreza"));
        obj.setHP(rs.getInt("HP"));
        obj.setMagia(rs.getInt("Magia"));


        return obj;
    }
    @Override
    public List<Classe> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select classe.*, raca.Nome as DepName " +
                    "from classe inner join raca " +
                    "on classe.Id = raca.Id " +
                    "order by Nome");

            rs = st.executeQuery();

            List<Classe> list = new ArrayList<>();
            Map<Integer, Raca> map = new HashMap<>();

            while (rs.next()){

                Raca dep = map.get(rs.getInt("Id"));

                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("Id"), dep);
                }

                Classe obj = instantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

//    @Override
//    public List<Classe> findByDepartment(Raca raca) {
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        try{
//            st = conn.prepareStatement("" +
//                    "select classe.*, raca.Nome as DepName " +
//                    "from classe inner join raca " +
//                    "on classe.Id = raca.Id " +
//                    "where id = ? " +
//                    "order by Nome");
//
//            st.setInt(1, raca.getId());
//
//            rs = st.executeQuery();
//
//            List<Classe> list = new ArrayList<>();
//            Map<Integer, Raca> map = new HashMap<>();
//
//            while (rs.next()){
//
//                Raca dep = map.get(rs.getInt("Id"));
//
//                if (dep == null){
//                    dep = instantiateDepartment(rs);
//                    map.put(rs.getInt("Id"), dep);
//                }
//
//                Classe obj = instantiateSeller(rs, dep);
//                list.add(obj);
//            }
//            return list;
//        } catch (SQLException e){
//            throw new DbException(e.getMessage());
//        } finally {
//            DB.closeStatement(st);
//            DB.closeResultSet(rs);
//        }
//    }
}
