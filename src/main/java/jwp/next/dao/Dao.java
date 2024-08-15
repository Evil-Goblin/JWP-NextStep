package jwp.next.dao;

import java.util.List;

public interface Dao<T, U> {

    T insert(T entity);

    T findById(U id);

    void delete(U id);

    List<T> findAll();
}
