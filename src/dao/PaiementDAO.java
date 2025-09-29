package dao;

import dao.interfaces.PaiementInter;
import model.Paiement;
import model.enums.TypePaiement;
import util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAO implements PaiementInter {

    private DatabaseConnection dbConnection;

    public PaiementDAO() {
        this.dbConnection = DatabaseConnection.getInstance();

    }

    @Override
    public void addPaiement(Paiement paiement) {
        String sql = "INSERT INTO paiement (type_paiement, montant, date_paiement, motif, condition_validee, id_agent) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
                    PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Handle null typePaiement safely
            String typePaiementStr;
            try {
                typePaiementStr = paiement.getTypePaiement();
            } catch (NullPointerException e) {
                typePaiementStr = "UNKNOWN";
                System.err.println("Warning: TypePaiement is null, using default value 'UNKNOWN'");
            }

            pstmt.setString(1, typePaiementStr);
            pstmt.setDouble(2, paiement.getMontant());
            pstmt.setDate(3, java.sql.Date.valueOf(paiement.getDatePaiement()));
            pstmt.setString(4, paiement.getMotif());
            pstmt.setBoolean(5, paiement.isConditionValidee());
            pstmt.setInt(6, paiement.getIdAgent());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Paiement added successfully!");
            }

        } catch (Exception e) {
            System.err.println("Error adding paiement: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updatePaiement(Paiement paiement) {
        String sql = "UPDATE paiement SET type_paiement = ?, montant = ?, date_paiement = ?, motif = ?, condition_validee = ?, id_agent = ? WHERE id_paiement = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Handle null typePaiement safely
            String typePaiementStr;
            try {
                typePaiementStr = paiement.getTypePaiement();
            } catch (NullPointerException e) {
                typePaiementStr = "UNKNOWN";
                System.err.println("Warning: TypePaiement is null, using default value 'UNKNOWN'");
            }

            pstmt.setString(1, typePaiementStr);
            pstmt.setDouble(2, paiement.getMontant());
            pstmt.setDate(3, java.sql.Date.valueOf(paiement.getDatePaiement()));
            pstmt.setString(4, paiement.getMotif());
            pstmt.setBoolean(5, paiement.isConditionValidee());
            pstmt.setInt(6, paiement.getIdAgent());
            pstmt.setInt(7, paiement.getIdPaiement());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Paiement updated successfully!");
            } else {
                System.out.println("No paiement found with ID: " + paiement.getIdPaiement());
            }

        } catch (Exception e) {
            System.err.println("Error updating paiement: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deletePaiement(int id) {
        String sql = "DELETE FROM paiement WHERE id_paiement = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Paiement deleted successfully!");
            } else {
                System.out.println("No paiement found with ID: " + id);
            }

        } catch (Exception e) {
            System.err.println("Error deleting paiement: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public Paiement getPaiementById(int id) {
        String sql = "SELECT * FROM paiement WHERE id_paiement = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Paiement paiement = new Paiement();
                    paiement.setIdPaiement(rs.getInt("id_paiement"));

                    // Handle TypePaiement enum conversion
                    String typeStr = rs.getString("type_paiement");
                    try {
                        paiement.setTypePaiement(TypePaiement.valueOf(typeStr));
                    } catch (IllegalArgumentException | NullPointerException e) {
                        System.err.println("Warning: Invalid TypePaiement value: " + typeStr);
                        // Set to a default value or leave null based on your business logic
                    }

                    // Handle BigDecimal conversion for amount
                    paiement.setMontant(BigDecimal.valueOf(rs.getDouble("montant")));
                    paiement.setDatePaiement(rs.getDate("date_paiement").toLocalDate());
                    paiement.setMotif(rs.getString("motif"));
                    paiement.setConditionValidee(rs.getBoolean("condition_validee"));
                    paiement.setIdAgent(rs.getInt("id_agent"));
                    return paiement;
                } else {
                    System.out.println("No paiement found with ID: " + id);
                }
            }

        } catch (Exception e) {
            System.err.println("Error retrieving paiement: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Paiement> getAllPaiements() {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Paiement paiement = new Paiement();
                paiement.setIdPaiement(rs.getInt("id_paiement"));

                // Handle TypePaiement enum conversion
                String typeStr = rs.getString("type_paiement");
                try {
                    paiement.setTypePaiement(TypePaiement.valueOf(typeStr));
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.err.println("Warning: Invalid TypePaiement value: " + typeStr);
                    // Set to a default value or leave null based on your business logic
                }

                // Handle BigDecimal conversion for amount
                paiement.setMontant(BigDecimal.valueOf(rs.getDouble("montant")));
                paiement.setDatePaiement(rs.getDate("date_paiement").toLocalDate());
                paiement.setMotif(rs.getString("motif"));
                paiement.setConditionValidee(rs.getBoolean("condition_validee"));
                paiement.setIdAgent(rs.getInt("id_agent"));

                paiements.add(paiement);
            }

            System.out.println("Retrieved " + paiements.size() + " paiements from database");

        } catch (Exception e) {
            System.err.println("Error retrieving all paiements: " + e.getMessage());
            e.printStackTrace();
        }

        return paiements;
    }

    // Méthodes spécialisées pour les agents
    public List<Paiement> getPaiementsByAgent(int idAgent) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE id_agent = ? ORDER BY date_paiement DESC";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idAgent);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Paiement paiement = new Paiement();
                    paiement.setIdPaiement(rs.getInt("id_paiement"));

                    // Handle TypePaiement enum conversion
                    String typeStr = rs.getString("type_paiement");
                    try {
                        paiement.setTypePaiement(TypePaiement.valueOf(typeStr));
                    } catch (IllegalArgumentException | NullPointerException e) {
                        System.err.println("Warning: Invalid TypePaiement value: " + typeStr);
                    }

                    paiement.setMontant(BigDecimal.valueOf(rs.getDouble("montant")));
                    paiement.setDatePaiement(rs.getDate("date_paiement").toLocalDate());
                    paiement.setMotif(rs.getString("motif"));
                    paiement.setConditionValidee(rs.getBoolean("condition_validee"));
                    paiement.setIdAgent(rs.getInt("id_agent"));

                    paiements.add(paiement);
                }
            }

        } catch (Exception e) {
            System.err.println("Error retrieving paiements for agent: " + e.getMessage());
            e.printStackTrace();
        }

        return paiements;
    }

    public List<Paiement> getPaiementsByAgentAndType(int idAgent, TypePaiement typePaiement) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE id_agent = ? AND type_paiement = ? ORDER BY date_paiement DESC";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idAgent);
            pstmt.setString(2, typePaiement.name());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Paiement paiement = new Paiement();
                    paiement.setIdPaiement(rs.getInt("id_paiement"));
                    paiement.setTypePaiement(TypePaiement.valueOf(rs.getString("type_paiement")));
                    paiement.setMontant(BigDecimal.valueOf(rs.getDouble("montant")));
                    paiement.setDatePaiement(rs.getDate("date_paiement").toLocalDate());
                    paiement.setMotif(rs.getString("motif"));
                    paiement.setConditionValidee(rs.getBoolean("condition_validee"));
                    paiement.setIdAgent(rs.getInt("id_agent"));

                    paiements.add(paiement);
                }
            }

        } catch (Exception e) {
            System.err.println("Error retrieving paiements by type for agent: " + e.getMessage());
            e.printStackTrace();
        }

        return paiements;
    }

    public List<Paiement> getPaiementsByAgentAndDateRange(int idAgent, LocalDate dateDebut, LocalDate dateFin) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE id_agent = ? AND date_paiement BETWEEN ? AND ? ORDER BY date_paiement DESC";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idAgent);
            pstmt.setDate(2, java.sql.Date.valueOf(dateDebut));
            pstmt.setDate(3, java.sql.Date.valueOf(dateFin));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Paiement paiement = new Paiement();
                    paiement.setIdPaiement(rs.getInt("id_paiement"));

                    String typeStr = rs.getString("type_paiement");
                    try {
                        paiement.setTypePaiement(TypePaiement.valueOf(typeStr));
                    } catch (IllegalArgumentException | NullPointerException e) {
                        System.err.println("Warning: Invalid TypePaiement value: " + typeStr);
                    }

                    paiement.setMontant(BigDecimal.valueOf(rs.getDouble("montant")));
                    paiement.setDatePaiement(rs.getDate("date_paiement").toLocalDate());
                    paiement.setMotif(rs.getString("motif"));
                    paiement.setConditionValidee(rs.getBoolean("condition_validee"));
                    paiement.setIdAgent(rs.getInt("id_agent"));

                    paiements.add(paiement);
                }
            }

        } catch (Exception e) {
            System.err.println("Error retrieving paiements by date range for agent: " + e.getMessage());
            e.printStackTrace();
        }

        return paiements;
    }

    public BigDecimal getTotalPaiementsByAgent(int idAgent) {
        String sql = "SELECT SUM(montant) as total FROM paiement WHERE id_agent = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, idAgent);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double total = rs.getDouble("total");
                    return BigDecimal.valueOf(total);
                }
            }

        } catch (Exception e) {
            System.err.println("Error calculating total paiements for agent: " + e.getMessage());
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }
}
