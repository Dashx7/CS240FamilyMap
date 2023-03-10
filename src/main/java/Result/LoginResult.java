package Result;

import DataAccess.DataAccessException;
import Request.LoginRequest;

/**
 * for login handling result
 */
public class LoginResult {
    String message;
    boolean success;
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

    public void success(){
       setMessage("Successfully Logged in");
       setSuccess(true);
    }
    public void fail(DataAccessException e){
        setMessage("Failed due to :" + e.toString());
        setSuccess(false);
    }


    /**
     * The wonderful default constructor
     */
    public LoginResult(){
    }
    /**
     * The wonderful non-default constructor
     * @param myRequest the login request
     */
    public LoginResult(LoginRequest myRequest){

    }
}
