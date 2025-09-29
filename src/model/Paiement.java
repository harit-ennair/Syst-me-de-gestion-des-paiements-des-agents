package model;

import model.enums.TypePaiement;
import model.enums.TypeAgent;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Paiement {
    private int idPaiement;
    private TypePaiement typePaiement;
    private BigDecimal montant;
    private LocalDate datePaiement;
    private String motif;
    private boolean conditionValidee;
    private int idAgent;
    private Agent agent; // Référence à l'agent

    // Constructeur par défaut
    public Paiement() {}

    // Constructeur avec paramètres de base
    public Paiement(TypePaiement typePaiement, BigDecimal montant, LocalDate datePaiement,
                   String motif, boolean conditionValidee, int idAgent) {
        this.typePaiement = typePaiement;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.motif = motif;
        this.conditionValidee = conditionValidee;
        this.idAgent = idAgent;
    }



    // Getters et Setters
    public int getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(int idPaiement) {
        this.idPaiement = idPaiement;
    }

    public String getTypePaiement() {
        return typePaiement.name();
    }

    public void setTypePaiement(TypePaiement typePaiement) {
        this.typePaiement = typePaiement;
    }

    public double getMontant() {
        return montant.doubleValue();
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public boolean isConditionValidee() {
        return conditionValidee;
    }

    public void setConditionValidee(boolean conditionValidee) {
        this.conditionValidee = conditionValidee;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
        if (agent != null) {
            this.idAgent = agent.getIdAgent();
        }
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "idPaiement=" + idPaiement +
                ", typePaiement=" + typePaiement +
                ", montant=" + montant +
                ", datePaiement=" + datePaiement +
                ", motif='" + motif + '\'' +
                ", conditionValidee=" + conditionValidee +
                ", idAgent=" + idAgent +
                (agent != null ? ", agent='" + agent.getNom() + " " + agent.getPrenom() + '\'' : "") +
                '}';
    }
}
