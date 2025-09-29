package services;

import dao.AgentDAO;
import dao.PaiementDAO;
import model.Agent;
import model.Paiement;
import model.enums.TypePaiement;
import services.servicesInterfaces.AgentsSERVICEinter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AgentsSERVICE implements AgentsSERVICEinter {

    private AgentDAO agentDAO;
    private PaiementDAO paiementDAO;

    public AgentsSERVICE() {
        this.agentDAO = new AgentDAO();
        this.paiementDAO = new PaiementDAO();
    }

    @Override
    public Agent getAgentById(int idAgent) {
        return agentDAO.getAgentById(idAgent);
    }

    @Override
    public Agent getAgentWithDepartement(int idAgent) {
        return agentDAO.getAgentWithDepartement(idAgent);
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
}
