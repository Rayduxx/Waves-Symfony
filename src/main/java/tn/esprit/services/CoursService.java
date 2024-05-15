package tn.esprit.services;
import tn.esprit.models.Cours;
import tn.esprit.models.Formation;
import tn.esprit.utils.MyDataBase;
import tn.esprit.interfaces.IService1;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import static jdk.internal.org.jline.utils.Colors.s;

public class CoursService implements IService1<Cours> {


    private final Connection cnx;
    private Statement stm;
    String sql = "";

    private Cours cours;



    public CoursService() {
        cnx= MyDataBase.getInstance().getCnx();
    }


    public void addCours(Cours cours, Formation formation) throws SQLException{
        sql ="INSERT INTO `cours` (id_cours,titre_cours ,duree_cours,id ) values (?,?,?,?)";
        try {


        PreparedStatement stm = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1,cours.getId_cours());
            stm.setString(2,cours.getTitre_cours());
        stm.setString(3,cours.getDuree_cours());
        stm.setInt(4,formation.getId());
        stm.executeUpdate();
        ResultSet rs = stm.getGeneratedKeys();
        if (rs.next()){
        cours.setId_cours(rs.getInt(1));

        }   System.out.println("Cours ajouté avec succés");
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }}




    public void supprimerCours (int id) {
            String qry = "DELETE FROM `cours` WHERE id_cours=?";
            try {
            PreparedStatement smt = this.cnx.prepareStatement(qry);
            smt.setInt(1, id);
                int rowsAffected = smt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Terrain supprimé avec succès.");
                } else {
                    System.out.println("Aucun terrain n'a été supprimé.");
                }
            } catch (SQLException ex) {
                System.out.println("Une erreur s'est produite lors de la suppression du terrain : " + ex.getMessage());
            }

    }



   /* public void updateCours(Cours cours, int id) {
            sql = "update terrain set titre_cours = ' " + cours.getTitre_cours() + " ', duree_cours = ' " + cours.getDuree_cours() + "', id = ' " + cours.getFormation().getId() + " ' where id_cours= " + id;
            try {
                Statement ste = cnx.createStatement();
                ste.executeUpdate(sql);
                System.out.println("Sinistre modifié avec succès.");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


        }*/



    @Override
    public void add(Cours cours) {

    }

    @Override
    public void delete(Cours cours) {

    }

    @Override
    public void update(Cours cours) {

    }

    @Override
    public ArrayList<Cours> getAll() {
        ArrayList<Cours> coursList = new ArrayList<>();
        String qry = "SELECT c.id_cours, c.titre_cours, c.duree_cours, f.id, f.titre " +
                "FROM cours c INNER JOIN formation f ON c.id = f.id";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Cours c = new Cours();
                c.setId_cours(rs.getInt("id_cours"));
                c.setTitre_cours(rs.getString("titre_cours"));
                c.setDuree_cours(rs.getString("duree_cours"));

                Formation f = new Formation();
                f.setId(rs.getInt("id"));
                f.setTitre(rs.getString("titre"));

                c.setFormation(f);
                coursList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coursList;
    }

    @Override
    public List<Cours> triFormationtBytitre() {
        return null;
    }

    public List<Cours> triFormationtBytitre_cours() {
        return getAll().stream().sorted((o1, o2) -> o1.getTitre_cours().compareTo(o2.getTitre_cours())).collect(Collectors.toList());
    }
    @Override
    public List<Formation> recherchePartitre(String titre) {
        return null;
    }

    @Override
    public Cours readById(int id) {
        return null;
    }
}



