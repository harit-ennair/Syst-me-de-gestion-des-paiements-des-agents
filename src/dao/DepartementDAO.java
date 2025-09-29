package dao;


import dao.interfaces.AgentInter;
import dao.interfaces.DepartementInter;
import model.Agent;
import model.Departement;
import model.enums.TypeAgent;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementDAO implements DepartementInter {

    private DatabaseConnection dbConnection;

    public DepartementDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }


    @Override
    public void addDepartement(Departement departement) throws  SQLException {
        String sql = "INSERT INTO departement (nom) VALUES (?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, departement.getNom());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating departement failed, no rows affected.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding departement: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void updateDepartement(Departement departement) throws   SQLException {
        String sql = "UPDATE departement SET nom = ? WHERE id_departement = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, departement.getNom());
            pstmt.setInt(2, departement.getIdDepartement());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating departement failed, no rows affected.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating departement: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void deleteDepartement(int id) throws  SQLException {
        String sql = "DELETE FROM departement WHERE id_departement = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting departement failed, no rows affected.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting departement: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public String getDepartementById(int id) throws  SQLException {
        String sql = "SELECT * FROM departement WHERE id_departement = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("id_departement") + ": " + rs.getString("nom");
            } else {
                return null; // Departement not found
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving departement: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getAllDepartements() throws  SQLException {
        List<String> departements = new ArrayList<>();
        String sql = "SELECT * FROM departement";
        try (Connection connection = dbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                departements.add(rs.getString("id_departement") + ": " + rs.getString("nom"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving departements: " + e.getMessage());
            e.printStackTrace();
        }
        return departements;
    }
}