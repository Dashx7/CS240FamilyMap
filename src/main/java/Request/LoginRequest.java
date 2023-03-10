package Request;

/**
 * Request to make a new login
 */
public class LoginRequest {
    String username;
    String password;
    /**
     * The wonderful default constructor
     */
    public LoginRequest(){
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
