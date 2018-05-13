package lelental.repository;

import lelental.domain.Author;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Paweł Lelental
 **/
public interface AuthorRepository {

    boolean insert(Author author1);

    boolean update(Author author) throws SQLException;

    Author findById(long id) throws SQLException;

    List<Author> findAll() throws SQLException;

    boolean delete(long id) throws SQLException;

    String sayWhoYouAre() throws SQLException;

    List<Author> findByName(String name) throws SQLException;

    void populateDb() throws SQLException;

    void dropAuthorTable() throws SQLException;

    Connection getConnection();

    boolean isDatabaseReady();

    void setConnection(Connection connection) throws SQLException;
}
