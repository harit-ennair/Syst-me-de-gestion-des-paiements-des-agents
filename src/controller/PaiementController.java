package controller;

import controller.controllerInterfaces.PaiementCONTROLLERinter;
import model.Paiement;
import model.enums.TypePaiement;
import services.PaiementSERVICE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PaiementController implements PaiementCONTROLLERinter {

    private final PaiementSERVICE paiementService;

    public PaiementController() {
        this.paiementService = new PaiementSERVICE();
    }


    public List<Paiement> voirMonHistoriquePaiements(int idAgent) {
        return paiementService.getHistoriquePaiements(idAgent);
    }


    public List<Paiement> filtrerMesPaiementsParType(int idAgent, TypePaiement typePaiement) {
        return paiementService.filterPaiementsByType(idAgent, typePaiement);
    }

    public List<Paiement> filtrerMesPaiementsParMontant(int idAgent, BigDecimal montantMin, BigDecimal montantMax) {
        return paiementService.filterPaiementsByMontant(idAgent, montantMin, montantMax);
    }

    public List<Paiement> filtrerMesPaiementsParDate(int idAgent, LocalDate dateDebut, LocalDate dateFin) {
        return paiementService.filterPaiementsByDate(idAgent, dateDebut, dateFin);
    }

    public List<Paiement> trierMesPaiementsParMontant(int idAgent, boolean croissant) {
        List<Paiement> paiements = paiementService.getHistoriquePaiements(idAgent);
        return paiementService.sortPaiementsByMontant(paiements, croissant);
    }

    public List<Paiement> trierMesPaiementsParDate(int idAgent, boolean croissant) {
        List<Paiement> paiements = paiementService.getHistoriquePaiements(idAgent);
        return paiementService.sortPaiementsByDate(paiements, croissant);
    }


    public BigDecimal calculerMonTotalPaiements(int idAgent) {
        return paiementService.calculerTotalPaiements(idAgent);
    }


    public BigDecimal calculerMonTotalPaiementsParType(int idAgent, TypePaiement typePaiement) {
        return paiementService.calculerTotalPaiementsByType(idAgent, typePaiement);
    }


    public BigDecimal calculerMonTotalPaiementsParPeriode(int idAgent, LocalDate dateDebut, LocalDate dateFin) {
        return paiementService.calculerTotalPaiementsByPeriode(idAgent, dateDebut, dateFin);
    }



    public void ajouterSalaire(int idAgent, BigDecimal montant, String motif) {
        try {

            System.out.println("Salaire ajouté pour l'agent " + idAgent + ": " + montant + "€ - " + motif);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du salaire : " + e.getMessage());
        }
    }


    public void ajouterPrime(int idAgent, BigDecimal montant, String motif) {
        try {

            System.out.println("Prime ajoutée pour l'agent " + idAgent + ": " + montant + "€ - " + motif);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de la prime : " + e.getMessage());
        }
    }


    public void ajouterBonus(int idAgent, BigDecimal montant, String motif) {
        try {

            System.out.println("Bonus ajouté pour l'agent " + idAgent + ": " + montant + "€ - " + motif);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du bonus : " + e.getMessage());
        }
    }


    public void ajouterIndemnite(int idAgent, BigDecimal montant, String motif) {
        try {

            System.out.println("Indemnité ajoutée pour l'agent " + idAgent + ": " + montant + "€ - " + motif);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'indemnité : " + e.getMessage());
        }
    }


    public List<Paiement> consulterPaiementsAgent(int idAgent) {
        return paiementService.getHistoriquePaiements(idAgent);
    }


    public List<Paiement> filtrerPaiements(int idDepartement, TypePaiement typePaiement,
                                           LocalDate dateDebut, LocalDate dateFin) {
        try {
            System.out.println("Filtrage des paiements par département non encore implémenté");
            return List.of();
        } catch (Exception e) {
            System.err.println("Erreur lors du filtrage des paiements : " + e.getMessage());
            return List.of();
        }
    }
}
