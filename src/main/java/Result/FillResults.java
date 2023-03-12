package Result;

import DataAccess.DataAccessException;

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

    public void success(){
        setSuccess(true);
        setMessage("Fill succeeded");
    }
    public void fail(){
        setSuccess(false);
        setMessage("Failed because:" + "Not currently specified");
    }
    public void fail(DataAccessException e){
        setSuccess(false);
        setMessage("Failed because:" + e.toString() + ", " + e.returnMessage());
    }

}
