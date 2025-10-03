package services;

import dao.AgentDAO;
import model.Agent;
import model.enums.TypeAgent;
import services.servicesInterfaces.AgentsSERVICEinter;

public class AgentsSERVICE implements AgentsSERVICEinter {

    private final AgentDAO agentDAO;

    public AgentsSERVICE() {
        this.agentDAO = new AgentDAO();
    }

    @Override
    public Agent getAgentById(int idAgent) {
        return agentDAO.getAgentById(idAgent);
    }

    @Override
    public Agent getAgentWithDepartement(int idAgent) {
        return agentDAO.getAgentWithDepartement(idAgent);
    }

    @Override
    public void ajouterAgent(String nom, String prenom, String email, String password,
                             TypeAgent typeAgent, int idDepartement) throws Exception {
        if (nom == null || nom.trim().isEmpty() || prenom == null || prenom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom et prénom ne peuvent pas être vides");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }

        Agent agent = new Agent(nom, prenom, email, password, typeAgent, idDepartement);

        try {
            agentDAO.addAgent(agent);
            System.out.println("Agent ajouté avec succès : " + nom + " " + prenom);
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'ajout de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void modifierAgent(int idAgent, String nom, String prenom, String email,
                              TypeAgent typeAgent, int idDepartement) throws Exception {
        Agent agent = agentDAO.getAgentById(idAgent);
        if (agent == null) {
            throw new Exception("Agent non trouvé avec l'ID : " + idAgent);
        }

        if (nom != null && !nom.trim().isEmpty()) {
            agent.setNom(nom);
        }
        if (prenom != null && !prenom.trim().isEmpty()) {
            agent.setPrenom(prenom);
        }
        if (email != null && email.contains("@")) {
            agent.setEmail(email);
        }
        if (typeAgent != null) {
            agent.setTypeAgent(typeAgent);
        }
        if (idDepartement > 0) {
            agent.setIdDepartement(idDepartement);
        }

        try {
            agentDAO.updateAgent(agent);
            System.out.println("Agent modifié avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la modification de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void supprimerAgent(int idAgent) throws Exception {
        try {
            agentDAO.deleteAgent(idAgent);
            System.out.println("Agent supprimé avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void affecterAgentDepartement(int idAgent, int idDepartement) throws Exception {
        Agent agent = agentDAO.getAgentById(idAgent);
        if (agent == null) {
            throw new Exception("Agent non trouvé avec l'ID : " + idAgent);
        }

        agent.setIdDepartement(idDepartement);

        try {
            agentDAO.updateAgent(agent);
            System.out.println("Agent affecté au département avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'affectation : " + e.getMessage());
        }
    }

    @Override
    public Agent authenticateAgent(String email, String password) {
        if (email == null || !email.contains("@")) {
            System.out.println("Email invalide");
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Le nom ne peut pas être vide");
            return null;
        }

        return agentDAO.authenticateAgent(email, password);
    }

}
