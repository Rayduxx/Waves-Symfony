package tn.esprit.models;

public class Cours {
    private int id_cours;
    private String titre_cours;
    private String duree_cours;
    private Formation formation;


    public Cours() {
    }

    public Cours(int id_cours, String titre_cours, String duree_cours, Formation formation) {
        this.id_cours = id_cours;
        this.titre_cours = titre_cours;
        this.duree_cours = duree_cours;
        this.formation = formation;


    }

    public Cours(String titre_cours, String duree_cours, Formation formation) {
        this.titre_cours = titre_cours;
        this.duree_cours = duree_cours;
        this.formation = formation;


    }


    public Cours(String titre_cours, String duree_cours) {
        this.titre_cours = titre_cours;
        this.duree_cours = duree_cours;

    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }


    public String getTitre_cours() {
        return titre_cours;
    }

    public void setTitre_cours(String titre_cours) {
        this.titre_cours = titre_cours;
    }

    public String getDuree_cours() {
        return duree_cours;
    }

    public void setDuree_cours(String duree_cours) {
        this.duree_cours = duree_cours;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    @Override
    public String toString() {
        return "Cours{" +
                ", titre='" + titre_cours + '\'' +
                ", duree='" + duree_cours + '\'' +
                ", formation='" + formation + '\'' +
                '}';
    }
}


