package Result;

import DataAccess.DataAccessException;
import Model.AuthToken;
import Request.LoginRequest;

/**
 * for login handling result
 */
public class LoginResult {
    String message;
    boolean success;
    String authtoken;
    String username;
    String personID;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
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

    //    public AuthToken getMyAuthtoken() {
//        return myAuthtoken;
//    }
//
//    public void setMyAuthtoken(AuthToken myAuthtoken) {
//        this.myAuthtoken = myAuthtoken;
//    }

    public void success(){
       setMessage("Successfully Logged in");
       setSuccess(true);
    }
    public void fail(DataAccessException e){
        setMessage("Error: Failed due to :" + e.toString() + "," + e.returnMessage());
        setSuccess(false);
    }


    /**
     * The wonderful default constructor
     */
    public LoginResult(){
    }
}
