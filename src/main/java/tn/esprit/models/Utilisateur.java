package tn.esprit.models;

import java.sql.Date;

public class Utilisateur {

    private int id , numtel, projects;
    private boolean is_verified;
    private Date created_at,birthday;
    private String country,nom , prenom, email, password, role, image;
    public static Utilisateur Current_User;

    public Utilisateur() {}

    public Utilisateur(int id, String nom, String prenom, String email, String password,int numtel, String role, String image, boolean is_verified, Date created_at, Date birthday, String country, int projects) {
        this.id = id;
        this.numtel = numtel;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.image = image;
        this.is_verified = is_verified;
        this.created_at = created_at;
        this.birthday = birthday;
        this.country = country;
        this.projects = projects;
    }
    public Utilisateur(int id, String nom, String prenom, String email, String password,int numtel, String role, String image, boolean is_verified, Date birthday, String country) {
        this.id = id;
        this.numtel = numtel;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.image = image;
        this.is_verified = is_verified;
        this.created_at = created_at;
        this.birthday = birthday;
        this.country = country;
        this.projects = projects;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getNumtel() {return numtel;}
    public void setNumtel(int numtel) {this.numtel = numtel;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    public static Utilisateur getCurrent_User() {return Current_User;}
    public static void setCurrent_User(Utilisateur Current_User) {Utilisateur.Current_User = Current_User;}

    public int getProjects() {return projects;}
    public void setProjects(int projects) {this.projects = projects;}

    public boolean isIs_verified() {return is_verified;}
    public void setIs_verified(boolean is_verified) {this.is_verified = is_verified;}

    public Date getCreated_at() {return created_at;}
    public void setCreated_at(Date created_at) {this.created_at = created_at;}

    public Date getBirthday() {return birthday;}
    public void setBirthday(Date birthday) {this.birthday = birthday;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", numtel=" + numtel +
                ", projects=" + projects +
                ", is_verified=" + is_verified +
                ", created_at=" + created_at +
                ", birthday=" + birthday +
                ", country='" + country + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
