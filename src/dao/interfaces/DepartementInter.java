package dao.interfaces;

import model.Departement;

import java.sql.SQLException;

public interface DepartementInter {
    public void addDepartement(Departement departement) throws SQLException;
    public void updateDepartement(Departement departement) throws SQLException;
    public void deleteDepartement(int id) throws SQLException;
    public String getDepartementById(int id) throws SQLException;
    public java.util.List<String> getAllDepartements() throws SQLException;
}
