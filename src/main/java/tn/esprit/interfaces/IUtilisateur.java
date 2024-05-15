package tn.esprit.interfaces;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public interface IUtilisateur<T> {
    void Add (T t);
    ArrayList<T> getAll();
    List<T> afficher();
    List<T> TriparNom();
    List<T> TriparEmail();
    List<T> Rechreche(String recherche);
    void Update(T t);
    void Delete(T t);
    void DeleteByID(int id);
}
