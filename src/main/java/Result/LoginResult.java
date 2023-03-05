package Result;

import Model.AuthToken;
import Request.LoginRequest;
import Request.RegisterRequest;

/**
 * for login handling result
 */
public class LoginResult {
    String message;
    boolean success;
    AuthToken userAuthtoken;
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

    public AuthToken getUserAuthtoken() {
        return userAuthtoken;
    }

    public void setUserAuthtoken(AuthToken userAuthtoken) {
        this.userAuthtoken = userAuthtoken;
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
