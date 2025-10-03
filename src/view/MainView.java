package view;

import java.util.Scanner;
import controller.AgentController;
import controller.DepartementController;
import controller.PaiementController;
import model.Agent;
import model.enums.TypeAgent;

public class MainView {
    private Scanner scanner;
    private AgentView agentView;
    private ResponsableView responsableView;
    private DirecteurView directeurView;

    // Controllers instead of direct services
    private AgentController agentController;
    private DepartementController departementController;
    private PaiementController paiementController;

    public MainView() {
        this.scanner = new Scanner(System.in);
        this.agentView = new AgentView();
        this.responsableView = new ResponsableView();
        this.directeurView = new DirecteurView();

        // Initialize controllers
        this.agentController = new AgentController();
        this.departementController = new DepartementController();
        this.paiementController = new PaiementController();
    }

    public void afficherMenuPrincipal() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("   SYSTÈME DE GESTION DES PAIEMENTS");
            System.out.println("=".repeat(50));
            System.out.println("1. Se connecter");
            System.out.println("0. Quitter");
            System.out.println("=".repeat(50));
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    gererConnexion();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void gererConnexion() {
        System.out.println("\n=== CONNEXION ===");
        System.out.print("Entrez votre email: ");
        String email = scanner.nextLine();
        System.out.print("Entrez votre password: ");
        String password = scanner.nextLine();

        // Authentifier l'utilisateur via le controller
        Agent agent = agentController.authentifierAgent(email, password);

        if (agent != null) {
            System.out.println("Connexion réussie ! Bienvenue " + agent.getPrenom() + " " + agent.getNom());

            // Redirection automatique basée sur le rôle
            redirectionAutomatique(agent);
        } else {
            System.out.println("Échec de la connexion. Nom ou password incorrect.");
            System.out.println("Veuillez vérifier vos informations et réessayer.");
        }
    }

    private void redirectionAutomatique(Agent agent) {
        TypeAgent typeAgent = agent.getTypeAgent();

        switch (typeAgent) {
            case OUVRIER:
            case PERMANENT:
            case STAGIAIRE:
                System.out.println("Redirection vers l'interface Agent...");
                agentView.afficherMenuAgent(agent.getIdAgent());
                break;

            case RESPONSABLE_DEPARTEMENT:
                System.out.println("Redirection vers l'interface Responsable de Département...");
                responsableView.afficherMenuResponsable(agent.getIdAgent());
                break;

            case DIRECTEUR:
                System.out.println("Redirection vers l'interface Directeur...");
                directeurView.afficherMenuDirecteur(agent.getIdAgent());
                break;

            default:
                System.out.println("Type d'agent non reconnu. Redirection vers l'interface Agent par défaut...");
                agentView.afficherMenuAgent(agent.getIdAgent());
        }
    }

    private int lireEntier() {
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre valide: ");
            scanner.next();
        }
        int valeur = scanner.nextInt();
        scanner.nextLine();
        return valeur;
    }

    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.afficherMenuPrincipal();
    }


}
