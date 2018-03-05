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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/**
 * @author Paweł Lelental
 **/
public class AuthorRepositoryTest {

    private List<Author> authorsDb;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void create_db() {
        authorsDb = new ArrayList<>();
        Author author = new Author(1, "Rammstein", new Date(1988), new ArrayList<>());
        authorsDb.add(author);
        AuthorRepositoryFactory.getInstance().populateDb(authorsDb);
    }

    @Test
    public void say_hello_repository() {
        assertThat("I'm simple crud repo without db", CoreMatchers.containsString(AuthorRepositoryFactory.getInstance().sayWhoYouAre()));
    }

    @Test
    public void insert_authors_to_db_and_find_all() {
        List<Cd> iraCds = new ArrayList<>();
        List<Cd> rammsteinCds = new ArrayList<>();
        List<Cd> pidzamaCds = new ArrayList<>();

        Author author1 = new Author(2, "IRA", new Date(1987), iraCds);
        Author author2 = new Author(3, "Rammstein2", new Date(1994), rammsteinCds);
        Author author3 = new Author(4, "Pidżama Porno", new Date(1987), pidzamaCds);

        AuthorRepositoryFactory.getInstance().insert(author1);
        AuthorRepositoryFactory.getInstance().insert(author2);
        AuthorRepositoryFactory.getInstance().insert(author3);

        assertTrue(AuthorRepositoryFactory.getInstance().findById(2).getId() == 2 &&
                AuthorRepositoryFactory.getInstance().findById(1).getName().equals("IRA"));

        assertTrue(AuthorRepositoryFactory.getInstance().findById(3).getId() == 3 &&
                AuthorRepositoryFactory.getInstance().findById(1).getName().equals("Rammstein2"));

        assertTrue(AuthorRepositoryFactory.getInstance().findById(4).getId() == 4 &&
                AuthorRepositoryFactory.getInstance().findById(1).getName().equals("Pidżama Porno"));

        assertEquals(4, AuthorRepositoryFactory.getInstance().findAll());
    }


    @Test
    public void delete_author_by_id() {
        Author author = AuthorRepositoryFactory.getInstance().findById(1);
        AuthorRepositoryFactory.getInstance().delete(author);
        assertEquals(null, AuthorRepositoryFactory.getInstance().findByName("Rammstein"));
    }

    @Test
    public void find_by_id() {
        assertEquals("Rammstein", AuthorRepositoryFactory.getInstance().findById(1).getName());
    }

    @Test
    public void update_author() {
        Author author = AuthorRepositoryFactory.getInstance().findById(1);
        if (author == null) {
            exception.expect(ClassNotFoundException.class);
        }

        author.setName("ira");
        AuthorRepositoryFactory.getInstance().update(author);
        assertEquals("ira", AuthorRepositoryFactory.getInstance().findByName("ira").getName());
    }

    @After
    public void drop_author_table() {
        AuthorRepositoryFactory.getInstance().dropAuthorTable();
        assertEquals(null, AuthorRepositoryFactory.getInstance().findAll());
    }

}