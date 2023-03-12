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
    public void success(int numUsers, int numPersons, int numEvents){
        setSuccess(true);
        setMessage("Successfully added " + numUsers + " users, " +
                numPersons + " persons, and " + numEvents + " events to the database.");
    }
    public void failure(String error){
        setMessage("Error : " + error);
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
