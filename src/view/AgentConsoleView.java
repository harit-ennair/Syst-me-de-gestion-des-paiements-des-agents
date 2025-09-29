package view;

import controller.AgentController;
import model.Agent;
import model.Paiement;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AgentConsoleView {

    private AgentController agentController;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public AgentConsoleView() {
        this.agentController = new AgentController();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    /**
     * Menu principal pour un agent connecté
     */
    public void afficherMenuAgent(int idAgent) {
        boolean continuer = true;

        System.out.println("\n=== ESPACE AGENT ===");
        System.out.println("Bienvenue ! Voici vos options :");

        while (continuer) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Consulter mes informations personnelles");
            System.out.println("2. Voir l'historique de mes paiements");
            System.out.println("3. Filtrer et trier mes paiements");
            System.out.println("4. Calculer le total de mes paiements");
            System.out.println("5. Afficher mon résumé complet");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    consulterInformationsPersonnelles(idAgent);
                    break;
                case 2:
                    voirHistoriquePaiements(idAgent);
                    break;
                case 3:
                    menuFiltrageTri(idAgent);
                    break;
                case 4:
                    menuCalculTotaux(idAgent);
                    break;
                case 5:
                    agentController.afficherResumeAgent(idAgent);
                    break;
                case 0:
                    continuer = false;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    private void consulterInformationsPersonnelles(int idAgent) {
        System.out.println("\n=== MES INFORMATIONS PERSONNELLES ===");

        Agent agent = agentController.consulterMesInformationsAvecDepartement(idAgent);

        if (agent != null) {
            System.out.println("ID Agent: " + agent.getIdAgent());
            System.out.println("Nom: " + agent.getNom());
            System.out.println("Prénom: " + agent.getPrenom());
            System.out.println("Email: " + agent.getEmail());
            System.out.println("Type d'agent: " + agent.getTypeAgent());

            if (agent.getDepartement() != null) {
                System.out.println("Département: " + agent.getDepartement().getNom());
            } else {
                System.out.println("Département: Non assigné");
            }
        } else {
            System.out.println("Impossible de récupérer vos informations.");
        }
    }

    private void voirHistoriquePaiements(int idAgent) {
        System.out.println("\n=== HISTORIQUE DE MES PAIEMENTS ===");

        List<Paiement> paiements = agentController.voirMonHistoriquePaiements(idAgent);

        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
        } else {
            afficherListePaiements(paiements);
        }
    }

    private void menuFiltrageTri(int idAgent) {
        System.out.println("\n=== FILTRAGE ET TRI DES PAIEMENTS ===");
        System.out.println("1. Filtrer par type de paiement");
        System.out.println("2. Filtrer par montant");
        System.out.println("3. Filtrer par période");
        System.out.println("4. Trier par montant");
        System.out.println("5. Trier par date");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix : ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                filtrerParType(idAgent);
                break;
            case 2:
                filtrerParMontant(idAgent);
                break;
            case 3:
                filtrerParPeriode(idAgent);
                break;
            case 4:
                trierParMontant(idAgent);
                break;
            case 5:
                trierParDate(idAgent);
                break;
            case 0:
                return;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void filtrerParType(int idAgent) {
        System.out.println("\n=== FILTRER PAR TYPE DE PAIEMENT ===");
        System.out.println("Types de paiement disponibles :");

        TypePaiement[] types = TypePaiement.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        System.out.print("Choisissez un type (numéro) : ");
        int choix = lireEntier();

        if (choix >= 1 && choix <= types.length) {
            TypePaiement typeChoisi = types[choix - 1];
            List<Paiement> paiements = agentController.filtrerMesPaiementsParType(idAgent, typeChoisi);

            System.out.println("\nPaiements de type " + typeChoisi + " :");
            afficherListePaiements(paiements);
        } else {
            System.out.println("Choix invalide.");
        }
    }

    private void filtrerParMontant(int idAgent) {
        System.out.println("\n=== FILTRER PAR MONTANT ===");
        System.out.print("Montant minimum : ");
        BigDecimal montantMin = lireBigDecimal();
        System.out.print("Montant maximum : ");
        BigDecimal montantMax = lireBigDecimal();

        List<Paiement> paiements = agentController.filtrerMesPaiementsParMontant(idAgent, montantMin, montantMax);

        System.out.println("\nPaiements entre " + montantMin + "€ et " + montantMax + "€ :");
        afficherListePaiements(paiements);
    }

    private void filtrerParPeriode(int idAgent) {
        System.out.println("\n=== FILTRER PAR PÉRIODE ===");
        System.out.print("Date de début (dd/MM/yyyy) : ");
        LocalDate dateDebut = lireDate();
        System.out.print("Date de fin (dd/MM/yyyy) : ");
        LocalDate dateFin = lireDate();

        if (dateDebut != null && dateFin != null) {
            List<Paiement> paiements = agentController.filtrerMesPaiementsParDate(idAgent, dateDebut, dateFin);

            System.out.println("\nPaiements du " + dateDebut.format(dateFormatter) +
                    " au " + dateFin.format(dateFormatter) + " :");
            afficherListePaiements(paiements);
        }
    }

    private void trierParMontant(int idAgent) {
        System.out.println("\n=== TRIER PAR MONTANT ===");
        System.out.println("1. Ordre croissant");
        System.out.println("2. Ordre décroissant");
        System.out.print("Votre choix : ");

        int choix = lireEntier();
        boolean croissant = (choix == 1);

        List<Paiement> paiements = agentController.trierMesPaiementsParMontant(idAgent, croissant);

        System.out.println("\nPaiements triés par montant (" +
                (croissant ? "croissant" : "décroissant") + ") :");
        afficherListePaiements(paiements);
    }

    private void trierParDate(int idAgent) {
        System.out.println("\n=== TRIER PAR DATE ===");
        System.out.println("1. Plus récents en premier");
        System.out.println("2. Plus anciens en premier");
        System.out.print("Votre choix : ");

        int choix = lireEntier();
        boolean croissant = (choix == 2); // Récents = décroissant

        List<Paiement> paiements = agentController.trierMesPaiementsParDate(idAgent, croissant);

        System.out.println("\nPaiements triés par date (" +
                (croissant ? "anciens en premier" : "récents en premier") + ") :");
        afficherListePaiements(paiements);
    }

    private void menuCalculTotaux(int idAgent) {
        System.out.println("\n=== CALCUL DES TOTAUX ===");
        System.out.println("1. Total général de tous mes paiements");
        System.out.println("2. Total par type de paiement");
        System.out.println("3. Total sur une période");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix : ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                BigDecimal totalGeneral = agentController.calculerMonTotalPaiements(idAgent);
                System.out.println("Total général de vos paiements : " + totalGeneral + "€");
                break;
            case 2:
                calculerTotalParType(idAgent);
                break;
            case 3:
                calculerTotalParPeriode(idAgent);
                break;
            case 0:
                return;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void calculerTotalParType(int idAgent) {
        System.out.println("\n=== TOTAL PAR TYPE DE PAIEMENT ===");
        System.out.println("Types de paiement disponibles :");

        TypePaiement[] types = TypePaiement.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        System.out.print("Choisissez un type (numéro) : ");
        int choix = lireEntier();

        if (choix >= 1 && choix <= types.length) {
            TypePaiement typeChoisi = types[choix - 1];
            BigDecimal total = agentController.calculerMonTotalPaiementsParType(idAgent, typeChoisi);
            System.out.println("Total des paiements de type " + typeChoisi + " : " + total + "€");
        } else {
            System.out.println("Choix invalide.");
        }
    }

    private void calculerTotalParPeriode(int idAgent) {
        System.out.println("\n=== TOTAL PAR PÉRIODE ===");
        System.out.print("Date de début (dd/MM/yyyy) : ");
        LocalDate dateDebut = lireDate();
        System.out.print("Date de fin (dd/MM/yyyy) : ");
        LocalDate dateFin = lireDate();

        if (dateDebut != null && dateFin != null) {
            BigDecimal total = agentController.calculerMonTotalPaiementsParPeriode(idAgent, dateDebut, dateFin);
            System.out.println("Total des paiements du " + dateDebut.format(dateFormatter) +
                    " au " + dateFin.format(dateFormatter) + " : " + total + "€");
        }
    }

    private void afficherListePaiements(List<Paiement> paiements) {
        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
            return;
        }

        System.out.println("\n" + String.format("%-5s %-15s %-10s %-12s %-20s %-10s",
                "ID", "Type", "Montant", "Date", "Motif", "Validé"));
        System.out.println("─".repeat(80));

        for (Paiement paiement : paiements) {
            System.out.println(String.format("%-5d %-15s %-10.2f %-12s %-20s %-10s",
                    paiement.getIdPaiement(),
                    paiement.getTypePaiement(),
                    paiement.getMontant(),
                    paiement.getDatePaiement().format(dateFormatter),
                    (paiement.getMotif() != null ? paiement.getMotif() : "N/A"),
                    (paiement.isConditionValidee() ? "Oui" : "Non")
            ));
        }

        System.out.println("\nNombre total de paiements : " + paiements.size());
    }

    // Méthodes utilitaires pour la saisie
    private int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide.");
            return -1;
        }
    }

    private BigDecimal lireBigDecimal() {
        try {
            return new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un montant valide.");
            return BigDecimal.ZERO;
        }
    }

    private LocalDate lireDate() {
        try {
            String dateStr = scanner.nextLine().trim();
            return LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Format de date invalide. Utilisez dd/MM/yyyy");
            return null;
        }
    }

    /**
     * Méthode de démonstration pour tester les fonctionnalités
     */
    public static void demonstrationFonctionnalites() {
        AgentConsoleView view = new AgentConsoleView();

        System.out.println("=== DÉMONSTRATION DES FONCTIONNALITÉS AGENT ===");
        System.out.println("Cette démonstration simule l'utilisation par un agent avec l'ID 1");

        // Simuler une session pour l'agent ID = 1
        view.agentController.afficherResumeAgent(1);

        // Vous pouvez décommenter la ligne suivante pour lancer le menu interactif
        // view.afficherMenuAgent(1);
    }
}
