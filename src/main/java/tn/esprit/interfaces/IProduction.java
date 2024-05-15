package tn.esprit.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface IProduction<T> {
    void Add (T t);
    ArrayList<T> getAll();
    List<T> afficher();
    List<T> TriparTitre();
    List<T> TriparGenre();
    List<T> Rechreche(String recherche);
    void Update(T t);
    void Delete(T t);
    void DeleteByID(int id);
}
