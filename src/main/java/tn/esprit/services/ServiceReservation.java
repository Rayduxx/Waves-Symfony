package tn.esprit.services;


import tn.esprit.interfaces.IReservation;
import tn.esprit.models.Event;
import tn.esprit.models.Reservation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;


public class ServiceReservation implements IReservation<Reservation> {

    private static Connection cnx;
    public ServiceReservation() {cnx = MyDataBase.getInstance().getCnx();}

    @Override
    public void Add(Reservation reservation) {
        String qry = "INSERT INTO `reservation`( `date_reservation`, `statut`,`Eid`,`Nom`,`Prenom`,`NbPersonne`,`Email`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);

            stm.setString(1, reservation.getDate_reservation());
            stm.setString(2, reservation.getStatut());
            stm.setInt(3,reservation.getId_evenement());
            stm.setString(4, reservation.getNom());
            stm.setString(5, reservation.getPrenom());
            stm.setInt(6,reservation.getNbPersonne());
            stm.setString(7, reservation.getEmail());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public  ArrayList<Reservation> getAll() {
        ArrayList<Reservation> reservations = new ArrayList();
        String qry = "SELECT * FROM `reservation`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Reservation p = new Reservation();
                p.setDate_reservation(rs.getString("date_reservation"));
                p.setStatut(rs.getString("statut"));
                p.setId_evenement(rs.getInt("Eid"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("Prenom"));
                p.setNbPersonne(rs.getInt("NbPersonne"));
                p.setEmail(rs.getString("Email"));
                reservations.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    @Override
    public void Update(Reservation reservation) {
        try
        {
            String qry="UPDATE `reservation` SET `date_reservation`=?,`statut`=? WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, reservation.getDate_reservation());
            stm.setString(2, reservation.getStatut());

            stm.setInt(3, reservation.getId());
            stm.executeUpdate();
            System.out.println("Modification effectué");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Delete(Reservation reservation) {
        try
        {
            String qry="DELETE FROM `reservation` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, reservation.getId());
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }


}
