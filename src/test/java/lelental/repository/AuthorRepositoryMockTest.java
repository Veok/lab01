package lelental.repository;

import lelental.domain.Author;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Paweł Lelental
 **/


@RunWith(MockitoJUnitRunner.class)
public class AuthorRepositoryMockTest {

    @Mock
    private Connection connectionMock;

    @Mock
    private PreparedStatement insertStatementMock;

    @Mock
    private PreparedStatement selectStatementMock;

    @Mock
    private PreparedStatement deleteStatementMock;

    @Mock
    private PreparedStatement findByIdStatementMock;

    private AuthorRepository authorRepository;

    @Before
    public void setup_db() throws SQLException {

        when(connectionMock.prepareStatement("INSERT INTO Author (name, date_of_creation) VALUES (?, ?)")).thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement("SELECT id, name, date_of_creation FROM Author")).thenReturn(selectStatementMock);
        when(connectionMock.prepareStatement("DELETE FROM Author WHERE id = ?")).thenReturn(deleteStatementMock);
        when(connectionMock.prepareStatement("SELECT (id,name,date_of_creation) FROM Author WHERE id = ?")).thenReturn(findByIdStatementMock);

        authorRepository = AuthorRepositoryFactory.getInstance2();
        authorRepository.setConnection(connectionMock);

        verify(connectionMock).prepareStatement("INSERT INTO Author (name, date_of_creation) VALUES (?, ?)");
        verify(connectionMock).prepareStatement("SELECT id, name, date_of_creation FROM Author");
        verify(connectionMock).prepareStatement("DELETE FROM Author WHERE id = ?");
        verify(connectionMock).prepareStatement("SELECT (id,name,date_of_creation) FROM Author WHERE id = ?");
    }

    @Test
    public void say_hello_repository() throws SQLException {
    }

    @Test
    public void insert_author() throws SQLException {
        when(insertStatementMock.executeUpdate()).thenReturn(1);
        Author author1 = new Author(2, "IRA", new Date(1987));
        assertEquals(true, authorRepository.insert(author1));
        verify(insertStatementMock, times(1)).setString(1, "IRA");
        verify(insertStatementMock, times(1)).setDate(2, new java.sql.Date(1987));
        verify(insertStatementMock).executeUpdate();
    }


    @Test
    public void delete_author_by_id() throws SQLException {
        Author author1 = new Author(2, "IRA", new Date(1987));
        authorRepository.insert(author1);
        when(insertStatementMock.executeUpdate()).thenReturn(1);
        verify(insertStatementMock, times(1)).setString(1, "IRA");
        verify(insertStatementMock, times(1)).setDate(2, new java.sql.Date(1987));
        authorRepository.delete(1);
        verify(deleteStatementMock).executeQuery();
    }

    @Test
    public void find_by_id() throws SQLException {
//        Author author1 = new Author(2, "IRA", new Date(1987));
//        authorRepository.insert(author1);
//        when(insertStatementMock.executeUpdate()).thenReturn(1);
//        verify(insertStatementMock, times(1)).setString(1, "IRA");
//        verify(insertStatementMock, times(1)).setDate(2, new java.sql.Date(1987));
//        authorRepository.findById(1);
//        when(selectStatementMock.executeQuery()).thenReturn((ResultSet) author1);
//        verify(selectStatementMock).executeQuery();
    }

    @Test
    public void update_author() throws SQLException {
    }


}
