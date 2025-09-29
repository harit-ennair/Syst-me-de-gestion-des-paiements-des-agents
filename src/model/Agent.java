package model;

import model.enums.TypeAgent;

public class Agent extends Personne {
    private int idAgent;
    private TypeAgent typeAgent;
    private int idDepartement;
    private Departement departement;


    // Constructeur avec paramètres de base
    public Agent(String nom, String prenom, String email, String password, TypeAgent typeAgent, int idDepartement) {
        super(nom, prenom, email, password);
        this.typeAgent = typeAgent;
        this.idDepartement = idDepartement;
    }

    public Agent(){


    }



    // Getters et Setters
    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
        // L'id agent est le même que l'id personne
        this.setIdPersonne(idAgent);
    }

    public TypeAgent getTypeAgent() {
        return typeAgent;
    }

    public void setTypeAgent(TypeAgent typeAgent) {
        this.typeAgent = typeAgent;
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
        if (departement != null) {
            this.idDepartement = departement.getIdDepartement();
        }
    }

    @Override
    public String toString() {
        return "Agent{" +
                "idAgent=" + idAgent +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", typeAgent=" + typeAgent +
                ", idDepartement=" + idDepartement +
                (departement != null ? ", departement='" + departement.getNom() + '\'' : "") +
                '}';
    }
}
