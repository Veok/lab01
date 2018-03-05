package lelental.repository;

import lelental.domain.Author;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
public interface AuthorRepository {

    void insert(Author author1);

    void delete(int id);

    void update(Author author);

    Author findById(int id);

    List<Author> findAll();

    void delete(Author author);

    String sayWhoYouAre();

    Author findByName(String name);

    void populateDb();

    void dropAuthorTable();
}
