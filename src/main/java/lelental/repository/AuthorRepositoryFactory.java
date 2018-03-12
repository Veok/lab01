package lelental.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Paweł Lelental
 **/
public class AuthorRepositoryFactory {


    public static AuthorRepository getInstance(String connectionUrl) throws SQLException {
        return new AuthorRepositoryImpl(DriverManager.getConnection(connectionUrl));
    }
}
