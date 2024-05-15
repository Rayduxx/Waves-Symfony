package tn.esprit.interfaces;

import java.util.ArrayList;

public interface Iitem<T> {

    void Add (T t);
    ArrayList<T> getAll();
    void Update(T t);
    void Delete(T t);

}
