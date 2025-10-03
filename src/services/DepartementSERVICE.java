package services;

import dao.AgentDAO;
import dao.DepartementDAO;
import model.Agent;
import model.Departement;
import model.enums.TypeAgent;
import services.servicesInterfaces.DepartementSERVICEinter;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DepartementSERVICE implements DepartementSERVICEinter {

    private final DepartementDAO departementDAO;
    private final AgentDAO agentDAO;

    public DepartementSERVICE() {
        this.departementDAO = new DepartementDAO();
        this.agentDAO = new AgentDAO();
    }

    @Override
    public void creerDepartement(String nom, String description) throws Exception {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du département ne peut pas être vide");
        }

        Departement departement = new Departement(nom, description);
        try {
            departementDAO.addDepartement(departement);
            System.out.println("Département créé avec succès : " + nom);
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la création du département : " + e.getMessage());
        }
    }

    @Override
    public void modifierDepartement(int idDepartement, String nom, String description) throws Exception {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du département ne peut pas être vide");
        }

        Departement departement = new Departement(nom, description);
        departement.setIdDepartement(idDepartement);

        try {
            departementDAO.updateDepartement(departement);
            System.out.println("Département modifié avec succès : " + nom);
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la modification du département : " + e.getMessage());
        }
    }

    @Override
    public void supprimerDepartement(int idDepartement) throws Exception {

        List<Agent> agents = obtenirAgentsDepartement(idDepartement);
        if (!agents.isEmpty()) {
            throw new Exception("Impossible de supprimer le département : il contient encore " +
                               agents.size() + " agent(s)");
        }

        try {
            departementDAO.deleteDepartement(idDepartement);
            System.out.println("Département supprimé avec succès");
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la suppression du département : " + e.getMessage());
        }
    }

    @Override
    public void associerResponsable(int idDepartement, int idResponsable) throws Exception {
        Agent responsable = agentDAO.getAgentById(idResponsable);
        if (responsable == null) {
            throw new Exception("Responsable non trouvé avec l'ID : " + idResponsable);
        }

        if (responsable.getTypeAgent() != TypeAgent.RESPONSABLE_DEPARTEMENT) {
            throw new Exception("L'agent sélectionné n'est pas un responsable");
        }

        try {
            affecterAgentDepartement(idResponsable, idDepartement);
            System.out.println("Responsable associé au département avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'association du responsable : " + e.getMessage());
        }
    }

    private void affecterAgentDepartement(int idAgent, int idDepartement) throws Exception {
        Agent agent = agentDAO.getAgentById(idAgent);
        if (agent == null) {
            throw new Exception("Agent non trouvé avec l'ID : " + idAgent);
        }

        agent.setIdDepartement(idDepartement);

        try {
            agentDAO.updateAgent(agent);
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'affectation : " + e.getMessage());
        }
    }

    @Override
    public List<Agent> obtenirAgentsDepartement(int idDepartement) throws Exception {
        try {
            List<Agent> tousAgents = agentDAO.getAllAgents();
            return tousAgents.stream()
                    .filter(agent -> agent.getIdDepartement() == idDepartement)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des agents : " + e.getMessage());
        }
    }


    @Override
    public boolean verifierEligibiliteBonus(int idAgent) throws Exception {
        try {
            Agent agent = agentDAO.getAgentById(idAgent);
            if (agent == null) {
                throw new Exception("Agent non trouvé avec l'ID : " + idAgent);
            }

            return agent.getTypeAgent() == TypeAgent.OUVRIER ||
                   agent.getTypeAgent() == TypeAgent.RESPONSABLE_DEPARTEMENT;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la vérification d'éligibilité au bonus : " + e.getMessage());
        }
    }

    @Override
    public boolean verifierEligibiliteIndemnite(int idAgent) throws Exception {
        try {
            Agent agent = agentDAO.getAgentById(idAgent);
            if (agent == null) {
                throw new Exception("Agent non trouvé avec l'ID : " + idAgent);
            }

            return true;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la vérification d'éligibilité à l'indemnité : " + e.getMessage());
        }
    }
    @Override
    public List<Departement> getAllDepartements() throws Exception {
        try {
            return departementDAO.getAllDepartements();
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération des départements : " + e.getMessage());
        }
}
}
