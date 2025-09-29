package model;

public class Departement {
    private int idDepartement;
    private String nom;
    private String description;

    // Constructeur par défaut
    public Departement() {
    }

    // Constructeur avec paramètres
    public Departement(String nom) {
        this.nom = nom;
    }

    // Constructeur complet
    public Departement(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    // Getters et Setters
    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Departement{" +
                "idDepartement=" + idDepartement +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
