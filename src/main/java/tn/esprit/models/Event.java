package tn.esprit.models;

import java.time.LocalDate;
import java.util.Date;

public class Event {

    private int Eid  ;
    private String nomE , adrE, desc, date,image;


    public Event() {
    }

    public Event(int eid, String nomE, String adrE, String desc, String date,String image) {
        this.Eid = eid;
        this.nomE = nomE;
        this.adrE = adrE;
        this.desc = desc;
        this.date = date;
        this.image = image;
    }
    public int getEid() {
        return Eid;
    }

    public void setEid(int eid) {
        Eid = eid;
    }

    public String getNomE() {
        return nomE;
    }

    public void setNomE(String nomE) {
        this.nomE = nomE;
    }

    public String getAdrE() {
        return adrE;
    }

    public void setAdrE(String adrE) {
        this.adrE = adrE;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Eid=" + Eid +
                ", nomE='" + nomE + '\'' +
                ", adrE='" + adrE + '\'' +
                ", desc='" + desc + '\'' +
                ", date=" + date +
                '}';
    }
}
