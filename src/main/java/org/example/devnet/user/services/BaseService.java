package org.example.devnet.user.services;



import java.util.List;


public interface BaseService<T, TId>{


    List<T> findAll();


    T findById(TId id);

    T add(T entity);

    T modify(T t, TId id);

    void delete(TId id);
}
