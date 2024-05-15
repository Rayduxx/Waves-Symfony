package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.interfaces.IProduction;
import tn.esprit.models.Production;
import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDataBase;
import tn.esprit.utils.SessionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduction implements IProduction<Production> {
    private Connection cnx;

    public ServiceProduction() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void Add(Production prod) {
        String qry = "INSERT INTO `production`(`nom`, `description`, `genre`, `moodtag`, `cover`, `idUser`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, prod.getNom());
            stm.setString(2, prod.getDesc());
            stm.setString(3, prod.getGenre());
            stm.setString(4, prod.getTags());
            stm.setString(5, prod.getCover());
            stm.setInt(6, SessionManager.getId_user());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Production> getAll() {
        ArrayList<Production> production = new ArrayList();
        String qry = "SELECT * FROM `production`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Production prod = new Production();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setDesc(rs.getString("description"));
                prod.setGenre(rs.getString("genre"));
                prod.setTags(rs.getString("moodtag"));
                prod.setCover(rs.getString("cover"));
                prod.setIdUser(rs.getInt("idUser"));
                production.add(prod);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return production;
    }

    @Override
    public void Update(Production prod) {
        try {
            String qry = "UPDATE `production` SET `nom`=?,`description`=?,`genre`=?,`moodtag`=?,`cover`=?,`idUser`=? WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, prod.getNom());
            stm.setString(2, prod.getDesc());
            stm.setString(3, prod.getGenre());
            stm.setString(4, prod.getTags());
            stm.setString(5, prod.getCover());
            stm.setInt(6, prod.getIdUser());
            stm.setInt(7, prod.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void Update2(Production prod) {
        try {
            String qry = "UPDATE `production` SET `nom`=?,`description`=?,`genre`=?,`moodtag`=?,`cover`=? WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, prod.getNom());
            stm.setString(2, prod.getDesc());
            stm.setString(3, prod.getGenre());
            stm.setString(4, prod.getTags());
            stm.setString(5, prod.getCover());
            stm.setInt(6, prod.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Delete(Production prod) {
        try {
            String qry = "DELETE FROM `production` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, prod.getId());
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void DeleteByID(int id) {
        try {
            String qry = "DELETE FROM `production` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public List<Production> afficher() {
        List<Production> prods = new ArrayList<>();
        String sql = "SELECT * FROM `production`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Production prod = new Production();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setDesc(rs.getString("description"));
                prod.setGenre(rs.getString("genre"));
                prod.setTags(rs.getString("moodtag"));
                prod.setCover(rs.getString("cover"));
                prod.setIdUser(rs.getInt("idUser"));
                prods.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prods;
    }
    @Override
    public List<Production> TriparTitre() {
        List<Production> prods = new ArrayList<>();
        String sql = "SELECT * FROM `production` ORDER BY `nom`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Production prod = new Production();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setDesc(rs.getString("description"));
                prod.setGenre(rs.getString("genre"));
                prod.setTags(rs.getString("moodtag"));
                prod.setCover(rs.getString("cover"));
                prod.setIdUser(rs.getInt("idUser"));
                prods.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prods;
    }

    @Override
    public List<Production> TriparGenre() {
        List<Production> prods = new ArrayList<>();
        String sql = "SELECT * FROM `production` ORDER BY `genre`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Production prod = new Production();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setDesc(rs.getString("description"));
                prod.setGenre(rs.getString("genre"));
                prod.setTags(rs.getString("moodtag"));
                prod.setCover(rs.getString("cover"));
                prod.setIdUser(rs.getInt("idUser"));
                prods.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prods;
    }
    @Override
    public List<Production> Rechreche(String recherche) {
        List<Production> prods = new ArrayList<>();
        String sql = "SELECT * FROM `production` WHERE `nom` LIKE '%" + recherche + "%' OR `moodtag` LIKE '%" + recherche + "%' OR `genre`LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Production prod = new Production();
                prod.setId(rs.getInt("id"));
                prod.setNom(rs.getString("nom"));
                prod.setDesc(rs.getString("description"));
                prod.setGenre(rs.getString("genre"));
                prod.setTags(rs.getString("moodtag"));
                prod.setCover(rs.getString("cover"));
                prod.setIdUser(rs.getInt("idUser"));
                prods.add(prod);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prods;
    }
}