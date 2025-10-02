package services.servicesInterfaces;

import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ResponsablesSERVICEinter {

    // Gestion des départements (pour directeur)
    void creerDepartement(String nom, String description) throws Exception;
    void modifierDepartement(int idDepartement, String nom, String description) throws Exception;
    void supprimerDepartement(int idDepartement) throws Exception;
    void associerResponsable(int idDepartement, int idResponsable) throws Exception;

    // Gestion des agents (pour responsable)
    void ajouterAgent(String nom, String prenom, String email, String password,
                     TypeAgent typeAgent, int idDepartement) throws Exception;
    void modifierAgent(int idAgent, String nom, String prenom, String email,
                      TypeAgent typeAgent, int idDepartement) throws Exception;
    void supprimerAgent(int idAgent) throws Exception;
    void affecterAgentDepartement(int idAgent, int idDepartement) throws Exception;

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

    // Méthodes utilitaires
    boolean verifierEligibiliteBonus(int idAgent) throws Exception;
    boolean verifierEligibiliteIndemnite(int idAgent) throws Exception;
    boolean validerConditionPaiement(int idAgent, TypePaiement typePaiement) throws Exception;
    List<Agent> obtenirAgentsDepartement(int idDepartement) throws Exception;
}
