package view;

import java.util.Scanner;

public class MainView {
    private Scanner scanner;
    private AgentView agentView;
    private ResponsableView responsableView;
    private DirecteurView directeurView;

    public MainView() {
        this.scanner = new Scanner(System.in);
        this.agentView = new AgentView();
        this.responsableView = new ResponsableView();
        this.directeurView = new DirecteurView();
    }

    public void afficherMenuPrincipal() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("   SYSTÈME DE GESTION DES PAIEMENTS");
            System.out.println("=".repeat(50));
            System.out.println("1. Interface Agent");
            System.out.println("2. Interface Responsable de Département");
            System.out.println("3. Interface Directeur");
            System.out.println("0. Quitter");
            System.out.println("=".repeat(50));
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    gererConnexionAgent();
                    break;
                case 2:
                    gererConnexionResponsable();
                    break;
                case 3:
                    gererConnexionDirecteur();
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

    private void gererConnexionAgent() {
        System.out.println("\n=== CONNEXION AGENT ===");
        System.out.print("Entrez votre ID d'agent: ");
        int idAgent = lireEntier();


        agentView.afficherMenuAgent(idAgent);
    }

    private void gererConnexionResponsable() {
        System.out.println("\n=== CONNEXION RESPONSABLE ===");
        System.out.print("Entrez votre ID de responsable: ");
        int idResponsable = lireEntier();

        responsableView.afficherMenuResponsable(idResponsable);
    }

    private void gererConnexionDirecteur() {
        System.out.println("\n=== CONNEXION DIRECTEUR ===");
        System.out.print("Entrez votre ID de directeur: ");
        int idDirecteur = lireEntier();

        directeurView.afficherMenuDirecteur(idDirecteur);
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
