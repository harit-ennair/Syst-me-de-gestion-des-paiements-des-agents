package services.servicesInterfaces;

import model.Agent;
import model.Paiement;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaiementSERVICEinter {

    // Historique des paiements
    List<Paiement> getHistoriquePaiements(int idAgent);

    // Filtrage et tri des paiements
    List<Paiement> filterPaiementsByType(int idAgent, TypePaiement typePaiement);
    List<Paiement> filterPaiementsByMontant(int idAgent, BigDecimal montantMin, BigDecimal montantMax);
    List<Paiement> filterPaiementsByDate(int idAgent, LocalDate dateDebut, LocalDate dateFin);
    List<Paiement> sortPaiementsByMontant(List<Paiement> paiements, boolean croissant);
    List<Paiement> sortPaiementsByDate(List<Paiement> paiements, boolean croissant);

    // Calcul du total des paiements
    BigDecimal calculerTotalPaiements(int idAgent);
    BigDecimal calculerTotalPaiementsByType(int idAgent, TypePaiement typePaiement);
    BigDecimal calculerTotalPaiementsByPeriode(int idAgent, LocalDate dateDebut, LocalDate dateFin);

    // Gestion des paiements (pour responsable)
    void ajouterPaiement(int idAgent, TypePaiement typePaiement, BigDecimal montant,
                         String motif) throws Exception;
    void ajouterSalaire(int idAgent, BigDecimal montant, String motif) throws Exception;
    void ajouterPrime(int idAgent, BigDecimal montant, String motif) throws Exception;
    void ajouterBonus(int idAgent, BigDecimal montant, String motif) throws Exception;
    void ajouterIndemnite(int idAgent, BigDecimal montant, String motif) throws Exception;

    // Consultation et filtrage des paiements
    List<Paiement> consulterPaiementsAgent(int idAgent) throws Exception;
    List<Paiement> consulterPaiementsDepartement(int idDepartement) throws Exception;
    List<Paiement> filtrerPaiements(int idDepartement, TypePaiement typePaiement,
                                    LocalDate dateDebut, LocalDate dateFin) throws Exception;
    List<Paiement> identifierPaiementsInhabituels(int idDepartement) throws Exception;

    // Méthodes
    boolean verifierEligibiliteBonus(int idAgent) throws Exception;
    boolean verifierEligibiliteIndemnite(int idAgent) throws Exception;
    boolean validerConditionPaiement(int idAgent, TypePaiement typePaiement) throws Exception;

}
