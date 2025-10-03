package view;

import controller.AgentController;
import controller.PaiementController;
import model.Agent;
import model.Paiement;
import model.enums.TypePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AgentView {
    private Scanner scanner;
    private AgentController agentController;
    private PaiementController paiementController;

    public AgentView() {
        this.scanner = new Scanner(System.in);
        this.agentController = new AgentController();
        this.paiementController = new PaiementController();
    }

    public void afficherMenuAgent(int idAgent) {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("      INTERFACE AGENT");
            System.out.println("=".repeat(40));
            System.out.println("1. Consulter mes informations");
            System.out.println("2. Voir mon historique de paiements");
            System.out.println("3. Filtrer mes paiements");
            System.out.println("4. Trier mes paiements");
            System.out.println("5. Calculer mes totaux");
            System.out.println("6. Afficher mon résumé complet");
            System.out.println("0. Retour au menu principal");
            System.out.println("=".repeat(40));
            System.out.print("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    consulterMesInformations(idAgent);
                    break;
                case 2:
                    voirHistoriquePaiements(idAgent);
                    break;
                case 3:
                    gererFiltragePaiements(idAgent);
                    break;
                case 4:
                    gererTriPaiements(idAgent);
                    break;
                case 5:
                    gererCalculTotaux(idAgent);
                    break;
                case 6:
                    agentController.afficherResumeAgent(idAgent);
                    break;
                case 0:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private void consulterMesInformations(int idAgent) {
        System.out.println("\n=== MES INFORMATIONS ===");
        Agent agent = agentController.consulterMesInformationsAvecDepartement(idAgent);

        if (agent != null) {
            afficherInformationsAgent(agent);
        } else {
            System.out.println("Aucune information trouvée pour l'agent ID: " + idAgent);
        }
    }

    private void voirHistoriquePaiements(int idAgent) {
        System.out.println("\n=== HISTORIQUE DE MES PAIEMENTS ===");
        List<Paiement> paiements = paiementController.voirMonHistoriquePaiements(idAgent);

        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement trouvé.");
        } else {
            afficherListePaiements(paiements);
        }
    }

    private void gererFiltragePaiements(int idAgent) {
        System.out.println("\n=== FILTRER MES PAIEMENTS ===");
        System.out.println("1. Par type de paiement");
        System.out.println("2. Par montant");
        System.out.println("3. Par date");
        System.out.print("Votre choix: ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                filtrerParType(idAgent);
                break;
            case 2:
                filtrerParMontant(idAgent);
                break;
            case 3:
                filtrerParDate(idAgent);
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void filtrerParType(int idAgent) {
        System.out.println("\n=== FILTRER PAR TYPE ===");
        System.out.println("Types disponibles:");
        TypePaiement[] types = TypePaiement.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        System.out.print("Choisissez un type (1-" + types.length + "): ");

        int choix = lireEntier();
        if (choix >= 1 && choix <= types.length) {
            TypePaiement type = types[choix - 1];
            List<Paiement> paiements = paiementController.filtrerMesPaiementsParType(idAgent, type);
            System.out.println("\n=== PAIEMENTS DE TYPE " + type + " ===");
            afficherListePaiements(paiements);
        } else {
            System.out.println("Choix invalide.");
        }
    }

    private void filtrerParMontant(int idAgent) {
        System.out.println("\n=== FILTRER PAR MONTANT ===");
        System.out.print("Montant minimum: ");
        BigDecimal montantMin = lireBigDecimal();
        System.out.print("Montant maximum: ");
        BigDecimal montantMax = lireBigDecimal();

        List<Paiement> paiements = paiementController.filtrerMesPaiementsParMontant(idAgent, montantMin, montantMax);
        System.out.println("\n=== PAIEMENTS ENTRE " + montantMin + " ET " + montantMax + " ===");
        afficherListePaiements(paiements);
    }

    private void filtrerParDate(int idAgent) {
        System.out.println("\n=== FILTRER PAR DATE ===");
        System.out.print("Date de début (yyyy-MM-dd): ");
        LocalDate dateDebut = lireDate();
        System.out.print("Date de fin (yyyy-MM-dd): ");
        LocalDate dateFin = lireDate();

        if (dateDebut != null && dateFin != null) {
            List<Paiement> paiements = paiementController.filtrerMesPaiementsParDate(idAgent, dateDebut, dateFin);
            System.out.println("\n=== PAIEMENTS DU " + dateDebut + " AU " + dateFin + " ===");
            afficherListePaiements(paiements);
        }
    }

    private void gererTriPaiements(int idAgent) {
        System.out.println("\n=== TRIER MES PAIEMENTS ===");
        System.out.println("1. Par montant (croissant)");
        System.out.println("2. Par montant (décroissant)");
        System.out.println("3. Par date (croissant)");
        System.out.println("4. Par date (décroissant)");
        System.out.print("Votre choix: ");

        int choix = lireEntier();
        List<Paiement> paiements = null;

        switch (choix) {
            case 1:
                paiements = paiementController.trierMesPaiementsParMontant(idAgent, true);
                System.out.println("\n=== PAIEMENTS TRIÉS PAR MONTANT (CROISSANT) ===");
                break;
            case 2:
                paiements = paiementController.trierMesPaiementsParMontant(idAgent, false);
                System.out.println("\n=== PAIEMENTS TRIÉS PAR MONTANT (DÉCROISSANT) ===");
                break;
            case 3:
                paiements = paiementController.trierMesPaiementsParDate(idAgent, true);
                System.out.println("\n=== PAIEMENTS TRIÉS PAR DATE (CROISSANT) ===");
                break;
            case 4:
                paiements = paiementController.trierMesPaiementsParDate(idAgent, false);
                System.out.println("\n=== PAIEMENTS TRIÉS PAR DATE (DÉCROISSANT) ===");
                break;
            default:
                System.out.println("Choix invalide.");
                return;
        }

        if (paiements != null) {
            afficherListePaiements(paiements);
        }
    }

    private void gererCalculTotaux(int idAgent) {
        System.out.println("\n=== CALCULER MES TOTAUX ===");
        System.out.println("1. Total de tous mes paiements");
        System.out.println("2. Total par type de paiement");
        System.out.println("3. Total pour une période");
        System.out.print("Votre choix: ");

        int choix = lireEntier();

        switch (choix) {
            case 1:
                BigDecimal total = paiementController.calculerMonTotalPaiements(idAgent);
                System.out.println("Total de tous vos paiements: " + total + " €");
                break;
            case 2:
                calculerTotalParType(idAgent);
                break;
            case 3:
                calculerTotalParPeriode(idAgent);
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void calculerTotalParType(int idAgent) {
        System.out.println("\n=== TOTAL PAR TYPE ===");
        TypePaiement[] types = TypePaiement.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        System.out.print("Choisissez un type (1-" + types.length + "): ");

        int choix = lireEntier();
        if (choix >= 1 && choix <= types.length) {
            TypePaiement type = types[choix - 1];
            BigDecimal total = paiementController.calculerMonTotalPaiementsParType(idAgent, type);
            System.out.println("Total pour " + type + ": " + total + " €");
        } else {
            System.out.println("Choix invalide.");
        }
    }

    private void calculerTotalParPeriode(int idAgent) {
        System.out.println("\n=== TOTAL PAR PÉRIODE ===");
        System.out.print("Date de début (yyyy-MM-dd): ");
        LocalDate dateDebut = lireDate();
        System.out.print("Date de fin (yyyy-MM-dd): ");
        LocalDate dateFin = lireDate();

        if (dateDebut != null && dateFin != null) {
            BigDecimal total = paiementController.calculerMonTotalPaiementsParPeriode(idAgent, dateDebut, dateFin);
            System.out.println("Total du " + dateDebut + " au " + dateFin + ": " + total + " €");
        }
    }

    private void afficherInformationsAgent(Agent agent) {
        System.out.println("ID: " + agent.getIdAgent());
        System.out.println("Nom: " + agent.getNom());
        System.out.println("Prénom: " + agent.getPrenom());
        System.out.println("Email: " + agent.getEmail());
        System.out.println("Type: " + agent.getTypeAgent());
        if (agent.getDepartement() != null) {
            System.out.println("Département: " + agent.getDepartement().getNom());
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

    private LocalDate lireDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                String input = scanner.nextLine();
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.print("Format de date invalide. Utilisez yyyy-MM-dd: ");
            }
        }
    }
}
