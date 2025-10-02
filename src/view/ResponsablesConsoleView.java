package view;

import controller.ResponsablesController;
import model.Agent;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ResponsablesConsoleView {

    private ResponsablesController responsablesController;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public ResponsablesConsoleView() {
        this.responsablesController = new ResponsablesController();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    /**
     * Menu principal pour les responsables et directeurs
     */

    public void afficherMenuPrincipal(TypeAgent typeUtilisateur, int idUtilisateur, int idDepartement) {
        boolean continuer = true;

        System.out.println("\n=== ESPACE " + typeUtilisateur + " ===");
        System.out.println("Bienvenue ! Voici vos options :");

        while (continuer) {
            System.out.println("\n--- MENU PRINCIPAL ---");

            if (typeUtilisateur == TypeAgent.DIRECTEUR) {
                afficherMenuDirecteur();
            } else if (typeUtilisateur == TypeAgent.RESPONSABLE_DEPARTEMENT) {
                afficherMenuResponsable();
            }

            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            int choix = lireEntier();

            if (typeUtilisateur == TypeAgent.DIRECTEUR) {
                continuer = traiterChoixDirecteur(choix);
            } else if (typeUtilisateur == TypeAgent.RESPONSABLE_DEPARTEMENT) {
                continuer = traiterChoixResponsable(choix, idDepartement);
            }
        }
    }

    // ============ MENUS ============

    private void afficherMenuDirecteur() {
        System.out.println("1. Gestion des départements");
        System.out.println("2. Associer un responsable à un département");
        System.out.println("3. Consulter les paiements d'un département");
        System.out.println("4. Identifier les paiements inhabituels");
        System.out.println("5. Afficher le résumé d'un département");
    }

    private void afficherMenuResponsable() {
        System.out.println("1. Gestion des agents");
        System.out.println("2. Gestion des paiements");
        System.out.println("3. Consultation des paiements");
        System.out.println("4. Filtrage et analyse des paiements");
        System.out.println("5. Afficher le résumé de mon département");
    }

    // ============ TRAITEMENT DES CHOIX ============

    private boolean traiterChoixDirecteur(int choix) {
        switch (choix) {
            case 1:
                menuGestionDepartements();
                break;
            case 2:
                associerResponsableDepartement();
                break;
            case 3:
                consulterPaiementsDepartement();
                break;
            case 4:
                identifierPaiementsInhabituels();
                break;
            case 5:
                afficherResumeDepartement();
                break;
            case 0:
                System.out.println("Au revoir !");
                return false;
            default:
                System.out.println("Choix invalide, veuillez réessayer.");
        }
        return true;
    }

    private boolean traiterChoixResponsable(int choix, int idDepartement) {
        switch (choix) {
            case 1:
                menuGestionAgents(idDepartement);
                break;
            case 2:
                menuGestionPaiements();
                break;
            case 3:
                menuConsultationPaiements(idDepartement);
                break;
            case 4:
                menuFiltrageAnalyse(idDepartement);
                break;
            case 5:
                responsablesController.afficherResumeDepartement(idDepartement);
                break;
            case 0:
                System.out.println("Au revoir !");
                return false;
            default:
                System.out.println("Choix invalide, veuillez réessayer.");
        }
        return true;
    }

    // ============ GESTION DES DÉPARTEMENTS (DIRECTEUR) ============

    private void menuGestionDepartements() {
        System.out.println("\n=== GESTION DES DÉPARTEMENTS ===");
        System.out.println("1. Créer un département");
        System.out.println("2. Modifier un département");
        System.out.println("3. Supprimer un département");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");

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
            case 0:
                return;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void creerDepartement() {
        System.out.println("\n=== CRÉER UN DÉPARTEMENT ===");
        System.out.print("Nom du département : ");
        String nom = scanner.nextLine();

        System.out.print("Description (optionnelle) : ");
        String description = scanner.nextLine();
        if (description.trim().isEmpty()) {
            description = null;
        }

        responsablesController.creerDepartement(nom, description);
    }

    private void modifierDepartement() {
        System.out.println("\n=== MODIFIER UN DÉPARTEMENT ===");
        System.out.print("ID du département à modifier : ");
        int idDepartement = lireEntier();

        System.out.print("Nouveau nom : ");
        String nom = scanner.nextLine();

        System.out.print("Nouvelle description : ");
        String description = scanner.nextLine();

        responsablesController.modifierDepartement(idDepartement, nom, description);
    }

    private void supprimerDepartement() {
        System.out.println("\n=== SUPPRIMER UN DÉPARTEMENT ===");
        System.out.print("ID du département à supprimer : ");
        int idDepartement = lireEntier();

        System.out.print("Êtes-vous sûr ? (oui/non) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("oui")) {
            responsablesController.supprimerDepartement(idDepartement);
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    private void associerResponsableDepartement() {
        System.out.println("\n=== ASSOCIER UN RESPONSABLE ===");
        System.out.print("ID du département : ");
        int idDepartement = lireEntier();

        System.out.print("ID du responsable : ");
        int idResponsable = lireEntier();

        responsablesController.associerResponsable(idDepartement, idResponsable);
    }

    // ============ GESTION DES AGENTS (RESPONSABLE) ============

    private void menuGestionAgents(int idDepartement) {
        System.out.println("\n=== GESTION DES AGENTS ===");
        System.out.println("1. Ajouter un agent");
        System.out.println("2. Modifier un agent");
        System.out.println("3. Supprimer un agent");
        System.out.println("4. Affecter un agent au département");
        System.out.println("5. Lister les agents du département");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                ajouterAgent(idDepartement);
                break;
            case 2:
                modifierAgent();
                break;
            case 3:
                supprimerAgent();
                break;
            case 4:
                affecterAgent(idDepartement);
                break;
            case 5:
                listerAgentsDepartement(idDepartement);
                break;
            case 0:
                return;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void ajouterAgent(int idDepartement) {
        System.out.println("\n=== AJOUTER UN AGENT ===");
        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        TypeAgent typeAgent = choisirTypeAgent();

        responsablesController.ajouterAgent(nom, prenom, email, password, typeAgent, idDepartement);
    }

    private void modifierAgent() {
        System.out.println("\n=== MODIFIER UN AGENT ===");
        System.out.print("ID de l'agent à modifier : ");
        int idAgent = lireEntier();

        System.out.print("Nouveau nom (laisser vide pour ne pas modifier) : ");
        String nom = scanner.nextLine();
        if (nom.trim().isEmpty()) nom = null;

        System.out.print("Nouveau prénom (laisser vide pour ne pas modifier) : ");
        String prenom = scanner.nextLine();
        if (prenom.trim().isEmpty()) prenom = null;

        System.out.print("Nouvel email (laisser vide pour ne pas modifier) : ");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) email = null;

        System.out.print("Modifier le type d'agent ? (oui/non) : ");
        TypeAgent typeAgent = null;
        if (scanner.nextLine().equalsIgnoreCase("oui")) {
            typeAgent = choisirTypeAgent();
        }

        System.out.print("Nouvel ID département (0 pour ne pas modifier) : ");
        int idDepartement = lireEntier();

        responsablesController.modifierAgent(idAgent, nom, prenom, email, typeAgent, idDepartement);
    }

    private void supprimerAgent() {
        System.out.println("\n=== SUPPRIMER UN AGENT ===");
        System.out.print("ID de l'agent à supprimer : ");
        int idAgent = lireEntier();

        System.out.print("Êtes-vous sûr ? (oui/non) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("oui")) {
            responsablesController.supprimerAgent(idAgent);
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    private void affecterAgent(int idDepartement) {
        System.out.println("\n=== AFFECTER UN AGENT ===");
        System.out.print("ID de l'agent : ");
        int idAgent = lireEntier();

        responsablesController.affecterAgentDepartement(idAgent, idDepartement);
    }

    private void listerAgentsDepartement(int idDepartement) {
        System.out.println("\n=== AGENTS DU DÉPARTEMENT ===");
        List<Agent> agents = responsablesController.obtenirAgentsDepartement(idDepartement);

        if (agents == null || agents.isEmpty()) {
            System.out.println("Aucun agent trouvé dans ce département.");
        } else {
            afficherListeAgents(agents);
        }
    }

    // ============ GESTION DES PAIEMENTS ============

    private void menuGestionPaiements() {
        System.out.println("\n=== GESTION DES PAIEMENTS ===");
        System.out.println("1. Ajouter un salaire");
        System.out.println("2. Ajouter une prime");
        System.out.println("3. Ajouter un bonus");
        System.out.println("4. Ajouter une indemnité");
        System.out.println("5. Vérifier l'éligibilité d'un agent");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                ajouterPaiement(TypePaiement.SALAIRE);
                break;
            case 2:
                ajouterPaiement(TypePaiement.PRIME);
                break;
            case 3:
                ajouterPaiement(TypePaiement.BONUS);
                break;
            case 4:
                ajouterPaiement(TypePaiement.INDEMNITE);
                break;
            case 5:
                verifierEligibilite();
                break;
            case 0:
                return;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void ajouterPaiement(TypePaiement type) {
        System.out.println("\n=== AJOUTER " + type + " ===");
        System.out.print("ID de l'agent : ");
        int idAgent = lireEntier();

        System.out.print("Montant : ");
        BigDecimal montant = lireBigDecimal();

        System.out.print("Motif : ");
        String motif = scanner.nextLine();

        switch (type) {
            case SALAIRE:
                responsablesController.ajouterSalaire(idAgent, montant, motif);
                break;
            case PRIME:
                responsablesController.ajouterPrime(idAgent, montant, motif);
                break;
            case BONUS:
                responsablesController.ajouterBonus(idAgent, montant, motif);
                break;
            case INDEMNITE:
                responsablesController.ajouterIndemnite(idAgent, montant, motif);
                break;
        }
    }

    private void verifierEligibilite() {
        System.out.println("\n=== VÉRIFIER ÉLIGIBILITÉ ===");
        System.out.print("ID de l'agent : ");
        int idAgent = lireEntier();

        boolean eligibleBonus = responsablesController.verifierEligibiliteBonus(idAgent);
        boolean eligibleIndemnite = responsablesController.verifierEligibiliteIndemnite(idAgent);

        System.out.println("Éligible au bonus : " + (eligibleBonus ? "OUI" : "NON"));
        System.out.println("Éligible à l'indemnité : " + (eligibleIndemnite ? "OUI" : "NON"));
    }

    // ============ CONSULTATION ET FILTRAGE ============

    private void menuConsultationPaiements(int idDepartement) {
        System.out.println("\n=== CONSULTATION DES PAIEMENTS ===");
        System.out.println("1. Consulter les paiements d'un agent");
        System.out.println("2. Consulter les paiements du département");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                consulterPaiementsAgent();
                break;
            case 2:
                consulterPaiementsDepartement(idDepartement);
                break;
            case 0:
                return;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void consulterPaiementsAgent() {
        System.out.println("\n=== PAIEMENTS D'UN AGENT ===");
        System.out.print("ID de l'agent : ");
        int idAgent = lireEntier();

        List<Paiement> paiements = responsablesController.consulterPaiementsAgent(idAgent);
        afficherListePaiements(paiements);
    }

    private void consulterPaiementsDepartement() {
        System.out.println("\n=== PAIEMENTS DU DÉPARTEMENT ===");
        System.out.print("ID du département : ");
        int idDepartement = lireEntier();

        List<Paiement> paiements = responsablesController.consulterPaiementsDepartement(idDepartement);
        afficherListePaiements(paiements);
    }

    private void consulterPaiementsDepartement(int idDepartement) {
        System.out.println("\n=== PAIEMENTS DU DÉPARTEMENT ===");
        List<Paiement> paiements = responsablesController.consulterPaiementsDepartement(idDepartement);
        afficherListePaiements(paiements);
    }

    private void menuFiltrageAnalyse(int idDepartement) {
        System.out.println("\n=== FILTRAGE ET ANALYSE ===");
        System.out.println("1. Filtrer les paiements");
        System.out.println("2. Identifier les paiements inhabituels");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                filtrerPaiements(idDepartement);
                break;
            case 2:
                identifierPaiementsInhabituels(idDepartement);
                break;
            case 0:
                return;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void filtrerPaiements(int idDepartement) {
        System.out.println("\n=== FILTRER LES PAIEMENTS ===");

        System.out.print("Filtrer par type ? (oui/non) : ");
        TypePaiement typePaiement = null;
        if (scanner.nextLine().equalsIgnoreCase("oui")) {
            typePaiement = choisirTypePaiement();
        }

        System.out.print("Date de début (dd/MM/yyyy) - laisser vide pour ignorer : ");
        LocalDate dateDebut = lireDate();

        System.out.print("Date de fin (dd/MM/yyyy) - laisser vide pour ignorer : ");
        LocalDate dateFin = lireDate();

        List<Paiement> paiements = responsablesController.filtrerPaiements(idDepartement, typePaiement, dateDebut, dateFin);
        afficherListePaiements(paiements);
    }

    private void identifierPaiementsInhabituels() {
        System.out.println("\n=== PAIEMENTS INHABITUELS ===");
        System.out.print("ID du département : ");
        int idDepartement = lireEntier();

        identifierPaiementsInhabituels(idDepartement);
    }

    private void identifierPaiementsInhabituels(int idDepartement) {
        System.out.println("\n=== PAIEMENTS INHABITUELS DÉTECTÉS ===");
        List<Paiement> paiements = responsablesController.identifierPaiementsInhabituels(idDepartement);

        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement inhabituel détecté.");
        } else {
            System.out.println("⚠️ " + paiements.size() + " paiement(s) inhabituel(s) détecté(s) :");
            afficherListePaiements(paiements);
        }
    }

    private void afficherResumeDepartement() {
        System.out.println("\n=== RÉSUMÉ DÉPARTEMENT ===");
        System.out.print("ID du département : ");
        int idDepartement = lireEntier();

        responsablesController.afficherResumeDepartement(idDepartement);
    }

    // ============ MÉTHODES UTILITAIRES ============

    private TypeAgent choisirTypeAgent() {
        System.out.println("\nTypes d'agent disponibles :");
        TypeAgent[] types = TypeAgent.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        System.out.print("Choisissez un type (numéro) : ");
        int choix = lireEntier() - 1;

        if (choix >= 0 && choix < types.length) {
            return types[choix];
        } else {
            System.out.println("Choix invalide, EMPLOYE sélectionné par défaut.");
            return TypeAgent.OUVRIER;
        }
    }

    private TypePaiement choisirTypePaiement() {
        System.out.println("\nTypes de paiement disponibles :");
        TypePaiement[] types = TypePaiement.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        System.out.print("Choisissez un type (numéro) : ");
        int choix = lireEntier() - 1;

        if (choix >= 0 && choix < types.length) {
            return types[choix];
        } else {
            System.out.println("Choix invalide, SALAIRE sélectionné par défaut.");
            return TypePaiement.SALAIRE;
        }
    }

    private void afficherListeAgents(List<Agent> agents) {
        System.out.println("\n--- LISTE DES AGENTS ---");
        for (Agent agent : agents) {
            System.out.println("ID: " + agent.getIdAgent() +
                             " | " + agent.getPrenom() + " " + agent.getNom() +
                             " | " + agent.getEmail() +
                             " | Type: " + agent.getTypeAgent());
        }
    }

    private void afficherListePaiements(List<Paiement> paiements) {
        if (paiements == null || paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            return;
        }

        System.out.println("\n--- LISTE DES PAIEMENTS ---");
        double total = 0;
        for (Paiement paiement : paiements) {
            System.out.println("ID: " + paiement.getIdPaiement() +
                             " | Agent: " + paiement.getIdAgent() +
                             " | Type: " + paiement.getTypePaiement() +
                             " | Montant: " + paiement.getMontant() + "€" +
                             " | Date: " + paiement.getDatePaiement().format(dateFormatter) +
                             " | Motif: " + paiement.getMotif());
            total += paiement.getMontant();
        }
        System.out.println("--- TOTAL: " + total + "€ ---");
    }

    private int lireEntier() {
        try {
            int valeur = Integer.parseInt(scanner.nextLine());
            return valeur;
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide.");
            return 0;
        }
    }

    private BigDecimal lireBigDecimal() {
        try {
            return new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Montant invalide, 0 utilisé par défaut.");
            return BigDecimal.ZERO;
        }
    }

    private LocalDate lireDate() {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(input, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Format de date invalide (dd/MM/yyyy), ignoré.");
            return null;
        }
    }
}
