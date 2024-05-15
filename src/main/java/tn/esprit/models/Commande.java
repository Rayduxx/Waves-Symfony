package tn.esprit.models;
import java.sql.Timestamp;

public class Commande {
    private int idc;
    private int idUser;
    private int idItem;
    private float total;
    private Timestamp dateC;


    public Commande() {
    }
    
    public Commande(int idUser, int idItem, float total, Timestamp dateC) {
        this.idUser = idUser;
        this.idItem = idItem;
        this.total = total;
        this.dateC = dateC;
    }

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Timestamp getDateC() {
        return dateC;
    }

    public void setDateC(Timestamp dateC) {
        this.dateC = dateC;
    }
}
