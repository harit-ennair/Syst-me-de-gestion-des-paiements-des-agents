package controller;

import controller.controllerInterfaces.AgentCONTROLLERinter;
import model.Agent;
import model.Paiement;
import model.enums.TypeAgent;
import services.AgentsSERVICE;
import services.PaiementSERVICE;

import java.math.BigDecimal;
import java.util.List;

public class AgentController implements AgentCONTROLLERinter {

    private final AgentsSERVICE agentsService;
    private final PaiementSERVICE paiementService;

    public AgentController() {
        this.agentsService = new AgentsSERVICE();
        this.paiementService = new PaiementSERVICE();
    }


    public Agent consulterMesInformations(int idAgent) {
        return agentsService.getAgentById(idAgent);
    }


    public Agent consulterMesInformationsAvecDepartement(int idAgent) {
        return agentsService.getAgentWithDepartement(idAgent);
    }


    public void afficherResumeAgent(int idAgent) {

        Agent agent = agentsService.getAgentWithDepartement(idAgent);

        if (agent == null) {
            System.out.println("Agent non trouvé avec l'ID : " + idAgent);
            return;
        }

        System.out.println("=== RÉSUMÉ DE L'AGENT ===");
        System.out.println("ID: " + agent.getIdAgent());
        System.out.println("Nom: " + agent.getNom() + " " + agent.getPrenom());
        System.out.println("Email: " + agent.getEmail());
        System.out.println("Type: " + agent.getTypeAgent());

        if (agent.getDepartement() != null) {
            System.out.println("Département: " + agent.getDepartement().getNom());
        } else {
            System.out.println("Département: Non assigné");
        }

        BigDecimal total = paiementService.calculerTotalPaiements(idAgent);
        System.out.println("Total des paiements: " + total + "€");

        List<Paiement> paiements = paiementService.getHistoriquePaiements(idAgent);
        System.out.println("Nombre de paiements: " + paiements.size());

        System.out.println("========================");
    }

    @Override
    public void ajouterAgent(String nom, String prenom, String email, String password, TypeAgent typeAgent, int idDepartement) {
        try {
            agentsService.ajouterAgent(nom, prenom, email, password, typeAgent, idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void modifierAgent(int idAgent, String nom, String prenom, String email,
                              TypeAgent typeAgent, int idDepartement) {
        try {
            agentsService.modifierAgent(idAgent, nom, prenom, email, typeAgent, idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de la modification de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void supprimerAgent(int idAgent) {
        try {
            agentsService.supprimerAgent(idAgent);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void affecterAgentDepartement(int idAgent, int idDepartement) {
        try {
            agentsService.affecterAgentDepartement(idAgent, idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'affectation de l'agent : " + e.getMessage());
        }
    }

    public Agent authentifierAgent(String email, String password) {
        return agentsService.authenticateAgent(email, password);
    }
}
