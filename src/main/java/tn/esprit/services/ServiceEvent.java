package tn.esprit.services;

import tn.esprit.interfaces.IEvent;
import tn.esprit.models.Event;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceEvent implements IEvent<Event> {
    private Connection cnx;
    public ServiceEvent() {cnx = MyDataBase.getInstance().getCnx();}

    @Override
    public void Add(Event event) {
        String qry = "INSERT INTO `event`( `nom_e`, `adr_e`,`descr`, `date` , `image`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, event.getNomE());
            stm.setString(2, event.getAdrE());
            stm.setString(3, event.getDesc());
            stm.setString(4, event.getDate());
            stm.setString(5, event.getImage());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Event> getAll() {
        ArrayList<Event> events = new ArrayList();
        String qry = "SELECT * FROM `event`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Event p = new Event();
                p.setEid(rs.getInt("id"));
                p.setNomE(rs.getString("nom_e"));
                p.setAdrE(rs.getString("adr_e"));
                p.setDesc(rs.getString("descr"));
                p.setDate(rs.getString("date"));
                p.setImage(rs.getString("image"));


                events.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    public String getNomById(int id) {
        String nom = null;
        String qry = "SELECT nom_e FROM event WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(qry);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nom = rs.getString("nom_e");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nom;
    }

    @Override
    public void Update(Event event) {
        try
        {
            String qry="UPDATE `event` SET `nom_e`=?,`adr_e`=?,`descr`=?,`date`=?,`image`=? WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, event.getNomE());
            stm.setString(2, event.getAdrE());
            stm.setString(3, event.getDesc());
            stm.setString(4, event.getDate());
            stm.setString(5, event.getImage());
            stm.setInt(6, event.getEid()); // Assurez-vous de définir l'ID de l'événement

            stm.executeUpdate();
            System.out.println("Modification effectué");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public int findEventId(String eventName, String eventDate) {
        int eventId = -1; // Initialisez à une valeur impossible ou utilisez un Optional<Integer>
        try {
            String qry = "SELECT id FROM event WHERE nom_e = ? AND date = ?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, eventName);
            stm.setString(2, eventDate);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                eventId = rs.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Gérer l'exception selon votre logique d'application
        }
        return eventId;
    }

    @Override
    public void Delete(Event event) {
        try
        {
            String qry="DELETE FROM `event` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, event.getEid());
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    public void supprimerParId(int id) {
        try
        {
            String qry="DELETE FROM `event` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    public Map<String, Integer> getOccurrencesParEvent() throws SQLException {
        String sql = "Select count(r.id) AS nbreservation,e.nom_e as nomevent  from reservation r JOIN event e ON r.id= e.id GROUP BY e.nom_e ";

        Statement st=cnx.createStatement();
        ResultSet rs=st.executeQuery(sql);
        Map<String,Integer> nmbruser=new HashMap<>();
        while(rs.next()){
            nmbruser.put(rs.getString("nomevent"),rs.getInt("nbreservation"));
        }
        return nmbruser;
    }
    }


