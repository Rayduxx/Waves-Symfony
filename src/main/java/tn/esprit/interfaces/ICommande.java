package tn.esprit.interfaces;
import java.util.ArrayList;

public interface ICommande<T> {
    void AddC(T t);
    ArrayList<T> getAll();
    void UpdateC(T t);
    void DeleteC(T t);
}
