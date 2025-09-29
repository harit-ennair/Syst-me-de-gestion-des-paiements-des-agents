import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.AgentDAO;
import dao.DepartementDAO;
import dao.PaiementDAO;
import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
import util.DatabaseConnection;
import view.AgentConsoleView;


public class Main {
    public static void main(String[] args) {
//Agent agent = new Agent("harit","ennair","fjkbedvev","12345", TypeAgent.DIRECTEUR,0);
//Agent agent1 = new Agent("haryhyit","enthnair","fjkbenydvev","123th45", TypeAgent.DIRECTEUR,0);
//Agent agent2 = new Agent("hariytbt","ennzrthair","fjkbjyedvev","12srn345", TypeAgent.DIRECTEUR,0);
//Agent agent3 = new Agent("hariubtjt","ennjzair","fjkbzedvev","123rsn45", TypeAgent.DIRECTEUR,0);
//Agent agent4 = new Agent("harbtjt","ennjzair","fjkbzthedvev","123rsn45", TypeAgent.DIRECTEUR,0);
//Agent agent5 = new Agent("harityjbt","ennujair","fjkbhjyedvev","123sn45", TypeAgent.DIRECTEUR,0);
//AgentDAO agentDAO = new AgentDAO();

            //agentDAO.addAgent(agent);
            //agentDAO.addAgent(agent1);
            //agentDAO.addAgent(agent2);
            //agentDAO.addAgent(agent3);
            //agentDAO.addAgent(agent4);
            //agentDAO.addAgent(agent5);
            //agentDAO.getAllAgents().forEach(System.out::println);

//        Departement departement = new Departement("Informatique");
//        Departement departement1 = new Departement("Ressources Humaines");
//        Departement departement2 = new Departement("Finances");
//        Departement departement3 = new Departement("Ingénierie");
//        Departement departement4 = new Departement("Marketing");

//        DepartementDAO departementDAO = new DepartementDAO();
//        departementDAO.addDepartement(departement);
//        departementDAO.addDepartement(departement1);
//        departementDAO.addDepartement(departement2);
//        departementDAO.addDepartement(departement3);
//        departementDAO.addDepartement(departement4);

//        departementDAO.getAllDepartements().forEach(System.out::println);
//        System.out.printf( departementDAO.getDepartementById(4));
//
//        Paiement paiement = new Paiement(TypePaiement.BONUS, BigDecimal.valueOf(74847777), LocalDate.now(), "Achat matériel", true, 2);
//        Paiement paiement1 = new Paiement(TypePaiement.PRIME, BigDecimal.valueOf(9849), LocalDate.now(), "Frais de déplacement", true, 2);
//        Paiement paiement2 = new Paiement(TypePaiement.SALAIRE, BigDecimal.valueOf(894), LocalDate.now(), "Paiement fournisseur", false, 2);
//        Paiement paiement3 = new Paiement(TypePaiement.INDEMNITE, BigDecimal.valueOf(845), LocalDate.now(), "Frais divers", true, 2);
//        Paiement paiement4 = new Paiement(TypePaiement.SALAIRE, BigDecimal.valueOf(1254), LocalDate.now(), "Remboursement", false, 2);

//        PaiementDAO paiementDAO = new PaiementDAO();
//        paiementDAO.addPaiement(paiement);
//        paiementDAO.addPaiement(paiement1);
//        paiementDAO.addPaiement(paiement2);
//        paiementDAO.addPaiement(paiement3);
//        paiementDAO.addPaiement(paiement4);

//        System.out.println(paiementDAO.getPaiementById(2));

//          paiementDAO.updatePaiement(TypePaiement.PRIME, BigDecimal.valueOf(9849), LocalDate.now(), "Frais de déplacement", true, 2,8 );
//        Paiement paiementToUpdate = new Paiement();
//        paiementToUpdate.setIdPaiement(2);
//        paiementToUpdate.setTypePaiement(TypePaiement.INDEMNITE);
//        paiementToUpdate.setMontant(BigDecimal.valueOf(1500));
//        paiementToUpdate.setDatePaiement(LocalDate.now());
//        paiementToUpdate.setMotif("Updated Motif");
//        paiementToUpdate.setConditionValidee(true);
//        paiementToUpdate.setIdAgent(2);
//
//
//
//        paiementDAO.updatePaiement(paiementToUpdate);

        AgentConsoleView view = new AgentConsoleView();
        view.afficherMenuAgent(2);
//
//        AgentDAO dao = new AgentDAO();
//        Agent agent = dao.getAgentWithDepartement(4);

    }
}
