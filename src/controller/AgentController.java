package controller;

import controller.controllerInterfaces.AgentCONTROLLERinter;
import model.Agent;
import model.Paiement;
import model.enums.TypePaiement;
import services.AgentsSERVICE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//public class AgentController implements AgentsSERVICEinter {
public class AgentController {

    private AgentsSERVICE agentsService;

    public AgentController() {
        this.agentsService = new AgentsSERVICE();
    }

    /**
     * Permet à un agent de consulter ses informations personnelles de base
     */
    public Agent consulterMesInformations(int idAgent) {
        return agentsService.getAgentById(idAgent);
    }

    /**
     * Permet à un agent de consulter ses informations personnelles avec son département
     */
    public Agent consulterMesInformationsAvecDepartement(int idAgent) {
        return agentsService.getAgentWithDepartement(idAgent);
    }

    /**
     * Permet à un agent de voir l'historique complet de ses paiements
     */
    public List<Paiement> voirMonHistoriquePaiements(int idAgent) {
        return agentsService.getHistoriquePaiements(idAgent);
    }

    /**
     * Permet à un agent de filtrer ses paiements par type
     */
    public List<Paiement> filtrerMesPaiementsParType(int idAgent, TypePaiement typePaiement) {
        return agentsService.filterPaiementsByType(idAgent, typePaiement);
    }

    /**
     * Permet à un agent de filtrer ses paiements par plage de montant
     */
    public List<Paiement> filtrerMesPaiementsParMontant(int idAgent, BigDecimal montantMin, BigDecimal montantMax) {
        return agentsService.filterPaiementsByMontant(idAgent, montantMin, montantMax);
    }

    /**
     * Permet à un agent de filtrer ses paiements par période
     */
    public List<Paiement> filtrerMesPaiementsParDate(int idAgent, LocalDate dateDebut, LocalDate dateFin) {
        return agentsService.filterPaiementsByDate(idAgent, dateDebut, dateFin);
    }

    /**
     * Permet à un agent de trier ses paiements par montant
     */
    public List<Paiement> trierMesPaiementsParMontant(int idAgent, boolean croissant) {
        List<Paiement> paiements = agentsService.getHistoriquePaiements(idAgent);
        return agentsService.sortPaiementsByMontant(paiements, croissant);
    }

    /**
     * Permet à un agent de trier ses paiements par date
     */
    public List<Paiement> trierMesPaiementsParDate(int idAgent, boolean croissant) {
        List<Paiement> paiements = agentsService.getHistoriquePaiements(idAgent);
        return agentsService.sortPaiementsByDate(paiements, croissant);
    }

    /**
     * Permet à un agent de calculer le total de ses paiements
     */
    public BigDecimal calculerMonTotalPaiements(int idAgent) {
        return agentsService.calculerTotalPaiements(idAgent);
    }

    /**
     * Permet à un agent de calculer le total de ses paiements par type
     */
    public BigDecimal calculerMonTotalPaiementsParType(int idAgent, TypePaiement typePaiement) {
        return agentsService.calculerTotalPaiementsByType(idAgent, typePaiement);
    }

    /**
     * Permet à un agent de calculer le total de ses paiements sur une période
     */
    public BigDecimal calculerMonTotalPaiementsParPeriode(int idAgent, LocalDate dateDebut, LocalDate dateFin) {
        return agentsService.calculerTotalPaiementsByPeriode(idAgent, dateDebut, dateFin);
    }

    /**
     * Affiche un résumé complet des informations et paiements d'un agent
     */
    public void afficherResumeAgent(int idAgent) {
        // Récupérer les informations de l'agent
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

        // Afficher le total des paiements
        BigDecimal total = agentsService.calculerTotalPaiements(idAgent);
        System.out.println("Total des paiements: " + total + "€");

        // Afficher le nombre de paiements
        List<Paiement> paiements = agentsService.getHistoriquePaiements(idAgent);
        System.out.println("Nombre de paiements: " + paiements.size());

        System.out.println("========================");
    }
}
