package Result;

import Model.Event;
import Request.EventRequest;

/**
 * Results of the events
 */
public class EventResult {

    String message; //Message only when it fails
    boolean success;
    Event singularEvent;
    Event[] eventList;

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

    public Event getSingularEvent() {
        return singularEvent;
    }

    public void setSingularEvent(Event singularEvent) {
        this.singularEvent = singularEvent;
    }

    public Event[] getEventList() {
        return eventList;
    }

    public void setEventList(Event[] eventList) {
        this.eventList = eventList;
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
