package Model;

/**
 * This is the Auth token that verifies who the user is
 * contains the authToken and their userName
 */
public class AuthToken {
    private String authToken;
    private String userName;

    /**
     * The wonderful default constructor
     */
    public AuthToken(){

    }
    /**
     * @param authToken
     */
    public AuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * @return gives you the authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
