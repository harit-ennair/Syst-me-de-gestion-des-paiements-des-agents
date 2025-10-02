package controller;

import controller.controllerInterfaces.ResponsablesCONTROLLERinter;
import model.Agent;
import model.Paiement;
import model.enums.TypeAgent;
import model.enums.TypePaiement;
import services.ResponsablesSERVICE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ResponsablesController implements ResponsablesCONTROLLERinter {

    private ResponsablesSERVICE responsablesService;

    public ResponsablesController() {
        this.responsablesService = new ResponsablesSERVICE();
    }

    // ============ GESTION DES DÉPARTEMENTS (DIRECTEUR) ============

    @Override
    public void creerDepartement(String nom, String description) {
        try {
            responsablesService.creerDepartement(nom, description);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du département : " + e.getMessage());
        }
    }

    @Override
    public void modifierDepartement(int idDepartement, String nom, String description) {
        try {
            responsablesService.modifierDepartement(idDepartement, nom, description);
        } catch (Exception e) {
            System.err.println("Erreur lors de la modification du département : " + e.getMessage());
        }
    }

    @Override
    public void supprimerDepartement(int idDepartement) {
        try {
            responsablesService.supprimerDepartement(idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du département : " + e.getMessage());
        }
    }

    @Override
    public void associerResponsable(int idDepartement, int idResponsable) {
        try {
            responsablesService.associerResponsable(idDepartement, idResponsable);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'association du responsable : " + e.getMessage());
        }
    }

    // ============ GESTION DES AGENTS (RESPONSABLE) ============

    @Override
    public void ajouterAgent(String nom, String prenom, String email, String password,
                            TypeAgent typeAgent, int idDepartement) {
        try {
            responsablesService.ajouterAgent(nom, prenom, email, password, typeAgent, idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void modifierAgent(int idAgent, String nom, String prenom, String email,
                             TypeAgent typeAgent, int idDepartement) {
        try {
            responsablesService.modifierAgent(idAgent, nom, prenom, email, typeAgent, idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de la modification de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void supprimerAgent(int idAgent) {
        try {
            responsablesService.supprimerAgent(idAgent);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'agent : " + e.getMessage());
        }
    }

    @Override
    public void affecterAgentDepartement(int idAgent, int idDepartement) {
        try {
            responsablesService.affecterAgentDepartement(idAgent, idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'affectation de l'agent : " + e.getMessage());
        }
    }

    // ============ GESTION DES PAIEMENTS (RESPONSABLE) ============

    @Override
    public void ajouterSalaire(int idAgent, BigDecimal montant, String motif) {
        try {
            responsablesService.ajouterSalaire(idAgent, montant, motif);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du salaire : " + e.getMessage());
        }
    }

    @Override
    public void ajouterPrime(int idAgent, BigDecimal montant, String motif) {
        try {
            responsablesService.ajouterPrime(idAgent, montant, motif);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de la prime : " + e.getMessage());
        }
    }

    @Override
    public void ajouterBonus(int idAgent, BigDecimal montant, String motif) {
        try {
            responsablesService.ajouterBonus(idAgent, montant, motif);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du bonus : " + e.getMessage());
        }
    }

    @Override
    public void ajouterIndemnite(int idAgent, BigDecimal montant, String motif) {
        try {
            responsablesService.ajouterIndemnite(idAgent, montant, motif);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'indemnité : " + e.getMessage());
        }
    }

    // ============ CONSULTATION ET FILTRAGE ============

    @Override
    public List<Paiement> consulterPaiementsAgent(int idAgent) {
        try {
            return responsablesService.consulterPaiementsAgent(idAgent);
        } catch (Exception e) {
            System.err.println("Erreur lors de la consultation des paiements : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Paiement> consulterPaiementsDepartement(int idDepartement) {
        try {
            return responsablesService.consulterPaiementsDepartement(idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de la consultation des paiements du département : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Paiement> filtrerPaiements(int idDepartement, TypePaiement typePaiement,
                                          LocalDate dateDebut, LocalDate dateFin) {
        try {
            return responsablesService.filtrerPaiements(idDepartement, typePaiement, dateDebut, dateFin);
        } catch (Exception e) {
            System.err.println("Erreur lors du filtrage des paiements : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Paiement> identifierPaiementsInhabituels(int idDepartement) {
        try {
            return responsablesService.identifierPaiementsInhabituels(idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'identification des paiements inhabituels : " + e.getMessage());
            return null;
        }
    }

    // ============ UTILITAIRES ============

    @Override
    public List<Agent> obtenirAgentsDepartement(int idDepartement) {
        try {
            return responsablesService.obtenirAgentsDepartement(idDepartement);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des agents : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean verifierEligibiliteBonus(int idAgent) {
        try {
            return responsablesService.verifierEligibiliteBonus(idAgent);
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification d'éligibilité au bonus : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean verifierEligibiliteIndemnite(int idAgent) {
        try {
            return responsablesService.verifierEligibiliteIndemnite(idAgent);
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification d'éligibilité à l'indemnité : " + e.getMessage());
            return false;
        }
    }

    /**
     * Affiche un résumé complet des informations d'un département
     */
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
