package tn.esprit.models;

public class Commentaire {
    private int  idComm,idUser;
    private String comment;
    private Poste poste;

    public Commentaire( Poste poste, int idComm, String comment,int idUser) {
        this.idComm = idComm;
        this.comment = comment;
        this.poste=poste;
        this.idUser=idUser;
    }
    public Commentaire( Poste poste, int idComm, String comment) {
        this.idComm = idComm;
        this.comment = comment;
        this.poste=poste;
    }

    public Commentaire() {
    }
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdComm() {
        return idComm;
    }

    public void setIdComm(int idComm) {
        this.idComm = idComm;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "idComm=" + idComm +
                ", comment='" + comment + '\'' +
                ", poste=" + poste +
                '}';
    }
}
