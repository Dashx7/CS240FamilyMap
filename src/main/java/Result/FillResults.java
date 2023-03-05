package Result;

import Request.FillRequest;

/**
 * For populating the database I think
 */
public class FillResults {
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

    /**
     * The wonderful default constructor
     */
    public FillResults(){
    }
    /**
     * The wonderful non-default constructor
     * @param myRequest the request
     */
    public FillResults(FillRequest myRequest){
    }

}
