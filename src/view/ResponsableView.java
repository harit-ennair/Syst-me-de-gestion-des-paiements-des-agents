package view;

import controller.AgentController;
import controller.DepartementController;
import controller.PaiementController;
import model.Agent;
import model.Departement;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ResponsableView {
    private Scanner scanner;
    private AgentController agentController;
    private PaiementController paiementController;
    private DepartementController departementController;

    public ResponsableView() {
        this.scanner = new Scanner(System.in);
        this.agentController = new AgentController();
        this.paiementController = new PaiementController();
        this.departementController = new DepartementController();
    }

    public void afficherMenuResponsable(int idResponsable) {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("     INTERFACE RESPONSABLE DE DÉPARTEMENT");
            System.out.println("=".repeat(50));
            System.out.println("1. Gestion des agents");
            System.out.println("2. Gestion des paiements");
            System.out.println("3. Consulter département");
            System.out.println("4. Rapports et statistiques");
            System.out.println("0. Retour au menu principal");
            System.out.println("=".repeat(50));
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    gererAgents(idResponsable);
                    break;
                case 2:
                    gererPaiements(idResponsable);
                    break;
                case 3:
                    consulterDepartement(idResponsable);
                    break;
                case 4:
                    afficherRapports(idResponsable);
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void gererAgents(int idResponsable) {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== GESTION DES AGENTS ===");
            System.out.println("1. Ajouter un agent");
            System.out.println("2. Modifier un agent");
            System.out.println("3. Supprimer un agent");
            System.out.println("4. Affecter un agent à un département");
            System.out.println("5. Consulter les informations d'un agent");
            System.out.println("6. Lister tous les agents du département");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    ajouterAgent();
                    break;
                case 2:
                    modifierAgent();
                    break;
                case 3:
                    supprimerAgent();
                    break;
                case 4:
                    affecterAgentDepartement();
                    break;
                case 5:
                    consulterAgent();
                    break;
                case 6:
                    listerAgentsDepartement(idResponsable);
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private void ajouterAgent() {
        System.out.println("\n=== AJOUTER UN AGENT ===");
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();

        System.out.println("Types d'agent disponibles:");
        TypeAgent[] types = TypeAgent.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        System.out.print("Choisissez un type (1-" + types.length + "): ");
        int choixType = lireEntier();

        if (choixType < 1 || choixType > types.length) {
            System.out.println("Type invalide.");
            return;
        }

        TypeAgent typeAgent = types[choixType - 1];

        System.out.print("ID du département: ");
        int idDepartement = lireEntier();

        try {
            agentController.ajouterAgent(nom, prenom, email, password, typeAgent, idDepartement);
            System.out.println("Agent ajouté avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout: " + e.getMessage());
        }
    }

    private void modifierAgent() {
        System.out.println("\n=== MODIFIER UN AGENT ===");
        System.out.print("ID de l'agent à modifier: ");
        int idAgent = lireEntier();

        System.out.print("Nouveau nom: ");
        String nom = scanner.nextLine();
        System.out.print("Nouveau prénom: ");
        String prenom = scanner.nextLine();
        System.out.print("Nouvel email: ");
        String email = scanner.nextLine();

        System.out.println("Types d'agent disponibles:");
        TypeAgent[] types = TypeAgent.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        System.out.print("Choisissez un type (1-" + types.length + "): ");
        int choixType = lireEntier();

        if (choixType < 1 || choixType > types.length) {
            System.out.println("Type invalide.");
            return;
        }

        TypeAgent typeAgent = types[choixType - 1];

        System.out.print("Nouveau ID du département: ");
        int idDepartement = lireEntier();

        try {
            agentController.modifierAgent(idAgent, nom, prenom, email, typeAgent, idDepartement);
            System.out.println("Agent modifié avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }

    private void supprimerAgent() {
        System.out.println("\n=== SUPPRIMER UN AGENT ===");
        System.out.print("ID de l'agent à supprimer: ");
        int idAgent = lireEntier();

        System.out.print("Êtes-vous sûr de vouloir supprimer cet agent? (o/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("o") || confirmation.equalsIgnoreCase("oui")) {
            try {
                agentController.supprimerAgent(idAgent);
                System.out.println("Agent supprimé avec succès!");
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression: " + e.getMessage());
            }
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    private void affecterAgentDepartement() {
        System.out.println("\n=== AFFECTER AGENT À DÉPARTEMENT ===");
        System.out.print("ID de l'agent: ");
        int idAgent = lireEntier();
        System.out.print("Nouvel ID du département: ");
        int idDepartement = lireEntier();

        try {
            agentController.affecterAgentDepartement(idAgent, idDepartement);
            System.out.println("Agent affecté avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'affectation: " + e.getMessage());
        }
    }

    private void consulterAgent() {
        System.out.println("\n=== CONSULTER UN AGENT ===");
        System.out.print("ID de l'agent: ");
        int idAgent = lireEntier();

        agentController.afficherResumeAgent(idAgent);
    }

    private void listerAgentsDepartement(int idResponsable) {
        System.out.println("\n=== AGENTS DU DÉPARTEMENT ===");
        // Cette méthode nécessiterait d'être implémentée dans le contrôleur
        System.out.println("Fonctionnalité à implémenter: lister les agents du département");
    }

    private void gererPaiements(int idResponsable) {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== GESTION DES PAIEMENTS ===");
            System.out.println("1. Ajouter un salaire");
            System.out.println("2. Ajouter une prime");
            System.out.println("3. Ajouter un bonus");
            System.out.println("4. Ajouter une indemnité");
            System.out.println("5. Consulter paiements d'un agent");
            System.out.println("6. Historique des paiements du département");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    ajouterSalaire();
                    break;
                case 2:
                    ajouterPrime();
                    break;
                case 3:
                    ajouterBonus();
                    break;
                case 4:
                    ajouterIndemnite();
                    break;
                case 5:
                    consulterPaiementsAgent();
                    break;
                case 6:
                    consulterPaiementsDepartement(idResponsable);
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private void ajouterSalaire() {
        System.out.println("\n=== AJOUTER UN SALAIRE ===");
        System.out.print("ID de l'agent: ");
        int idAgent = lireEntier();
        System.out.print("Montant: ");
        BigDecimal montant = lireBigDecimal();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();

        try {
            paiementController.ajouterSalaire(idAgent, montant, motif);
            System.out.println("Salaire ajouté avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void ajouterPrime() {
        System.out.println("\n=== AJOUTER UNE PRIME ===");
        System.out.print("ID de l'agent: ");
        int idAgent = lireEntier();
        System.out.print("Montant: ");
        BigDecimal montant = lireBigDecimal();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();

        try {
            paiementController.ajouterPrime(idAgent, montant, motif);
            System.out.println("Prime ajoutée avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void ajouterBonus() {
        System.out.println("\n=== AJOUTER UN BONUS ===");
        System.out.print("ID de l'agent: ");
        int idAgent = lireEntier();
        System.out.print("Montant: ");
        BigDecimal montant = lireBigDecimal();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();

        try {
            paiementController.ajouterBonus(idAgent, montant, motif);
            System.out.println("Bonus ajouté avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void ajouterIndemnite() {
        System.out.println("\n=== AJOUTER UNE INDEMNITÉ ===");
        System.out.print("ID de l'agent: ");
        int idAgent = lireEntier();
        System.out.print("Montant: ");
        BigDecimal montant = lireBigDecimal();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();

        try {
            paiementController.ajouterIndemnite(idAgent, montant, motif);
            System.out.println("Indemnité ajoutée avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void consulterPaiementsAgent() {
        System.out.println("\n=== CONSULTER PAIEMENTS D'UN AGENT ===");
        System.out.print("ID de l'agent: ");
        int idAgent = lireEntier();

        List<Paiement> paiements = paiementController.consulterPaiementsAgent(idAgent);
        afficherListePaiements(paiements);
    }

    private void consulterPaiementsDepartement(int idResponsable) {
        System.out.println("\n=== PAIEMENTS DU DÉPARTEMENT ===");
        // Cette fonctionnalité nécessiterait d'être implémentée
        System.out.println("Fonctionnalité à implémenter: consulter tous les paiements du département");
    }

    private void consulterDepartement(int idResponsable) {
        System.out.println("\n=== INFORMATIONS DU DÉPARTEMENT ===");
        // Cette fonctionnalité nécessiterait d'être implémentée dans le contrôleur
        System.out.println("Fonctionnalité à implémenter: consulter les informations du département");
    }

    private void afficherRapports(int idResponsable) {
        System.out.println("\n=== RAPPORTS ET STATISTIQUES ===");
        System.out.println("1. Total des paiements du département");
        System.out.println("2. Statistiques par type de paiement");
        System.out.println("3. Rapport mensuel");
        System.out.println("4. Agents les mieux payés");
        System.out.print("Votre choix: ");

        int choix = lireEntier();
        switch (choix) {
            case 1:
            case 2:
            case 3:
            case 4:
                System.out.println("Fonctionnalité à implémenter: " + choix);
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void afficherListePaiements(List<Paiement> paiements) {
        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            return;
        }

        System.out.println("-".repeat(80));
        System.out.printf("%-5s %-12s %-12s %-12s %-20s%n",
                         "ID", "Type", "Montant", "Date", "Motif");
        System.out.println("-".repeat(80));

        for (Paiement paiement : paiements) {
            System.out.printf("%-5d %-12s %-12.2f %-12s %-20s%n",
                             paiement.getIdPaiement(),
                             paiement.getTypePaiement(),
                             paiement.getMontant(),
                             paiement.getDatePaiement(),
                             paiement.getMotif() != null ? paiement.getMotif() : "N/A");
        }
        System.out.println("-".repeat(80));
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

    private BigDecimal lireBigDecimal() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.print("Veuillez entrer un montant valide: ");
            }
        }
    }
}
