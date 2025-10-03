package services.servicesInterfaces;

import model.Agent;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

public interface AgentsSERVICEinter {


    Agent getAgentById(int idAgent);
    Agent getAgentWithDepartement(int idAgent);
    Agent authenticateAgent(String nom, String email);

    void ajouterAgent(String nom, String prenom, String email, String password, TypeAgent typeAgent, int idDepartement) throws Exception;
    void modifierAgent(int idAgent, String nom, String prenom, String email, TypeAgent typeAgent, int idDepartement) throws Exception;
    void supprimerAgent(int idAgent) throws Exception;
    void affecterAgentDepartement(int idAgent, int idDepartement) throws Exception;


}
