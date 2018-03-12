package lelental.repository;

import lelental.domain.Author;
import lelental.domain.Cd;
import lelental.domain.Song;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Paweł Lelental
 **/
@RunWith(MockitoJUnitRunner.class)
public class AuthorRepositoryTest {

    private List<Author> authorsDb;
    private AuthorRepository authorRepository;


    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public AuthorRepositoryTest() throws SQLException {
        String dbConnectionURL = "jdbc:hsqldb:hsql://localhost/";
        authorRepository = AuthorRepositoryFactory.getInstance(dbConnectionURL);
    }

    @Before
    public void create_db() throws SQLException {
        authorRepository = AuthorRepositoryFactory.getInstance("jdbc:hsqldb:hsql://localhost/");
//        when(connectionMock.prepareStatement("INSERT INTO Author (name, date_of_creation) VALUES (?, ?)"))
//                .thenReturn(insertStatementMock);
//        when(connectionMock.prepareStatement("SELECT id, name, date_of_creation FROM Author"))
//                .thenReturn(selectStatementMock);
//
//        verify(connectionMock).prepareStatement("INSERT INTO Author (name, date_of_creation) VALUES (?, ?)");
//        verify(connectionMock).prepareStatement("SELECT id, name, date_of_creation FROM Author");
    }

    @Test
    public void say_hello_repository()  {
        assertThat("I'm simple crud repo without db", CoreMatchers.containsString(authorRepository.sayWhoYouAre()));
    }

    @Test
    public void insert_authors_to_db_and_find_all() throws SQLException {
        Author author1 = new Author(2, "IRA", new Date(1987));
        Author author2 = new Author(3, "Rammstein2", new Date(1994));
        Author author3 = new Author(4, "Pidżama Porno", new Date(1987));

        authorRepository.insert(author1);
        authorRepository.insert(author2);
        authorRepository.insert(author3);

        assertTrue(authorRepository.findById(2).getId() == 2 &&
                authorRepository.findById(1).getName().equals("IRA"));

        assertTrue(authorRepository.findById(3).getId() == 3 &&
                authorRepository.findById(1).getName().equals("Rammstein2"));

        assertTrue(authorRepository.findById(4).getId() == 4 &&
                authorRepository.findById(1).getName().equals("Pidżama Porno"));

        assertEquals(4, authorRepository.findAll());
    }


    @Test
    public void delete_author_by_id() throws SQLException {
        Author author = authorRepository.findById(1);
        authorRepository.delete(author);
        assertEquals(null, authorRepository.findByName("Rammstein"));
    }

    @Test
    public void find_by_id() throws SQLException {
        assertEquals("Rammstein", authorRepository.findById(1).getName());
    }

    @Test
    public void update_author() throws SQLException {
        Author author = authorRepository.findById(2);
        if (author == null) {
            exception.expect(ClassNotFoundException.class);
        }

        author.setName("ira");
        authorRepository.update(author);
        assertEquals("ira", authorRepository.findByName("ira").getName());
    }

    @After
    public void drop_author_table() throws SQLException {
        authorRepository.dropAuthorTable();
     //   assertEquals(null, authorRepository.findAll());
        exception.expect(SQLException.class);

    }

}