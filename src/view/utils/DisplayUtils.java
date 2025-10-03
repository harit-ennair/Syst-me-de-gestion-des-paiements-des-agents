//package view.utils;
//
//import model.Agent;
//import model.Departement;
//import model.Paiement;
//import model.enums.TypeAgent;
//import model.enums.TypePaiement;
//
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Locale;
//
///**
// * Classe utilitaire pour l'affichage formaté des données dans les vues
// */
//public class DisplayUtils {
//
//    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.FRANCE);
//
//    /**
//     * Affiche un agent avec formatage
//     */
//    public static void afficherAgent(Agent agent) {
//        if (agent == null) {
//            System.out.println("Agent non trouvé");
//            return;
//        }
//
//        System.out.println("┌" + "─".repeat(50) + "┐");
//        System.out.println("│" + centrerTexte("INFORMATIONS AGENT", 50) + "│");
//        System.out.println("├" + "─".repeat(50) + "┤");
//        System.out.printf("│ %-12s : %-33s │%n", "ID", agent.getIdAgent());
//        System.out.printf("│ %-12s : %-33s │%n", "Nom", agent.getNom());
//        System.out.printf("│ %-12s : %-33s │%n", "Prénom", agent.getPrenom());
//        System.out.printf("│ %-12s : %-33s │%n", "Email", agent.getEmail());
//        System.out.printf("│ %-12s : %-33s │%n", "Type", agent.getTypeAgent());
//
//        if (agent.getDepartement() != null) {
//            System.out.printf("│ %-12s : %-33s │%n", "Département", agent.getDepartement().getNom());
//        } else {
//            System.out.printf("│ %-12s : %-33s │%n", "Département", "Non assigné");
//        }
//
//        System.out.println("└" + "─".repeat(50) + "┘");
//    }
//
//    /**
//     * Affiche une liste d'agents sous forme de tableau
//     */
//    public static void afficherListeAgents(List<Agent> agents) {
//        if (agents == null || agents.isEmpty()) {
//            System.out.println("Aucun agent à afficher");
//            return;
//        }
//
//        System.out.println("\n" + "=".repeat(90));
//        System.out.println(centrerTexte("LISTE DES AGENTS", 90));
//        System.out.println("=".repeat(90));
//
//        System.out.printf("%-5s %-15s %-15s %-25s %-15s %-10s%n",
//                         "ID", "Nom", "Prénom", "Email", "Type", "Dept ID");
//        System.out.println("-".repeat(90));
//
//        for (Agent agent : agents) {
//            System.out.printf("%-5d %-15s %-15s %-25s %-15s %-10d%n",
//                             agent.getIdAgent(),
//                             tronquer(agent.getNom(), 15),
//                             tronquer(agent.getPrenom(), 15),
//                             tronquer(agent.getEmail(), 25),
//                             agent.getTypeAgent(),
//                             agent.getIdDepartement());
//        }
//        System.out.println("-".repeat(90));
//        System.out.println("Total: " + agents.size() + " agent(s)");
//    }
//
//    /**
//     * Affiche un département avec formatage
//     */
//    public static void afficherDepartement(Departement departement) {
//        if (departement == null) {
//            System.out.println("Département non trouvé");
//            return;
//        }
//
//        System.out.println("┌" + "─".repeat(60) + "┐");
//        System.out.println("│" + centrerTexte("INFORMATIONS DÉPARTEMENT", 60) + "│");
//        System.out.println("├" + "─".repeat(60) + "┤");
//        System.out.printf("│ %-15s : %-40s │%n", "ID", departement.getIdDepartement());
//        System.out.printf("│ %-15s : %-40s │%n", "Nom", departement.getNom());
//
//        String description = departement.getDescription() != null ?
//                           departement.getDescription() : "Aucune description";
//        System.out.printf("│ %-15s : %-40s │%n", "Description", tronquer(description, 40));
//
//        System.out.println("└" + "─".repeat(60) + "┘");
//    }
//
//    /**
//     * Affiche une liste de paiements sous forme de tableau détaillé
//     */
//    public static void afficherListePaiements(List<Paiement> paiements) {
//        if (paiements == null || paiements.isEmpty()) {
//            System.out.println("\n📄 Aucun paiement à afficher");
//            return;
//        }
//
//        System.out.println("\n" + "=".repeat(100));
//        System.out.println(centrerTexte("HISTORIQUE DES PAIEMENTS", 100));
//        System.out.println("=".repeat(100));
//
//        System.out.printf("%-5s %-12s %-15s %-12s %-30s %-15s%n",
//                         "ID", "Type", "Montant", "Date", "Motif", "Statut");
//        System.out.println("-".repeat(100));
//
//        BigDecimal totalMontant = BigDecimal.ZERO;
//
//        for (Paiement paiement : paiements) {
//            String montantStr = CURRENCY_FORMATTER.format(paiement.getMontant());
//            String dateStr = paiement.getDatePaiement() != null ?
//                           paiement.getDatePaiement().format(DATE_FORMATTER) : "N/A";
//            String motif = paiement.getMotif() != null ?
//                          tronquer(paiement.getMotif(), 30) : "N/A";
//            String statut = paiement.isConditionValidee() ? "✅ Validé" : "⏳ En attente";
//
//            System.out.printf("%-5d %-12s %-15s %-12s %-30s %-15s%n",
//                             paiement.getIdPaiement(),
//                             paiement.getTypePaiement(),
//                             montantStr,
//                             dateStr,
//                             motif,
//                             statut);
//
//            totalMontant = totalMontant.add(BigDecimal.valueOf(paiement.getMontant()));
//        }
//
//        System.out.println("-".repeat(100));
//        System.out.printf("%-59s TOTAL: %s%n", "", CURRENCY_FORMATTER.format(totalMontant));
//        System.out.println("=".repeat(100));
//        System.out.println("📊 Nombre de paiements: " + paiements.size());
//    }
//
//    /**
//     * Affiche un résumé statistique des paiements
//     */
//    public static void afficherResumePaiements(List<Paiement> paiements) {
//        if (paiements == null || paiements.isEmpty()) {
//            System.out.println("Aucune donnée pour générer le résumé");
//            return;
//        }
//
//        // Calculs statistiques
//        BigDecimal total = BigDecimal.ZERO;
//        int nbSalaires = 0, nbPrimes = 0, nbBonus = 0, nbIndemnites = 0;
//        BigDecimal totalSalaires = BigDecimal.ZERO;
//        BigDecimal totalPrimes = BigDecimal.ZERO;
//        BigDecimal totalBonus = BigDecimal.ZERO;
//        BigDecimal totalIndemnites = BigDecimal.ZERO;
//
//        for (Paiement p : paiements) {
//            BigDecimal montant = BigDecimal.valueOf(p.getMontant());
//            total = total.add(montant);
//
//            switch (p.getTypePaiement()) {
//                case "SALAIRE":
//                    nbSalaires++;
//                    totalSalaires = totalSalaires.add(montant);
//                    break;
//                case "PRIME":
//                    nbPrimes++;
//                    totalPrimes = totalPrimes.add(montant);
//                    break;
//                case "BONUS":
//                    nbBonus++;
//                    totalBonus = totalBonus.add(montant);
//                    break;
//                case "INDEMNITE":
//                    nbIndemnites++;
//                    totalIndemnites = totalIndemnites.add(montant);
//                    break;
//            }
//        }
//
//        System.out.println("\n┌" + "─".repeat(50) + "┐");
//        System.out.println("│" + centrerTexte("RÉSUMÉ FINANCIER", 50) + "│");
//        System.out.println("├" + "─".repeat(50) + "┤");
//        System.out.printf("│ %-25s : %20s │%n", "Total général", CURRENCY_FORMATTER.format(total));
//        System.out.println("├" + "─".repeat(50) + "┤");
//        System.out.printf("│ %-25s : %3d - %12s │%n", "Salaires", nbSalaires, CURRENCY_FORMATTER.format(totalSalaires));
//        System.out.printf("│ %-25s : %3d - %12s │%n", "Primes", nbPrimes, CURRENCY_FORMATTER.format(totalPrimes));
//        System.out.printf("│ %-25s : %3d - %12s │%n", "Bonus", nbBonus, CURRENCY_FORMATTER.format(totalBonus));
//        System.out.printf("│ %-25s : %3d - %12s │%n", "Indemnités", nbIndemnites, CURRENCY_FORMATTER.format(totalIndemnites));
//        System.out.println("└" + "─".repeat(50) + "┘");
//    }
//
//    /**
//     * Affiche un en-tête de section stylisé
//     */
//    public static void afficherEnTeteSection(String titre) {
//        int longueur = Math.max(titre.length() + 6, 50);
//        System.out.println("\n" + "╔" + "═".repeat(longueur - 2) + "╗");
//        System.out.println("║" + centrerTexte(titre, longueur - 2) + "║");
//        System.out.println("╚" + "═".repeat(longueur - 2) + "╝");
//    }
//
//    /**
//     * Affiche un message de succès
//     */
//    public static void afficherSucces(String message) {
//        System.out.println("\n✅ " + message);
//    }
//
//    /**
//     * Affiche un message d'erreur
//     */
//    public static void afficherErreur(String message) {
//        System.out.println("\n❌ " + message);
//    }
//
//    /**
//     * Affiche un message d'avertissement
//     */
//    public static void afficherAvertissement(String message) {
//        System.out.println("\n⚠️  " + message);
//    }
//
//    /**
//     * Affiche un message d'information
//     */
//    public static void afficherInfo(String message) {
//        System.out.println("\nℹ️  " + message);
//    }
//
//    /**
//     * Centre un texte dans une largeur donnée
//     */
//    private static String centrerTexte(String texte, int largeur) {
//        if (texte.length() >= largeur) {
//            return texte.substring(0, largeur);
//        }
//
//        int espaces = largeur - texte.length();
//        int espacesGauche = espaces / 2;
//        int espacesDroite = espaces - espacesGauche;
//
//        return " ".repeat(espacesGauche) + texte + " ".repeat(espacesDroite);
//    }
//
//    /**
//     * Tronque un texte à une longueur donnée
//     */
//    private static String tronquer(String texte, int longueurMax) {
//        if (texte == null) {
//            return "N/A";
//        }
//
//        if (texte.length() <= longueurMax) {
//            return texte;
//        }
//
//        return texte.substring(0, longueurMax - 3) + "...";
//    }
//
//    /**
//     * Affiche une barre de progression simple
//     */
//    public static void afficherBarreProgression(String operation, int pourcentage) {
//        int longueurBarre = 30;
//        int progression = (pourcentage * longueurBarre) / 100;
//
//        StringBuilder barre = new StringBuilder();
//        barre.append("[");
//        for (int i = 0; i < longueurBarre; i++) {
//            if (i < progression) {
//                barre.append("█");
//            } else {
//                barre.append("░");
//            }
//        }
//        barre.append("] ").append(pourcentage).append("%");
//
//        System.out.print("\r" + operation + ": " + barre.toString());
//
//        if (pourcentage == 100) {
//            System.out.println(" ✅");
//        }
//    }
//
//    /**
//     * Affiche un séparateur stylisé
//     */
//    public static void afficherSeparateur() {
//        System.out.println("\n" + "─".repeat(60));
//    }
//
//    /**
//     * Pause l'exécution et attend une entrée utilisateur
//     */
//    public static void attendreEntree() {
//        System.out.print("\nAppuyez sur Entrée pour continuer...");
//        try {
//            System.in.read();
//        } catch (Exception e) {
//            // Ignore
//        }
//    }
//}
