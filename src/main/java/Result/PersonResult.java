package Result;

//import Request.PersonRequest;
import Request.RegisterRequest;

/**
 * looking up a Person result
 */
public class PersonResult {

    String message; //No message?
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
    public PersonResult(){
    }
    public void success(){
        //setMessage("Success");
        setSuccess(true);
    }

}
