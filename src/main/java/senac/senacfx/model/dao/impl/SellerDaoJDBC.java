package senac.senacfx.model.dao.impl;

import senac.senacfx.db.DB;
import senac.senacfx.db.DbException;
import senac.senacfx.model.dao.SellerDao;
import senac.senacfx.model.entities.Classe;
import senac.senacfx.model.entities.Raca;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Classe obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "insert into seller " +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                            "values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

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
                    "update seller " +
                            "set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                            "where id = ?");

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

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
            st = conn.prepareStatement("delete from seller where Id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("Vendedor inexistente!");
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
                    "select seller.*, department.Name as DepName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.Id " +
                    "where seller.Id = ?");

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
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Classe instantiateSeller(ResultSet rs, Raca dep) throws SQLException{
        Classe obj = new Classe();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(new java.util.Date(rs.getTimestamp("BirthDate").getTime()));
        obj.setDepartment(dep);
        return obj;
    }
    @Override
    public List<Classe> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select seller.*, department.Name as DepName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.Id " +
                    "order by Name");

            rs = st.executeQuery();

            List<Classe> list = new ArrayList<>();
            Map<Integer, Raca> map = new HashMap<>();

            while (rs.next()){

                Raca dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
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

    @Override
    public List<Classe> findByDepartment(Raca raca) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select seller.*, raca.Name as DepName " +
                    "from seller inner join raca " +
                    "on seller.DepartmentId = raca.Id " +
                    "where DepartmentId = ? " +
                    "order by Name");

            st.setInt(1, raca.getId());

            rs = st.executeQuery();

            List<Classe> list = new ArrayList<>();
            Map<Integer, Raca> map = new HashMap<>();

            while (rs.next()){

                Raca dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
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
}
