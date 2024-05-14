package tn.esprit.interfaces;

import tn.esprit.models.Formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IService1 <T>{

    void add(T t) throws SQLException;
    void delete(T t);


    void update(T t);

    ArrayList<T> getAll();
    //List<T> readAll();
     List<T> triFormationtBytitre();
    List<Formation> recherchePartitre(String titre);

    T readById(int id);
}
