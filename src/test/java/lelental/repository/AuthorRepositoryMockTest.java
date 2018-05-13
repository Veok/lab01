package lelental.repository;

import lelental.domain.Author;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Paweł Lelental
 **/


@RunWith(MockitoJUnitRunner.Silent.class)
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
    private PreparedStatement findById;
    @Mock
    private PreparedStatement update;

    @Mock
    ResultSet resultSet;

    private AuthorRepository authorRepository;

    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public boolean next() throws SQLException {
            if (i == 1) {
                return false;
            }
            i++;
            return true;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            if (columnLabel.equals("name")) {
                return "IRA";
            }
            return null;
        }

        @Override
        public java.sql.Date getDate(String columnLabel) throws SQLException {
            if (columnLabel.equals("date_of_creation")) {
                return new java.sql.Date(1987);
            }
            return null;
        }

        @Override
        public long getLong(String columnLabel) throws SQLException {
            if(columnLabel.equals("id")){
                return 2;
            }else{
                return 0;
            }
        }
    }

    @Before
    public void setup_db() throws SQLException {

        when(connectionMock.prepareStatement("INSERT INTO Author (name, date_of_creation) VALUES (?, ?)")).thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement("SELECT id, name, date_of_creation FROM Author")).thenReturn(selectStatementMock);
        when(connectionMock.prepareStatement("DELETE FROM Author WHERE id = ?")).thenReturn(deleteStatementMock);
        when(connectionMock.prepareStatement("SELECT id,name,date_of_creation FROM Author WHERE id = ?")).thenReturn(findById);
        when(connectionMock.prepareStatement("UPDATE Author SET name = ?, date_of_creation = ? WHERE id= ? ")).thenReturn(update);
        authorRepository = AuthorRepositoryFactory.getInstance2();
        authorRepository.setConnection(connectionMock);

        verify(connectionMock).prepareStatement("INSERT INTO Author (name, date_of_creation) VALUES (?, ?)");
        verify(connectionMock).prepareStatement("SELECT id, name, date_of_creation FROM Author");
        verify(connectionMock).prepareStatement("DELETE FROM Author WHERE id = ?");
        verify(connectionMock).prepareStatement("SELECT id,name,date_of_creation FROM Author WHERE id = ?");
        verify(connectionMock).prepareStatement("UPDATE Author SET name = ?, date_of_creation = ? WHERE id= ? ");
        when(selectStatementMock.executeQuery()).thenReturn(resultSet);
    }


    @Test(expected = NullPointerException.class)
    public void null_create_exception() {
        authorRepository.insert(null);
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
        assertEquals(true,authorRepository.delete(1));
        verify(deleteStatementMock).executeUpdate();
    }

    @Test
    public void find_by_id() throws SQLException {
        Author author1 = new Author(2, "IRA", new Date(1987));
        AbstractResultSet mockResultSet = mock(AbstractResultSet.class, Mockito.CALLS_REAL_METHODS);
        when(connectionMock.prepareStatement("SELECT id,name,date_of_creation FROM Author WHERE id = ?")
                .executeQuery())
                .thenReturn(mockResultSet);
        when(mockResultSet.next()).thenCallRealMethod();
        when(mockResultSet.getLong("id")).thenCallRealMethod();
        when(mockResultSet.getString("name")).thenCallRealMethod();
        when(mockResultSet.getDate("date_of_creation")).thenCallRealMethod();
        assertEquals(author1.getId(), authorRepository.findById(2).getId());
        assertEquals(author1.getName(), authorRepository.findById(2).getName());
        assertEquals(author1.getDateOfCreation(), authorRepository.findById(2).getDateOfCreation());
    }

    @Test
    public void update() throws SQLException {
        Author author1 = new Author(2, "IRA2", new Date(1987));
        when(update.executeUpdate()).thenReturn(1);
        assertEquals(true,authorRepository.update(author1));
        verify(update).executeUpdate();
    }

}
