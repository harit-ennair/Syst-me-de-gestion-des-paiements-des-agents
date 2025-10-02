package controller.controllerInterfaces;

import model.Agent;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ResponsablesCONTROLLERinter {

    // Gestion des départements (Directeur)
    void creerDepartement(String nom, String description);
    void modifierDepartement(int idDepartement, String nom, String description);
    void supprimerDepartement(int idDepartement);
    void associerResponsable(int idDepartement, int idResponsable);

    // Gestion des agents (Responsable)
    void ajouterAgent(String nom, String prenom, String email, String password,
                     TypeAgent typeAgent, int idDepartement);
    void modifierAgent(int idAgent, String nom, String prenom, String email,
                      TypeAgent typeAgent, int idDepartement);
    void supprimerAgent(int idAgent);
    void affecterAgentDepartement(int idAgent, int idDepartement);

    // Gestion des paiements (Responsable)
    void ajouterSalaire(int idAgent, BigDecimal montant, String motif);
    void ajouterPrime(int idAgent, BigDecimal montant, String motif);
    void ajouterBonus(int idAgent, BigDecimal montant, String motif);
    void ajouterIndemnite(int idAgent, BigDecimal montant, String motif);

    // Consultation et filtrage
    List<Paiement> consulterPaiementsAgent(int idAgent);
    List<Paiement> consulterPaiementsDepartement(int idDepartement);
    List<Paiement> filtrerPaiements(int idDepartement, TypePaiement typePaiement,
                                   LocalDate dateDebut, LocalDate dateFin);
    List<Paiement> identifierPaiementsInhabituels(int idDepartement);

    // Utilitaires
    List<Agent> obtenirAgentsDepartement(int idDepartement);
    boolean verifierEligibiliteBonus(int idAgent);
    boolean verifierEligibiliteIndemnite(int idAgent);
}
