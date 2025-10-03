package services;

import dao.AgentDAO;
import dao.PaiementDAO;
import model.Agent;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
import services.servicesInterfaces.PaiementSERVICEinter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PaiementSERVICE implements PaiementSERVICEinter {

    private final PaiementDAO paiementDAO;
    private final AgentDAO agentDAO;

    public PaiementSERVICE() {
        this.paiementDAO = new PaiementDAO();
        this.agentDAO = new AgentDAO();
    }

    @Override
    public List<Paiement> getHistoriquePaiements(int idAgent) {
        return paiementDAO.getPaiementsByAgent(idAgent);
    }

    @Override
    public List<Paiement> filterPaiementsByType(int idAgent, TypePaiement typePaiement) {
        return paiementDAO.getPaiementsByAgentAndType(idAgent, typePaiement);
    }

    @Override
    public List<Paiement> filterPaiementsByMontant(int idAgent, BigDecimal montantMin, BigDecimal montantMax) {
        List<Paiement> allPaiements = paiementDAO.getPaiementsByAgent(idAgent);

        return allPaiements.stream()
                .filter(p -> {
                    BigDecimal montant = BigDecimal.valueOf(p.getMontant());
                    return montant.compareTo(montantMin) >= 0 && montant.compareTo(montantMax) <= 0;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Paiement> filterPaiementsByDate(int idAgent, LocalDate dateDebut, LocalDate dateFin) {
        return paiementDAO.getPaiementsByAgentAndDateRange(idAgent, dateDebut, dateFin);
    }

    @Override
    public List<Paiement> sortPaiementsByMontant(List<Paiement> paiements, boolean croissant) {
        if (croissant) {
            return paiements.stream()
                    .sorted(Comparator.comparing(Paiement::getMontant))
                    .collect(Collectors.toList());
        } else {
            return paiements.stream()
                    .sorted(Comparator.comparing(Paiement::getMontant).reversed())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Paiement> sortPaiementsByDate(List<Paiement> paiements, boolean croissant) {
        if (croissant) {
            return paiements.stream()
                    .sorted(Comparator.comparing(Paiement::getDatePaiement))
                    .collect(Collectors.toList());
        } else {
            return paiements.stream()
                    .sorted(Comparator.comparing(Paiement::getDatePaiement).reversed())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public BigDecimal calculerTotalPaiements(int idAgent) {
        return paiementDAO.getTotalPaiementsByAgent(idAgent);
    }

    @Override
    public BigDecimal calculerTotalPaiementsByType(int idAgent, TypePaiement typePaiement) {
        List<Paiement> paiements = paiementDAO.getPaiementsByAgentAndType(idAgent, typePaiement);

        return paiements.stream()
                .map(p -> BigDecimal.valueOf(p.getMontant()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculerTotalPaiementsByPeriode(int idAgent, LocalDate dateDebut, LocalDate dateFin) {
        List<Paiement> paiements = paiementDAO.getPaiementsByAgentAndDateRange(idAgent, dateDebut, dateFin);

        return paiements.stream()
                .map(p -> BigDecimal.valueOf(p.getMontant()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


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

    private List<Agent> obtenirAgentsDepartement(int idDepartement) throws Exception {
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


    @Override
    public boolean verifierEligibiliteBonus(int idAgent) throws Exception {
        Agent agent = agentDAO.getAgentById(idAgent);
        if (agent == null) {
            return false;
        }


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
        return switch (typePaiement) {
            case SALAIRE, PRIME -> true;
            case BONUS -> verifierEligibiliteBonus(idAgent);
            case INDEMNITE -> verifierEligibiliteIndemnite(idAgent);
        };
    }


}
