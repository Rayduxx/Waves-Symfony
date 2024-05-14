package tn.esprit.services;

import tn.esprit.interfaces.IService1;
import tn.esprit.models.Formation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import static jdk.internal.org.jline.utils.Colors.s;

public class FormationService implements IService1<Formation> {


        private final Connection cnx;
        private Statement stm;



        public FormationService() {
            cnx= MyDataBase.getInstance().getCnx();
        }


        public void add(Formation formation){
            String qry="INSERT INTO `formation` (`titre` , `description` , `affiche` , `video`) values (?,?,?,?)";
            try {
            PreparedStatement stm = null;
                stm = cnx.prepareStatement(qry);
            stm.setString(1,formation.getTitre());
            stm.setString(2,formation.getDescription());
            stm.setString(3,formation.getAffiche());
            stm.setString(4,formation.getVideo());

            stm.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



        }

        /*public void addPst(Sport s){
            String redquete="insert into personne (nom,description,image) values (?,?,?)";
            try {
                pst=conn.prepareStatement(redquete);
                pst.setString(1,s.getNom());
                pst.setString(2,s.getDescription());
                pst.setString(2,s.getImage());
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }*/

        @Override
        public void delete(Formation formation) {
            try {
                String qry = "DELETE FROM `formation` WHERE id=?";
                PreparedStatement smt = this.cnx.prepareStatement(qry);
                smt.setInt(1, formation.getId());
                smt.executeUpdate();
                System.out.println("Suppression Effectué");
            } catch (SQLException var4) {
                System.out.println(var4.getMessage());
            }

        }


        @Override
        public void update(Formation formation) {
            try {
                String qry = "UPDATE `formation` SET `titre`=?,`description`=?,`affiche`=?, `video`=? WHERE `id`=?";
                PreparedStatement stm = this.cnx.prepareStatement(qry);

                stm.setString(1, formation.getTitre());
                stm.setString(2, formation.getDescription());
                stm.setString(3, formation.getAffiche());
                stm.setString(4, formation.getVideo());
                stm.setInt(5, formation.getId());

                stm.executeUpdate();
                System.out.println("Modification effectué");
            } catch (Exception var4) {
                System.out.println(var4.getMessage());
            }

        }
    @Override
    public ArrayList<Formation> getAll(){

            ArrayList<Formation> formations = new ArrayList<>();
            String qry = "SELECT * FROM `formation`";
            try{
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(qry);
                while (rs.next()){
                    Formation f = new Formation();
                    f.setId(rs.getInt(1));
                    f.setTitre(rs.getString(2));
                    f.setDescription(rs.getString(3));
                    f.setAffiche(rs.getString(4));
                    f.setVideo(rs.getString(5));
                    formations.add(f);



                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return formations;
    }


        /*@Override
        public List<Sport> readAll() {
            List<Sport> list=new ArrayList<>();
            String requete="SELECT * FROM `sport`";

            try {
                ste=conn.createStatement();
                ResultSet rs= ste.executeQuery(requete);
                while(rs.next()){
                    list.add(new Sport(rs.getInt(1),rs.getString("nom"),rs.getString("description")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                //throw new RuntimeException(e);
            }

            return list;
        }*/



        @Override
        public Formation readById(int id) {
            String requete = "SELECT *  FROM `formation` WHERE `id`=?";
            List<Formation> list = new ArrayList<>();

            try {
                stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(requete);
                if (rs.next()) {
                    Formation f = new Formation();

                    f.setId(rs.getInt(1));
                    f.setTitre(rs.getString(2));
                    f.setDescription(rs.getString(3));
                    f.setAffiche(rs.getString(4));
                    f.setVideo(rs.getString(5));

                    list.add(f);
                }
                //list.add(new Sport(rs.getInt(1),rs.getString("nom"),rs.getString("description")));

            } catch (SQLException e) {
                //throw new RuntimeException(e);
                System.out.println(e.getMessage());
            }
            return null;
        }
    public List<Formation> triFormationtBytitre() {
        return getAll().stream().sorted((o1, o2) -> o1.getTitre().compareTo(o2.getTitre())).collect(Collectors.toList());
    }


    public List<Formation> recherchePartitre(String titre) {

        return getAll().stream()
                .filter(formation -> formation.getTitre().equalsIgnoreCase(titre))
                .collect(Collectors.toList());
    }

    public List<Formation> rechercheSport(String recherche) {
        return getAll().stream()
                .filter(e -> e.getTitre().toLowerCase().contains(recherche.toLowerCase()) ||
                        e.getDescription().toLowerCase().contains(recherche.toLowerCase()))
                .collect(Collectors.toList());
    }
}


