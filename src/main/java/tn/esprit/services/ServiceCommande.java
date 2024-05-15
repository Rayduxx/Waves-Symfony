package tn.esprit.services;

import tn.esprit.interfaces.ICommande;
import tn.esprit.models.Commande;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;

public class ServiceCommande implements ICommande<Commande> {

    private Connection cnx;

    public ServiceCommande() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void AddC(Commande commande) {
        String query = "INSERT INTO commande (idUser, idItem, total, dateC) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, commande.getIdUser());
            preparedStatement.setInt(2, commande.getIdItem());
            preparedStatement.setFloat(3, commande.getTotal());
            preparedStatement.setTimestamp(4, commande.getDateC());
            preparedStatement.executeUpdate();
            System.out.println("Commande ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Commande> getAll() {
        ArrayList<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande";
        try {
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.setIdUser(resultSet.getInt("idUser"));
                commande.setIdItem(resultSet.getInt("idItem"));
                commande.setTotal(resultSet.getFloat("total"));
                commande.setDateC(resultSet.getTimestamp("dateC"));
                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    public ArrayList<Commande> getCommandesUtilisateur(int userId) {
        ArrayList<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande WHERE idUser = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.setIdc(resultSet.getInt("idc"));
                commande.setIdUser(resultSet.getInt("idUser"));
                commande.setIdItem(resultSet.getInt("idItem"));
                commande.setTotal(resultSet.getFloat("total"));
                commande.setDateC(resultSet.getTimestamp("dateC"));
                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }


    @Override
    public void UpdateC(Commande commande) {
        String query = "UPDATE commande SET idUser=?, idItem=?, total=?, dateC=? WHERE idc=?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, commande.getIdUser());
            preparedStatement.setInt(2, commande.getIdItem());
            preparedStatement.setFloat(3, commande.getTotal());
            preparedStatement.setTimestamp(4, commande.getDateC());
            preparedStatement.setInt(5, commande.getIdc());
            preparedStatement.executeUpdate();
            System.out.println("Commande mise à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteC(Commande commande) {
        String query = "DELETE FROM commande WHERE idc=?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, commande.getIdc());
            preparedStatement.executeUpdate();
            System.out.println("Commande supprimée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
