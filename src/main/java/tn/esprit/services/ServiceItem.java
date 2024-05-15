package tn.esprit.services;

import tn.esprit.interfaces.Iitem;
import tn.esprit.models.Item;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;

public class ServiceItem implements Iitem<Item> {
    private Connection cnx;
    public ServiceItem() {cnx = MyDataBase.getInstance().getCnx();}

    @Override
    public void Add(Item item) {
        String qry = "INSERT INTO `item`(`titre`, `description`, `auteur`, `prix`) VALUES (?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, item.getTitre());
            stm.setString(2, item.getDescription());
            stm.setString(3, item.getAuteur());
            stm.setFloat(4, item.getPrix());
            stm.executeUpdate();

            ResultSet generatedKeys = stm.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setItemID(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Item> getAll() {
        ArrayList<Item> items = new ArrayList<>();
        String qry = "SELECT * FROM `item`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setTitre(rs.getString("titre"));
                item.setDescription(rs.getString("description"));
                item.setAuteur(rs.getString("auteur"));
                item.setPrix(rs.getFloat("prix"));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public void Update(Item item) {
        try
        {
            String qry="UPDATE `item` SET `titre`=?,`description`=?,`auteur`=?,`prix`=? WHERE `itemID`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, item.getTitre());
            stm.setString(2, item.getDescription());
            stm.setString(3, item.getAuteur());
            stm.setFloat(4, item.getPrix());
            stm.setInt(5, item.getItemID());
            stm.executeUpdate();
            System.out.println("Modification effectuée");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Delete(Item item) {
        try
        {
            String qry="DELETE FROM `item` WHERE itemID=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, item.getItemID());
            smt.executeUpdate();
            System.out.println("Suppression effectuée");
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public Item getItemById(int itemId) {
        Item item = null;
        String query = "SELECT * FROM `item` WHERE itemID = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("itemID");
                String titre = resultSet.getString("titre");
                String description = resultSet.getString("description");
                String auteur = resultSet.getString("auteur");
                float prix = resultSet.getFloat("prix");
                // Créer un nouvel objet Item avec les données récupérées
                item = new Item(id, titre, description, auteur, prix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
