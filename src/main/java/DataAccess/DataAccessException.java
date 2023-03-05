package DataAccess;

public class DataAccessException extends Throwable {
    String message;
    DataAccessException(String message) {
        this.message = message;
    }
}
