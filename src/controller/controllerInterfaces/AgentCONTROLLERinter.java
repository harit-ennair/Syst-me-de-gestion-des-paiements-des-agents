package controller.controllerInterfaces;

import model.Agent;
import model.Paiement;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AgentCONTROLLERinter {

    // Consultation des informations de l'agent
    Agent consulterMesInformations(int idAgent);
    Agent consulterMesInformationsAvecDepartement(int idAgent);

    // Gestion de l'historique des paiements
    List<Paiement> voirMonHistoriquePaiements(int idAgent);

    // Filtrage des paiements
    List<Paiement> filtrerMesPaiementsParType(int idAgent, TypePaiement typePaiement);
    List<Paiement> filtrerMesPaiementsParMontant(int idAgent, BigDecimal montantMin, BigDecimal montantMax);
    List<Paiement> filtrerMesPaiementsParDate(int idAgent, LocalDate dateDebut, LocalDate dateFin);

    // Tri des paiements
    List<Paiement> trierMesPaiementsParMontant(int idAgent, boolean croissant);
    List<Paiement> trierMesPaiementsParDate(int idAgent, boolean croissant);

    // Calculs de totaux
    BigDecimal calculerMonTotalPaiements(int idAgent);
    BigDecimal calculerMonTotalPaiementsParType(int idAgent, TypePaiement typePaiement);
    BigDecimal calculerMonTotalPaiementsParPeriode(int idAgent, LocalDate dateDebut, LocalDate dateFin);

    // Affichage
    void afficherResumeAgent(int idAgent);
}
