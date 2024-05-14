package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Poste;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicePoste implements IService<Poste> {
  private Connection cnx ;
  public ServicePoste(){
      cnx =MyDataBase.getInstance().getCnx();
  }

    @Override
    public void add(Poste poste) {
        // ajouter une personne dans la bd
        //1 - req SQL done
        //2 - executer la req SQL done
        String qry = "INSERT INTO `poste`(`titre`, `artiste`, `genre`,`image`, `morceau` ,`description` ) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, poste.getTitre());
            stm.setString(2, poste.getArtiste());
            stm.setString(3, poste.getGenre());
            stm.setString(4, poste.getImage());
            stm.setString(5, poste.getMorceau());
            stm.setString(6, poste.getDescription());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }




    }

    @Override
    public ArrayList<Poste> getAll() {


        //retourner toute les perosnnes dans la bd
        //1- req SQL done
        //2 -execution de la req done
        // 3- remplire la liste done
        // 4 - retourner la liste done
        ArrayList<Poste> postes = new ArrayList();
        String qry ="SELECT * FROM `poste`";
        try {
            Statement stm = cnx.createStatement();
          ResultSet rs = stm.executeQuery(qry);
          while (rs.next()){
              Poste p = new Poste();
              p.setId(rs.getInt(1));
              p.setTitre(rs.getString(2));
              p.setArtiste(rs.getString(3));
              p.setGenre(rs.getString(4));
              p.setImage(rs.getString(5));
              p.setMorceau(rs.getString(6));
              p.setDescription(rs.getString(7));
              postes.add(p);
          }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return postes;
    }

    @Override
    public void update(Poste poste) {
        try {
            String qry = "UPDATE poste SET `titre`=?, `artiste`=?,`genre`=?,`image`=?,`morceau`=?,`description`=? WHERE `id_Poste`=?";
            PreparedStatement stm = this.cnx.prepareStatement(qry);

            stm.setString(1, poste.getTitre());
            stm.setString(2, poste.getArtiste());
            stm.setString(3, String.valueOf(poste.getGenre()));
            stm.setString(4, poste.getImage());
            stm.setString(5, poste.getMorceau());
            stm.setString(6, poste.getDescription());
            stm.setInt(7, poste.getId());

            stm.executeUpdate();
            System.out.println("Modification effectué");
        } catch (Exception var4) {
            System.out.println(var4.getMessage());
        }

    }

    @Override
    public void delete(Poste poste) {

        try {
            String qry = "DELETE FROM poste WHERE id_Poste=?";
            PreparedStatement smt = this.cnx.prepareStatement(qry);
            smt.setInt(1, poste.getId());
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }


    public List<Poste> recherchePoste(String recherche) {
        return getAll().stream()
                .filter(e -> e.getTitre().toLowerCase().contains(recherche.toLowerCase()) ||
                        e.getDescription().toLowerCase().contains(recherche.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Poste> triPostetBytitre() {
        return getAll().stream().sorted((o1, o2) -> o1.getTitre().compareTo(o2.getTitre())).collect(Collectors.toList());
    }
}
