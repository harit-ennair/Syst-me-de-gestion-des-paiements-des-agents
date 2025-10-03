package controller.controllerInterfaces;

import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DepartementCONTROLLERinter {

    void creerDepartement(String nom, String description);
    void modifierDepartement(int idDepartement, String nom, String description);
    void supprimerDepartement(int idDepartement);
    void associerResponsable(int idDepartement, int idResponsable);

    List<Departement> afficherTousLesDepartements();
    List<Agent> obtenirAgentsDepartement(int idDepartement);
    boolean verifierEligibiliteBonus(int idAgent);
    boolean verifierEligibiliteIndemnite(int idAgent);
}
