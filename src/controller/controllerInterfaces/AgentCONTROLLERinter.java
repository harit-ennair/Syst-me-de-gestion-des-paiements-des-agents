package controller.controllerInterfaces;

import model.Agent;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AgentCONTROLLERinter {


    Agent consulterMesInformations(int idAgent);
    Agent consulterMesInformationsAvecDepartement(int idAgent);
    Agent authentifierAgent(String email, String password);


    void ajouterAgent(String nom, String prenom, String email, String password,
                      TypeAgent typeAgent, int idDepartement);
    void modifierAgent(int idAgent, String nom, String prenom, String email,
                       TypeAgent typeAgent, int idDepartement);
    void supprimerAgent(int idAgent);
    void affecterAgentDepartement(int idAgent, int idDepartement);


    void afficherResumeAgent(int idAgent);
}
