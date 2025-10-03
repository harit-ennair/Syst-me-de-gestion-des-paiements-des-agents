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

public class DirecteurView {
    private Scanner scanner;
    private AgentController agentController;
    private PaiementController paiementController;
    private DepartementController departementController;

    public DirecteurView() {
        this.scanner = new Scanner(System.in);
        this.agentController = new AgentController();
        this.paiementController = new PaiementController();
        this.departementController = new DepartementController();
    }

    public void afficherMenuDirecteur(int idDirecteur) {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("              INTERFACE DIRECTEUR");
            System.out.println("=".repeat(60));
            System.out.println("1. Gestion globale des agents");
            System.out.println("2. Gestion des départements");
            System.out.println("3. Gestion globale des paiements");
            System.out.println("4. Rapports et analyses");
            System.out.println("5. Statistiques globales");
            System.out.println("6. Contrôle et validation");
            System.out.println("0. Retour au menu principal");
            System.out.println("=".repeat(60));
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    gestionGlobaleAgents();
                    break;
                case 2:
                    gestionDepartements();
                    break;
                case 3:
                    gestionGlobalePaiements();
                    break;
                case 4:
                    rapportsEtAnalyses();
                    break;
                case 5:
                    statistiquesGlobales();
                    break;
                case 6:
                    controleEtValidation();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void gestionGlobaleAgents() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== GESTION GLOBALE DES AGENTS ===");
            System.out.println("1. Voir tous les agents de l'entreprise");
            System.out.println("2. Ajouter un agent (tous départements)");
            System.out.println("3. Modifier un agent");
            System.out.println("4. Supprimer un agent");
            System.out.println("5. Transférer un agent entre départements");
            System.out.println("6. Promouvoir/Rétrograder un agent");
            System.out.println("7. Rechercher un agent");
            System.out.println("8. Agents par type");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    voirTousLesAgents();
                    break;
                case 2:
                    ajouterAgentGlobal();
                    break;
                case 3:
                    modifierAgentGlobal();
                    break;
                case 4:
                    supprimerAgentGlobal();
                    break;
                case 5:
                    transfererAgent();
                    break;
                case 6:
                    gererPromotionAgent();
                    break;
                case 7:
                    rechercherAgent();
                    break;
                case 8:
                    afficherAgentsParType();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private void gestionDepartements() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== GESTION DES DÉPARTEMENTS ===");
            System.out.println("1. Créer un nouveau département");
            System.out.println("2. Modifier un département");
            System.out.println("3. Supprimer un département");
            System.out.println("4. Voir tous les départements");
            System.out.println("5. Statistiques par département");
            System.out.println("6. Affecter un responsable");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    creerDepartement();
                    break;
                case 2:
                    modifierDepartement();
                    break;
                case 3:
                    supprimerDepartement();
                    break;
                case 4:
                    voirTousLesDepartements();
                    break;
                case 5:
                    statistiquesParDepartement();
                    break;
                case 6:
                    affecterResponsable();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private void gestionGlobalePaiements() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== GESTION GLOBALE DES PAIEMENTS ===");
            System.out.println("1. Approuver/Rejeter des paiements en attente");
            System.out.println("2. Historique complet des paiements");
            System.out.println("3. Paiements par département");
            System.out.println("4. Paiements exceptionnels (gros montants)");
            System.out.println("5. Ajouter un paiement spécial");
            System.out.println("6. Annuler un paiement");
            System.out.println("7. Audit des paiements");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    gererApprobationsPaiements();
                    break;
                case 2:
                    historiqueCompletPaiements();
                    break;
                case 3:
                    paiementsParDepartement();
                    break;
                case 4:
                    paiementsExceptionnels();
                    break;
                case 5:
                    ajouterPaiementSpecial();
                    break;
                case 6:
                    annulerPaiement();
                    break;
                case 7:
                    auditPaiements();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private void rapportsEtAnalyses() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== RAPPORTS ET ANALYSES ===");
            System.out.println("1. Rapport mensuel global");
            System.out.println("2. Rapport annuel");
            System.out.println("3. Comparaison inter-départements");
            System.out.println("4. Évolution des coûts salariaux");
            System.out.println("5. Analyse des primes et bonus");
            System.out.println("6. Rapport de performance par agent");
            System.out.println("7. Prévisions budgétaires");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    rapportMensuelGlobal();
                    break;
                case 2:
                    rapportAnnuel();
                    break;
                case 3:
                    comparaisonInterDepartements();
                    break;
                case 4:
                    evolutionCoutsSalariaux();
                    break;
                case 5:
                    analysePrimesBonus();
                    break;
                case 6:
                    rapportPerformanceAgent();
                    break;
                case 7:
                    previsionsBudgetaires();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private void statistiquesGlobales() {
        System.out.println("\n=== STATISTIQUES GLOBALES ===");
        System.out.println("Génération des statistiques en cours...");

        // Simulation de statistiques (à implémenter avec les vraies données)
        System.out.println("\n📊 TABLEAU DE BORD EXÉCUTIF");
        System.out.println("-".repeat(50));
        System.out.println("• Nombre total d'agents: [À calculer]");
        System.out.println("• Nombre de départements: [À calculer]");
        System.out.println("• Total des paiements ce mois: [À calculer] €");
        System.out.println("• Salaire moyen: [À calculer] €");
        System.out.println("• Budget alloué aux primes: [À calculer] €");
        System.out.println("-".repeat(50));

        System.out.println("\n📈 INDICATEURS CLÉS");
        System.out.println("• Évolution des coûts vs mois précédent: [À calculer]%");
        System.out.println("• Département le plus coûteux: [À identifier]");
        System.out.println("• Type de paiement le plus fréquent: [À calculer]");

        System.out.println("\n⚠️  ALERTES");
        System.out.println("• Paiements en attente d'approbation: [À vérifier]");
        System.out.println("• Budgets départementaux dépassés: [À contrôler]");
    }

    private void controleEtValidation() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== CONTRÔLE ET VALIDATION ===");
            System.out.println("1. Valider les paiements en attente");
            System.out.println("2. Audit des comptes agents");
            System.out.println("3. Vérification des budgets départementaux");
            System.out.println("4. Contrôle des autorisations");
            System.out.println("5. Historique des modifications");
            System.out.println("6. Sauvegarde et export des données");
            System.out.println("0. Retour");
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    validerPaiementsEnAttente();
                    break;
                case 2:
                    auditComptesAgents();
                    break;
                case 3:
                    verificationBudgetsDepartementaux();
                    break;
                case 4:
                    controleAutorisations();
                    break;
                case 5:
                    historiqueModifications();
                    break;
                case 6:
                    sauvegardeExportDonnees();
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    // Méthodes d'implémentation (stubs pour l'instant)
    private void voirTousLesAgents() {
        System.out.println("\n=== TOUS LES AGENTS DE L'ENTREPRISE ===");
        System.out.println("Fonctionnalité à implémenter: afficher tous les agents avec leurs départements");
    }

    private void ajouterAgentGlobal() {
        System.out.println("\n=== AJOUTER UN AGENT (NIVEAU DIRECTEUR) ===");
        // Même logique que ResponsableView.ajouterAgent() mais avec plus de privilèges
        System.out.println("Fonctionnalité étendue d'ajout d'agent...");
    }

    private void modifierAgentGlobal() {
        System.out.println("\n=== MODIFIER UN AGENT (NIVEAU DIRECTEUR) ===");
        System.out.println("Fonctionnalité étendue de modification d'agent...");
    }

    private void supprimerAgentGlobal() {
        System.out.println("\n=== SUPPRIMER UN AGENT (NIVEAU DIRECTEUR) ===");
        System.out.println("Fonctionnalité étendue de suppression d'agent...");
    }

    private void transfererAgent() {
        System.out.println("\n=== TRANSFÉRER UN AGENT ===");
        System.out.print("ID de l'agent à transférer: ");
        int idAgent = lireEntier();
        System.out.print("ID du nouveau département: ");
        int idDepartement = lireEntier();

        try {
            agentController.affecterAgentDepartement(idAgent, idDepartement);
            System.out.println("Agent transféré avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur lors du transfert: " + e.getMessage());
        }
    }

    private void gererPromotionAgent() {
        System.out.println("\n=== PROMOTION/RÉTROGRADATION D'AGENT ===");
        System.out.print("ID de l'agent: ");
        int idAgent = lireEntier();

        System.out.println("Nouveaux types disponibles:");
        TypeAgent[] types = TypeAgent.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        System.out.print("Choisissez le nouveau type (1-" + types.length + "): ");
        int choixType = lireEntier();

        if (choixType >= 1 && choixType <= types.length) {
            TypeAgent nouveauType = types[choixType - 1];
            System.out.println("Changement de type vers " + nouveauType + " - Fonctionnalité à implémenter");
        } else {
            System.out.println("Choix invalide.");
        }
    }

    private void rechercherAgent() {
        System.out.println("\n=== RECHERCHER UN AGENT ===");
        System.out.print("Entrez le nom, prénom ou email: ");
        String critere = scanner.nextLine();
        System.out.println("Recherche de: " + critere + " - Fonctionnalité à implémenter");
    }

    private void afficherAgentsParType() {
        System.out.println("\n=== AGENTS PAR TYPE ===");
        TypeAgent[] types = TypeAgent.values();
        for (TypeAgent type : types) {
            System.out.println("• " + type + ": [Nombre à calculer] agents");
        }
    }

    private void creerDepartement() {
        System.out.println("\n=== CRÉER UN DÉPARTEMENT ===");
        System.out.print("Nom du département: ");
        String nom = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();

        try {
            departementController.creerDepartement(nom, description);
            System.out.println("Département créé avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void modifierDepartement() {
        System.out.println("\n=== MODIFIER UN DÉPARTEMENT ===");
        System.out.print("ID du département: ");
        int idDepartement = lireEntier();
        System.out.print("Nouveau nom: ");
        String nom = scanner.nextLine();
        System.out.print("Nouvelle description: ");
        String description = scanner.nextLine();

        try {
            departementController.modifierDepartement(idDepartement, nom, description);
            System.out.println("Département modifié avec succès!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void supprimerDepartement() {
        System.out.println("\n=== SUPPRIMER UN DÉPARTEMENT ===");
        System.out.print("ID du département: ");
        int idDepartement = lireEntier();

        System.out.print("Êtes-vous sûr? Cette action est irréversible (o/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("o") || confirmation.equalsIgnoreCase("oui")) {
            try {
                departementController.supprimerDepartement(idDepartement);
                System.out.println("Département supprimé avec succès!");
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }
    }

    private void voirTousLesDepartements() {
        System.out.println("\n=== TOUS LES DÉPARTEMENTS ===");
        departementController.afficherTousLesDepartements();
    }

    private void statistiquesParDepartement() {
        System.out.println("\n=== STATISTIQUES PAR DÉPARTEMENT ===");
        System.out.println("Fonctionnalité à implémenter: statistiques détaillées par département");
    }

    private void affecterResponsable() {
        System.out.println("\n=== AFFECTER UN RESPONSABLE ===");
        System.out.print("ID du département: ");
        int idDepartement = lireEntier();
        System.out.print("ID de l'agent responsable: ");
        int idAgent = lireEntier();

        System.out.println("Affectation du responsable - Fonctionnalité à implémenter");
    }

    // Stubs pour les autres méthodes
    private void gererApprobationsPaiements() {
        System.out.println("Gestion des approbations de paiements - À implémenter");
    }

    private void historiqueCompletPaiements() {
        System.out.println("Historique complet des paiements - À implémenter");
    }

    private void paiementsParDepartement() {
        System.out.println("Paiements par département - À implémenter");
    }

    private void paiementsExceptionnels() {
        System.out.println("Paiements exceptionnels - À implémenter");
    }

    private void ajouterPaiementSpecial() {
        System.out.println("Ajout de paiement spécial - À implémenter");
    }

    private void annulerPaiement() {
        System.out.println("Annulation de paiement - À implémenter");
    }

    private void auditPaiements() {
        System.out.println("Audit des paiements - À implémenter");
    }

    private void rapportMensuelGlobal() {
        System.out.println("Rapport mensuel global - À implémenter");
    }

    private void rapportAnnuel() {
        System.out.println("Rapport annuel - À implémenter");
    }

    private void comparaisonInterDepartements() {
        System.out.println("Comparaison inter-départements - À implémenter");
    }

    private void evolutionCoutsSalariaux() {
        System.out.println("Évolution des coûts salariaux - À implémenter");
    }

    private void analysePrimesBonus() {
        System.out.println("Analyse des primes et bonus - À implémenter");
    }

    private void rapportPerformanceAgent() {
        System.out.println("Rapport de performance par agent - À implémenter");
    }

    private void previsionsBudgetaires() {
        System.out.println("Prévisions budgétaires - À implémenter");
    }

    private void validerPaiementsEnAttente() {
        System.out.println("Validation des paiements en attente - À implémenter");
    }

    private void auditComptesAgents() {
        System.out.println("Audit des comptes agents - À implémenter");
    }

    private void verificationBudgetsDepartementaux() {
        System.out.println("Vérification des budgets départementaux - À implémenter");
    }

    private void controleAutorisations() {
        System.out.println("Contrôle des autorisations - À implémenter");
    }

    private void historiqueModifications() {
        System.out.println("Historique des modifications - À implémenter");
    }

    private void sauvegardeExportDonnees() {
        System.out.println("Sauvegarde et export des données - À implémenter");
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
}
