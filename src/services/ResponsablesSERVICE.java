package services;

import dao.AgentDAO;
import dao.DepartementDAO;
import dao.PaiementDAO;
import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
import services.servicesInterfaces.ResponsablesSERVICEinter;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponsablesSERVICE implements ResponsablesSERVICEinter {

    private DepartementDAO departementDAO;
    private AgentDAO agentDAO;
    private PaiementDAO paiementDAO;

    public ResponsablesSERVICE() {
        this.departementDAO = new DepartementDAO();
        this.agentDAO = new AgentDAO();
        this.paiementDAO = new PaiementDAO();
    }

    // ============ GESTION DES DÉPARTEMENTS (DIRECTEUR) ============

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
        // Vérifier s'il y a des agents dans le département
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

        // Affecter le responsable au département
        affecterAgentDepartement(idResponsable, idDepartement);
        System.out.println("Responsable associé au département avec succès");
    }

    // ============ GESTION DES AGENTS (RESPONSABLE) ============

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

    // ============ GESTION DES PAIEMENTS (RESPONSABLE) ============

    @Override
    public void ajouterPaiement(int idAgent, TypePaiement typePaiement, BigDecimal montant,
                               String motif) throws Exception {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }

        Agent agent = agentDAO.getAgentById(idAgent);
        if (agent == null) {
            throw new Exception("Agent non trouvé avec l'ID : " + idAgent);
        }

        // Vérifier les conditions selon le type de paiement
        if (!validerConditionPaiement(idAgent, typePaiement)) {
            throw new Exception("Conditions non remplies pour ce type de paiement");
        }

        Paiement paiement = new Paiement(typePaiement, montant, LocalDate.now(),
                                        motif, true, idAgent);

        try {
            paiementDAO.addPaiement(paiement);
            System.out.println("Paiement ajouté avec succès : " + typePaiement + " de " + montant);
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'ajout du paiement : " + e.getMessage());
        }
    }

    @Override
    public void ajouterSalaire(int idAgent, BigDecimal montant, String motif) throws Exception {
        ajouterPaiement(idAgent, TypePaiement.SALAIRE, montant, motif);
    }

    @Override
    public void ajouterPrime(int idAgent, BigDecimal montant, String motif) throws Exception {
        ajouterPaiement(idAgent, TypePaiement.PRIME, montant, motif);
    }

    @Override
    public void ajouterBonus(int idAgent, BigDecimal montant, String motif) throws Exception {
        if (!verifierEligibiliteBonus(idAgent)) {
            throw new Exception("L'agent n'est pas éligible au bonus");
        }
        ajouterPaiement(idAgent, TypePaiement.BONUS, montant, motif);
    }

    @Override
    public void ajouterIndemnite(int idAgent, BigDecimal montant, String motif) throws Exception {
        if (!verifierEligibiliteIndemnite(idAgent)) {
            throw new Exception("L'agent n'est pas éligible à l'indemnité");
        }
        ajouterPaiement(idAgent, TypePaiement.INDEMNITE, montant, motif);
    }

    // ============ CONSULTATION ET FILTRAGE ============

    @Override
    public List<Paiement> consulterPaiementsAgent(int idAgent) throws Exception {
        try {
            List<Paiement> tousPaiements = paiementDAO.getAllPaiements();
            return tousPaiements.stream()
                    .filter(p -> p.getIdAgent() == idAgent)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Erreur lors de la consultation des paiements : " + e.getMessage());
        }
    }

    @Override
    public List<Paiement> consulterPaiementsDepartement(int idDepartement) throws Exception {
        try {
            List<Agent> agents = obtenirAgentsDepartement(idDepartement);
            List<Paiement> paiementsDepartement = new ArrayList<>();

            for (Agent agent : agents) {
                paiementsDepartement.addAll(consulterPaiementsAgent(agent.getIdAgent()));
            }

            return paiementsDepartement;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la consultation des paiements du département : " + e.getMessage());
        }
    }

    @Override
    public List<Paiement> filtrerPaiements(int idDepartement, TypePaiement typePaiement,
                                          LocalDate dateDebut, LocalDate dateFin) throws Exception {
        List<Paiement> paiements = consulterPaiementsDepartement(idDepartement);

        return paiements.stream()
                .filter(p -> typePaiement == null || p.getTypePaiement().equals(typePaiement.name()))
                .filter(p -> dateDebut == null || !p.getDatePaiement().isBefore(dateDebut))
                .filter(p -> dateFin == null || !p.getDatePaiement().isAfter(dateFin))
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> identifierPaiementsInhabituels(int idDepartement) throws Exception {
        List<Paiement> paiements = consulterPaiementsDepartement(idDepartement);
        List<Paiement> paiementsInhabituels = new ArrayList<>();

        // Calculer les statistiques pour identifier les anomalies
        double moyenneMontant = paiements.stream()
                .mapToDouble(Paiement::getMontant)
                .average()
                .orElse(0.0);

        double seuilAnomalie = moyenneMontant * 2; // Montant supérieur à 2x la moyenne

        for (Paiement paiement : paiements) {
            // Paiements avec montant inhabituel
            if (paiement.getMontant() > seuilAnomalie) {
                paiementsInhabituels.add(paiement);
            }

            // Paiements avec conditions non validées
            if (!paiement.isConditionValidee()) {
                paiementsInhabituels.add(paiement);
            }

            // Bonus/Indemnités multiples pour le même agent dans le même mois
            LocalDate debutMois = paiement.getDatePaiement().withDayOfMonth(1);
            LocalDate finMois = debutMois.plusMonths(1).minusDays(1);

            if (paiement.getTypePaiement().equals("BONUS") || paiement.getTypePaiement().equals("INDEMNITE")) {
                long count = paiements.stream()
                        .filter(p -> p.getIdAgent() == paiement.getIdAgent())
                        .filter(p -> p.getTypePaiement().equals(paiement.getTypePaiement()))
                        .filter(p -> !p.getDatePaiement().isBefore(debutMois) &&
                                   !p.getDatePaiement().isAfter(finMois))
                        .count();

                if (count > 1) {
                    paiementsInhabituels.add(paiement);
                }
            }
        }

        return paiementsInhabituels.stream().distinct().collect(Collectors.toList());
    }

    // ============ MÉTHODES UTILITAIRES ============

    @Override
    public boolean verifierEligibiliteBonus(int idAgent) throws Exception {
        Agent agent = agentDAO.getAgentById(idAgent);
        if (agent == null) {
            return false;
        }

        // Critères d'éligibilité au bonus :
        // 1. L'agent doit être de type EMPLOYE ou RESPONSABLE
        // 2. Pas plus d'un bonus par mois
        LocalDate maintenant = LocalDate.now();
        LocalDate debutMois = maintenant.withDayOfMonth(1);

        List<Paiement> bonusMois = consulterPaiementsAgent(idAgent).stream()
                .filter(p -> p.getTypePaiement().equals("BONUS"))
                .filter(p -> !p.getDatePaiement().isBefore(debutMois))
                .collect(Collectors.toList());

        return (agent.getTypeAgent() == TypeAgent.OUVRIER || agent.getTypeAgent() == TypeAgent.RESPONSABLE_DEPARTEMENT)
               && bonusMois.isEmpty();
    }

    @Override
    public boolean verifierEligibiliteIndemnite(int idAgent) throws Exception {
        Agent agent = agentDAO.getAgentById(idAgent);
        if (agent == null) {
            return false;
        }

        LocalDate maintenant = LocalDate.now();
        LocalDate debutMois = maintenant.withDayOfMonth(1);

        List<Paiement> indemnitesMois = consulterPaiementsAgent(idAgent).stream()
                .filter(p -> p.getTypePaiement().equals("INDEMNITE"))
                .filter(p -> !p.getDatePaiement().isBefore(debutMois))
                .collect(Collectors.toList());

        return indemnitesMois.isEmpty();
    }

    @Override
    public boolean validerConditionPaiement(int idAgent, TypePaiement typePaiement) throws Exception {
        switch (typePaiement) {
            case SALAIRE:
            case PRIME:
                // Salaire et prime : librement selon le contrat
                return true;
            case BONUS:
                return verifierEligibiliteBonus(idAgent);
            case INDEMNITE:
                return verifierEligibiliteIndemnite(idAgent);
            default:
                return false;
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
}
