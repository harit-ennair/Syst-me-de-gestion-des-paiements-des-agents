package dao;

import dao.interfaces.AgentInter;
import model.Agent;
import model.Departement;
import model.enums.TypeAgent;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgentDAO implements AgentInter {
    
    private DatabaseConnection dbConnection;
    
    public AgentDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }
    
    @Override
    public void addAgent(Agent agent) {
        String sql = "INSERT INTO agent (nom, prenom, email, password, type_agent, id_departement) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, agent.getNom());
            pstmt.setString(2, agent.getPrenom());
            pstmt.setString(3, agent.getEmail());
            pstmt.setString(4, agent.getPassword());
            pstmt.setString(5, agent.getTypeAgent().name());
            
            if (agent.getIdDepartement() > 0) {
                pstmt.setInt(6, agent.getIdDepartement());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating agent failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    agent.setIdAgent(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating agent failed, no ID obtained.");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error adding agent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateAgent(Agent agent) {

        String sql = "UPDATE agent SET nom=?, prenom=?, email=?, password=?, type_agent=?, id_departement=? WHERE id_agent=?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, agent.getNom());
            pstmt.setString(2, agent.getPrenom());
            pstmt.setString(3, agent.getEmail());
            pstmt.setString(4, agent.getPassword());
            pstmt.setString(5, agent.getTypeAgent().name());

            if (agent.getIdDepartement() > 0) {
                pstmt.setInt(6, agent.getIdDepartement());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }

            pstmt.setInt(7, agent.getIdAgent());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("No agent found with id: " + agent.getIdAgent());
            } else {
                System.out.println("Agent with id " + agent.getIdAgent() + " updated successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating agent: " + e.getMessage());
            e.printStackTrace();
    }


    }

    @Override
    public void deleteAgent(int id) {
        String sql = "DELETE FROM agent WHERE id_agent=? ";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("No agent found with id: " + id);
            } else {
                System.out.println("Agent with id " + id + " deleted successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting agent: " + e.getMessage());
            e.printStackTrace();

        }
    }

    @Override
    public Agent getAgentById(int id) {
        String sql = "SELECT * FROM agent WHERE id_agent=?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Agent agent = new Agent();
                    agent.setIdAgent(rs.getInt("id_agent"));
                    agent.setNom(rs.getString("nom"));
                    agent.setPrenom(rs.getString("prenom"));
                    agent.setEmail(rs.getString("email"));
                    agent.setPassword(rs.getString("password"));
                    agent.setTypeAgent(TypeAgent.valueOf(rs.getString("type_agent")));
                    agent.setIdDepartement(rs.getInt("id_departement"));
                    return agent;
                } else {
                    System.out.println("No agent found with id: " + id);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving agent: " + e.getMessage());
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public List<Agent> getAllAgents() {
        String sql = "SELECT * FROM agent";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            List<Agent> agents = new ArrayList<>();
            while (rs.next()) {
                Agent agent = new Agent();
                agent.setIdAgent(rs.getInt("id_agent"));
                agent.setNom(rs.getString("nom"));
                agent.setPrenom(rs.getString("prenom"));
                agent.setEmail(rs.getString("email"));
                agent.setPassword(rs.getString("password"));
                agent.setTypeAgent(TypeAgent.valueOf(rs.getString("type_agent")));
                agent.setIdDepartement(rs.getInt("id_departement"));
                agents.add(agent);
            }
            return agents;

        } catch (SQLException e) {
            System.err.println("Error retrieving agents: " + e.getMessage());
            e.printStackTrace();

        }
        return List.of();
    }

    @Override
    public Agent getAgentWithDepartement(int id) {
        String sql = "SELECT a.id_agent, a.nom, a.prenom, a.email, a.password, a.type_agent, a.id_departement, " +
                    "d.nom as nom_departement " +
                    "FROM agent a " +
                    "LEFT JOIN departement d ON a.id_departement = d.id_departement " +
                    "WHERE a.id_agent = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Agent agent = new Agent();
                    agent.setIdAgent(rs.getInt("id_agent"));
                    agent.setNom(rs.getString("nom"));
                    agent.setPrenom(rs.getString("prenom"));
                    agent.setEmail(rs.getString("email"));
                    agent.setPassword(rs.getString("password"));
                    agent.setTypeAgent(TypeAgent.valueOf(rs.getString("type_agent")));
                    agent.setIdDepartement(rs.getInt("id_departement"));

                    // Créer et associer le département si il existe
                    if (rs.getObject("nom_departement") != null) {
                        Departement departement = new Departement();
                        departement.setIdDepartement(rs.getInt("id_departement"));
                        departement.setNom(rs.getString("nom_departement"));
                        // Ne pas définir de description car cette colonne n'existe pas dans la DB
                        agent.setDepartement(departement);
                    }

                    return agent;
                } else {
                    System.out.println("No agent found with id: " + id);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving agent with department: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public Agent authenticateAgent(String email, String password) {
        String sql = "SELECT * FROM agent WHERE email = ? AND password = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Agent agent = new Agent();
                    agent.setIdAgent(rs.getInt("id_agent"));
                    agent.setNom(rs.getString("nom"));
                    agent.setPrenom(rs.getString("prenom"));
                    agent.setEmail(rs.getString("email"));
                    agent.setPassword(rs.getString("password"));
                    agent.setTypeAgent(TypeAgent.valueOf(rs.getString("type_agent")));
                    agent.setIdDepartement(rs.getInt("id_departement"));
                    return agent;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error authenticating agent: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
