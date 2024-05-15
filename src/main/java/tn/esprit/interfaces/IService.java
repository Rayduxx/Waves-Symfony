package tn.esprit.interfaces;

import java.util.ArrayList;

public interface IService<T> {

    void add (T t);
    ArrayList<T> getAll();
    void update(T t);
    void delete(T t);
//getOne getById

}
