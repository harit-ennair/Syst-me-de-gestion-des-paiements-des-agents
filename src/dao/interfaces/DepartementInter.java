package dao.interfaces;

import model.Departement;

import java.sql.SQLException;
import java.util.List;

public interface DepartementInter {
    public void addDepartement(Departement departement) throws SQLException;
    public void updateDepartement(Departement departement) throws SQLException;
    public void deleteDepartement(int id) throws SQLException;
    public String getDepartementById(int id) throws SQLException;
    public List<Departement> getAllDepartements() throws SQLException;
}
