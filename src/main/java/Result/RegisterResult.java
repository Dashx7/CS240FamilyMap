package Result;

import Model.AuthToken;
import Request.RegisterRequest;

/**
 * For registration handling result
 */
public class RegisterResult {
    String message;
    String authToken;
    String username;
    String personID;

    boolean success;
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * The wonderful default constructor
     */
    public RegisterResult(){
    }
//    public RegisterResult(RegisterRequest myRequest){
//        //setUserAuthToken(myRequest.);
//        this.username = myRequest.getUsername();
//        this.personID = my
//    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
