package DataAccess;

public class DataAccessException extends Throwable {
    String message;
    public DataAccessException(String message) {
        this.message = message;
    }
    public String returnMessage(){
        return message;
    }
}
