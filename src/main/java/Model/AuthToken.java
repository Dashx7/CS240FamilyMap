package Model;

import java.util.Objects;

/**
 * This is the Auth token that verifies who the user is
 * contains the authToken and their userName
 */
//This is the models, which are the "objects" for the main four things

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
    public AuthToken(String authToken, String userName) {
        this.authToken = authToken;
        this.userName = userName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(authToken, authToken1.authToken) && Objects.equals(userName, authToken1.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, userName);
    }
}
