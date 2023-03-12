package DataAccess;

import Model.AuthToken;

import java.sql.*;

/**
 * AuthToken Dao
 */
//Note on DAOs, they create a connection to database, allows for the sanitized sql statements
// They modify the models more or less
public class AuthTokenDao {
    private final Connection conn;

    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    public void insert(AuthToken authToken) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Authtoken (authtoken,username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUserName());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authToken into the database");
        }
    }
    public AuthToken find(String authTokenString) throws DataAccessException {
        ResultSet rs;
        AuthToken authToken;
        String sql = "SELECT * FROM Authtoken WHERE authtoken  = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authTokenString);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken();
                authToken.setAuthToken(rs.getString("authtoken"));
                authToken.setUserName(rs.getString("username"));
                return authToken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authToken in the database");
        }

    }

    public AuthToken find(String attribute, String type) throws DataAccessException {
        if(type != "authtoken" && type !="username"){
            System.out.println("Programmer missnamed");
            return null;
        }
        AuthToken authToken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE " + type + " = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attribute);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken();
                authToken.setAuthToken(rs.getString("authtoken"));
                authToken.setUserName(rs.getString("username"));
                return authToken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authToken in the database");
        }

    }

    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authToken table");
        }
    }
}
