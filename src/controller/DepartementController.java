package controller;

import controller.controllerInterfaces.DepartementCONTROLLERinter;
import model.Agent;
import model.Departement;
import model.Paiement;
import services.DepartementSERVICE;
import services.PaiementSERVICE;

import java.util.List;

public class DepartementController implements DepartementCONTROLLERinter {

    private final DepartementSERVICE departementService;
    private final PaiementSERVICE paiementService;

    public DepartementController() {
        this.departementService = new DepartementSERVICE();
        this.paiementService = new PaiementSERVICE();
    }

    @Override
    public void creerDepartement(String nom, String description) {
        try {
            departementService.creerDepartement(nom, description);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du département : " + e.getMessage());
        }
    }

    @Override
    public void modifierDepartement(int idDepartement, String nom, String description) {
        try {
            departementService.modifierDepartement(idDepartement, nom, description);
        } catch (Exception e) {
            System.err.println("Erreur lors de la modification du département : " + e.getMessage());
        }
    }

    @Override
    public void supprimerDepartement(int idDepartement) {
        try {
            departementService.supprimerDepartement(idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du département : " + e.getMessage());
        }
    }

    @Override
    public void associerResponsable(int idDepartement, int idResponsable) {
        try {
            departementService.associerResponsable(idDepartement, idResponsable);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'association du responsable : " + e.getMessage());
        }
    }

    @Override
    public List<Departement> afficherTousLesDepartements() {
        try {
            List<Departement> departements = departementService.getAllDepartements();
                System.out.println("%-5s |  %-30s ".formatted(
                        "ID", "Departement"
                ));
            for (Departement d : departements) {
                System.out.println("%-5d |  %-30s ".formatted(
                        d.getIdDepartement(),
                        d.getNom()
                ));
            }
            return  departements;
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des départements : " + e.getMessage());
            return List.of();
        }
    }


    @Override
    public List<Agent> obtenirAgentsDepartement(int idDepartement) {
        try {
            return departementService.obtenirAgentsDepartement(idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des agents : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean verifierEligibiliteBonus(int idAgent) {
        try {
            return departementService.verifierEligibiliteBonus(idAgent);
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification d'éligibilité au bonus : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean verifierEligibiliteIndemnite(int idAgent) {
        try {
            return departementService.verifierEligibiliteIndemnite(idAgent);
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification d'éligibilité à l'indemnité : " + e.getMessage());
            return false;
        }
    }

    public List<Paiement> consulterPaiementsDepartement(int idDepartement) {
        try {
            List<Agent> agents = obtenirAgentsDepartement(idDepartement);
            if (agents == null || agents.isEmpty()) {
                return List.of();
            }

            return agents.stream()
                    .flatMap(agent -> paiementService.getHistoriquePaiements(agent.getIdAgent()).stream())
                    .toList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la consultation des paiements du département : " + e.getMessage());
            return List.of();
        }
    }


    public List<Paiement> identifierPaiementsInhabituels(int idDepartement) {
        try {
            List<Paiement> paiements = consulterPaiementsDepartement(idDepartement);
            if (paiements.isEmpty()) {
                return List.of();
            }

            double moyenne = paiements.stream()
                    .mapToDouble(Paiement::getMontant)
                    .average()
                    .orElse(0.0);

            double seuil = moyenne * 2;

            return paiements.stream()
                    .filter(p -> p.getMontant() > seuil)
                    .toList();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'identification des paiements inhabituels : " + e.getMessage());
            return List.of();
        }
    }


    public void afficherResumeDepartement(int idDepartement) {
        try {
            List<Agent> agents = obtenirAgentsDepartement(idDepartement);
            List<Paiement> paiements = consulterPaiementsDepartement(idDepartement);
            List<Paiement> paiementsInhabituels = identifierPaiementsInhabituels(idDepartement);

            System.out.println("\n=== RÉSUMÉ DU DÉPARTEMENT " + idDepartement + " ===");
            System.out.println("Nombre d'agents : " + (agents != null ? agents.size() : 0));
            System.out.println("Nombre de paiements : " + (paiements != null ? paiements.size() : 0));
            System.out.println("Paiements inhabituels détectés : " + (paiementsInhabituels != null ? paiementsInhabituels.size() : 0));

            if (paiements != null && !paiements.isEmpty()) {
                double totalMontant = paiements.stream().mapToDouble(Paiement::getMontant).sum();
                System.out.println("Montant total des paiements : " + totalMontant + " €");
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'affichage du résumé : " + e.getMessage());
        }
    }


}
