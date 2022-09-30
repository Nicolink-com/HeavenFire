package senac.senacfx.model.dao.impl;

import senac.senacfx.db.DB;
import senac.senacfx.db.DbException;
import senac.senacfx.model.dao.RacaDao;
import senac.senacfx.model.entities.Raca;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RacaDaoJDBC implements RacaDao {
    private Connection conn;

    public RacaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Raca obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into department " +
                    "(Name) " +
                    "values (?) ",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());

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
    public void update(Raca obj) {

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("update department " +
                            "set Name = ? " +
                            "where Id = ?");

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("Error! No rows affected!");
            }

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
            st = conn.prepareStatement("delete from department where Id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("Departamento inexistente!");
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Raca findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select * from department " +
                    "where Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){
                Raca dep = instantiateDepartment(rs);
                return dep;

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
        dep.setName(rs.getString("Name"));
        return dep;
    }

    @Override
    public List<Raca> findAll() {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select * from department "+
                    "order by Name");

            rs = st.executeQuery();

            List<Raca> list = new ArrayList<>();
            Map<Integer, Raca> map = new HashMap<>();

            while (rs.next()){

                Raca dep = map.get(rs.getInt("Id"));

                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("Id"), dep);
                }

                list.add(dep);

            }
            return list;

        } catch (SQLException e){
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }


    }
}
