package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.interfaces.IUtilisateur;
import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDataBase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur implements IUtilisateur<Utilisateur> {
    private Connection cnx;
    public ServiceUtilisateur() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void Add(Utilisateur user) {
        String qry = "INSERT INTO `user`(`email`, `name`, `prename`, `roles`, `password`, `phone`, `is_verified`, `birthday`, `image`, `created_at`, `country`, `projets`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getNom());
            stm.setString(3, user.getPrenom());
            stm.setString(4, user.getRole());
            stm.setString(5, user.getPassword());
            stm.setInt(6, user.getNumtel());
            stm.setBoolean(7, user.isIs_verified());
            stm.setDate(8, user.getBirthday());
            stm.setString(9, user.getImage());
            stm.setDate(10, user.getCreated_at());
            stm.setString(11, user.getCountry());
            stm.setInt(12, user.getProjects());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Utilisateur> afficher() {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT * FROM `user`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("name"));
                user.setPrenom(rs.getString("prename"));
                user.setRole(rs.getString("roles"));
                user.setPassword(rs.getString("password"));
                user.setNumtel(rs.getInt("phone"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setBirthday(rs.getDate("birthday"));
                user.setImage(rs.getString("image"));
                user.setCreated_at(rs.getDate("created_at"));
                user.setCountry(rs.getString("country"));
                user.setProjects(rs.getInt("projets"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public List<Utilisateur> TriparNom() {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT `id`, `email`, `name`, `prename`, `roles`, `phone`, `is_verified`, `birthday`, `image`, `created_at`, `country`, `projets` FROM `user` ORDER BY `nom`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("name"));
                user.setPrenom(rs.getString("prename"));
                user.setRole(rs.getString("roles"));
                user.setNumtel(rs.getInt("phone"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setBirthday(rs.getDate("birthday"));
                user.setImage(rs.getString("image"));
                user.setCreated_at(rs.getDate("created_at"));
                user.setCountry(rs.getString("country"));
                user.setProjects(rs.getInt("projets"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public List<Utilisateur> TriparEmail() {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT `id`, `email`, `name`, `prename`, `roles`, `phone`, `is_verified`, `birthday`, `image`, `created_at`, `country`, `projets` FROM `user` ORDER BY `email`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("name"));
                user.setPrenom(rs.getString("prename"));
                user.setRole(rs.getString("roles"));
                user.setNumtel(rs.getInt("phone"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setBirthday(rs.getDate("birthday"));
                user.setImage(rs.getString("image"));
                user.setCreated_at(rs.getDate("created_at"));
                user.setCountry(rs.getString("country"));
                user.setProjects(rs.getInt("projets"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public List<Utilisateur> Rechreche(String recherche) {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT `id`, `email`, `name`, `prename`, `roles`, `phone`, `is_verified`, `birthday`, `image`, `created_at`, `country`, `projets` FROM `user` WHERE `nom` LIKE '%" + recherche + "%' OR `prenom` LIKE '%" + recherche + "%' OR `email`LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("name"));
                user.setPrenom(rs.getString("prename"));
                user.setRole(rs.getString("roles"));
                user.setNumtel(rs.getInt("phone"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setBirthday(rs.getDate("birthday"));
                user.setImage(rs.getString("image"));
                user.setCreated_at(rs.getDate("created_at"));
                user.setCountry(rs.getString("country"));
                user.setProjects(rs.getInt("projets"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override //manich nestaamel fiha
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> users = new ArrayList();
        String qry = "SELECT * FROM `user`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setNom(rs.getString("name"));
                user.setPrenom(rs.getString("prename"));
                user.setRole(rs.getString("roles"));
                user.setNumtel(rs.getInt("phone"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setBirthday(rs.getDate("birthday"));
                user.setImage(rs.getString("image"));
                user.setCreated_at(rs.getDate("created_at"));
                user.setCountry(rs.getString("country"));
                user.setProjects(rs.getInt("projets"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void Update(Utilisateur user) {
        try {
            String qry = "UPDATE `user` SET `email`=?,`name`=?,`prename`=?,`roles`=?,`password`=?,`phone`=?,`is_verified`=?,`birthday`=?,`image`=?,`country`=? WHERE  `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getNom());
            stm.setString(3, user.getPrenom());
            stm.setString(4, user.getRole());
            stm.setString(5, user.getPassword());
            stm.setInt(6, user.getNumtel());
            stm.setBoolean(7, user.isIs_verified());
            stm.setDate(8, user.getBirthday());
            stm.setString(9, user.getImage());
            stm.setString(10, user.getCountry());
            stm.setInt(11, user.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Delete(Utilisateur user) {
        try {
            String qry = "DELETE FROM `user` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, user.getId());
            smt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void DeleteByID(int id) {
        try {
            String qry = "DELETE FROM `user` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean updatePassword(String email, String newPassword) {
        String req = "UPDATE `user` SET `password` = ? WHERE `email` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(newPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hash) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String NewHashedPassword = hexString.toString();
            ps.setString(1, NewHashedPassword);
            ps.setString(2, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUserExists(String email) {
        String req = "SELECT count(1) FROM `user` WHERE `email`=?";
        boolean exists = false;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                exists = res.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking if user exists: " + e.getMessage());
        }
        return exists;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*\\.?[a-zA-Z0-9_+&*-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isValidPhoneNumber(int numTel) {
        String numTelStr = String.valueOf(numTel);
        return numTelStr.length() == 8;
    }

}