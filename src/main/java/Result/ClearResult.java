package Result;

/**
 * Clearing database
 */
//Returns the results of a https request through a message and its success
public class ClearResult {
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

    String message;
    boolean success;
    /**
     * The wonderful default constructor
     */
    public ClearResult(){
        clear();
    }
    void clear(){

    }
}
