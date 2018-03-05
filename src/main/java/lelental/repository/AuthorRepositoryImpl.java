package lelental.repository;

import lelental.domain.Author;

import java.util.List;

/**
 * @author Paweł Lelental
 **/
public class AuthorRepositoryImpl implements  AuthorRepository {

    private List<Author> authors;

    @Override
    public void insert(Author author1) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Author author) {

    }

    @Override
    public Author findById(int id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public void delete(Author author) {

    }

    @Override
    public String sayWhoYouAre() {
        return null;
    }

    @Override
    public Author findByName(String name) {
        return null;
    }

    @Override
    public void populateDb(List<Author> authorsDb) {
        authors = authorsDb;
    }

    @Override
    public void dropAuthorTable() {

    }
}
