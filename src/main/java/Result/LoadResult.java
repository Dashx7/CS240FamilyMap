package Result;

import Model.AuthToken;
import Request.LoadRequest;
import Request.LoginRequest;

/**
 * Load result
 */
public class LoadResult {
    String message;
    boolean success;
    /**
     * The wonderful default constructor
     */
    public LoadResult(){
    }
    /**
     * The wonderful non-default constructor
     * @param myRequest the login request
     */
    public LoadResult(LoadRequest myRequest){
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
}
