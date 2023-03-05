package Result;

import Model.AuthToken;
import Request.RegisterRequest;

/**
 * For registration handling result
 */
public class RegisterResult {
    String message;
    boolean success;
    AuthToken userAuthToken;

    public AuthToken getUserAuthToken() {
        return userAuthToken;
    }

    public void setUserAuthToken(AuthToken userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

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

    /**
     * The wonderful default constructor
     */
    public RegisterResult(){
    }
    /**
     * The wonderful non-default constructor
     * @param myRequest the RegisterRequest
     */
    public RegisterResult(RegisterRequest myRequest){

    }
}
