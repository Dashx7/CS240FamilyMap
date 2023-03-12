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
    AuthToken myAuthtoken;
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

    public AuthToken getMyAuthtoken() {
        return myAuthtoken;
    }

    public void setMyAuthtoken(AuthToken myAuthtoken) {
        this.myAuthtoken = myAuthtoken;
    }

    public void success(){
       setMessage("Successfully Logged in");
       setSuccess(true);
    }
    public void fail(DataAccessException e){
        setMessage("Failed due to :" + e.toString() + "," + e.returnMessage());
        setSuccess(false);
    }


    /**
     * The wonderful default constructor
     */
    public LoginResult(){
    }
}
