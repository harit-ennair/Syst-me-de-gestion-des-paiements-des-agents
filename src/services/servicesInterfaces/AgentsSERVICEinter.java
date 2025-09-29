package services.servicesInterfaces;

import model.Agent;
import model.Paiement;
import model.enums.TypePaiement;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

public interface AgentsSERVICEinter {

    // Consultation des informations personnelles et département
    Agent getAgentById(int idAgent);
    Agent getAgentWithDepartement(int idAgent);

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
}
