package services.servicesInterfaces;

import model.Agent;
import model.Departement;

import java.util.List;

public interface DepartementSERVICEinter {

    void creerDepartement(String nom, String description) throws Exception;
    void modifierDepartement(int idDepartement, String nom, String description) throws Exception;
    void supprimerDepartement(int idDepartement) throws Exception;
    void associerResponsable(int idDepartement, int idResponsable) throws Exception;

    List<Departement> getAllDepartements() throws Exception;
    List<Agent> obtenirAgentsDepartement(int idDepartement) throws Exception;
    boolean verifierEligibiliteBonus(int idAgent) throws Exception;
    boolean verifierEligibiliteIndemnite(int idAgent) throws Exception;
}
