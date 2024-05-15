package tn.esprit.services;

import tn.esprit.controllers.CardCommController;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Poste;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommentaire {
    private Connection cnx;

    public ServiceCommentaire() {
        cnx = MyDataBase.getInstance().getCnx();

    }

    String sql = "";

    public void addComm(Commentaire commentaire, Poste poste) {
        sql = "INSERT INTO `commentaire`(id_Poste, idComm, comment) VALUES (?,?,?)";
        try {

            PreparedStatement stm = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, poste.getId());
            stm.setInt(2, commentaire.getIdComm());
            stm.setString(3, commentaire.getComment());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                commentaire.setIdComm(rs.getInt(1));
            }
            System.out.println("commentaire ajouteé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void addComm2(Commentaire commentaire, Poste poste, int idUser) {
        sql = "INSERT INTO `commentaire`(id_Poste, idComm, comment,idUser) VALUES (?,?,?,?)";
        try {

            PreparedStatement stm = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, poste.getId());
            stm.setInt(2, commentaire.getIdComm());
            stm.setString(3, commentaire.getComment());
            stm.setInt(4, idUser);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                commentaire.setIdComm(rs.getInt(1));
            }
            System.out.println("commentaire ajouteé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public ArrayList<Commentaire> getIcom(int idComm) {
        ArrayList<Commentaire> commentaires = new ArrayList();
        String qry = "SELECT c.*, p.titre FROM commentaire c inner join poste p on c.id_Poste = p.id_Poste";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Commentaire c = new Commentaire(new Poste(),22,"lol");
                c.setIdComm(rs.getInt(2));
                Poste p = new Poste();
                p.setId(rs.getInt(1));
                p.setTitre(rs.getString("titre"));
                c.setPoste(p);
                c.setComment(rs.getString(3));
                commentaires.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commentaires;
    }
    public ArrayList<Commentaire> getAll() {
        ArrayList<Commentaire> commentaires = new ArrayList();
        String qry = "SELECT c.*, p.titre FROM commentaire c inner join poste p on c.id_Poste = p.id_Poste";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Commentaire c = new Commentaire(new Poste(),22,"lol");
                c.setIdComm(rs.getInt(2));
                Poste p = new Poste();
                p.setId(rs.getInt(1));
                p.setTitre(rs.getString("titre"));
                c.setPoste(p);
                c.setComment(rs.getString(3));
                commentaires.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commentaires;
    }
    public void VerifComUser(int IDcom, int IDuser) {
        String qry = "SELECT * FROM commentaire WHERE idComm = ? AND idUser = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(qry);
            pstmt.setInt(1, IDcom);
            pstmt.setInt(2, IDuser);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                CardCommController CCC = new CardCommController();
                CCC.suppbtn.setVisible(true);
                CCC.modbtn.setVisible(true);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void update(Commentaire commentaire) {
        try
        {
            String qry="UPDATE `commentaire` SET `comment`=? WHERE `idComm`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, commentaire.getComment());
            stm.setInt(2, commentaire.getIdComm());
            stm.executeUpdate();
            System.out.println("Modification effectué");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    public void delete(Commentaire commentaire) {
        try
        {
            if (commentaire != null) {
                String qry="DELETE FROM `commentaire` WHERE idComm=?";
                PreparedStatement smt = cnx.prepareStatement(qry);
                smt.setInt(1, commentaire.getIdComm());
                smt.executeUpdate();
                System.out.println("Suppression Effectué");
            } else {
                System.out.println("Suppression Impossible");
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public Commentaire readById(int idComm) {
        String qry = "SELECT *  FROM commentaire WHERE `idComm`=?";
        List<Commentaire> list = new ArrayList<>();

        try {
            PreparedStatement stm =  cnx.prepareStatement(qry);

            ResultSet rs = stm.executeQuery(qry);
            if (rs.next()) {
                Commentaire s = new Commentaire(new Poste(),22,"lol");
                s.setIdComm(rs.getInt(1));
                s.setComment(rs.getString(3));



                list.add(s);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Commentaire> getAllCommentairesByPoste(Poste poste) {
        List<Commentaire> commentaires = new ArrayList<>();
        if (poste == null) {
            System.out.println("Le poste spécifié est null.");
            return commentaires;
        }

        final String COL_ID_POSTE = "id_Poste";
        String sql = "SELECT * FROM commentaire WHERE " + COL_ID_POSTE + " = ?";

        try (PreparedStatement stm = cnx.prepareStatement(sql)) {
            stm.setInt(1, poste.getId());
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Commentaire commentaire = new Commentaire();
                    commentaire.setComment(rs.getString("comment"));
                    commentaire.setPoste(poste); // Associer le poste au commentaire
                    commentaires.add(commentaire);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des commentaires : " + ex.getMessage());
        }
        return commentaires;
    }



  /*  public List<Poste> triPostetBytitre() {
        return getAll().stream().sorted((o1, o2) -> o1.getPoste().getId().compareTo(o2.getPoste().getId())).collect(Collectors.toList());
    }*/

}


