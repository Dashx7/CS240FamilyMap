package Result;

import Request.EventRequest;

/**
 * Results of the events
 */
public class EventResult {

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
    public EventResult(){
    }
    /**
     * The wonderful non-default constructor
     * @param myRequest the request
     */


}
