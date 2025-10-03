package controller.controllerInterfaces;

import model.Paiement;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaiementCONTROLLERinter {

    List<Paiement> voirMonHistoriquePaiements(int idAgent);

    List<Paiement> filtrerMesPaiementsParType(int idAgent, TypePaiement typePaiement);
    List<Paiement> filtrerMesPaiementsParMontant(int idAgent, BigDecimal montantMin, BigDecimal montantMax);
    List<Paiement> filtrerMesPaiementsParDate(int idAgent, LocalDate dateDebut, LocalDate dateFin);

    List<Paiement> trierMesPaiementsParMontant(int idAgent, boolean croissant);
    List<Paiement> trierMesPaiementsParDate(int idAgent, boolean croissant);

    BigDecimal calculerMonTotalPaiements(int idAgent);
    BigDecimal calculerMonTotalPaiementsParType(int idAgent, TypePaiement typePaiement);
    BigDecimal calculerMonTotalPaiementsParPeriode(int idAgent, LocalDate dateDebut, LocalDate dateFin);


    void ajouterSalaire(int idAgent, BigDecimal montant, String motif);
    void ajouterPrime(int idAgent, BigDecimal montant, String motif);
    void ajouterBonus(int idAgent, BigDecimal montant, String motif);
    void ajouterIndemnite(int idAgent, BigDecimal montant, String motif);

    List<Paiement> consulterPaiementsAgent(int idAgent);
    List<Paiement> filtrerPaiements(int idDepartement, TypePaiement typePaiement, LocalDate dateDebut, LocalDate dateFin);
//    List<Paiement> consulterPaiementsDepartement(int idDepartement);
//    List<Paiement> identifierPaiementsInhabituels(int idDepartement);


}
