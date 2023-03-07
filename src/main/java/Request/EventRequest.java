package Request;

import Model.AuthToken;
import Model.Event;

/**
 * Event Requests
 */
//Is a request to the server to do something
public class EventRequest {
    AuthToken userAuthToken;
    boolean singlePerson;
    Event myEvent = new Event();

    public boolean isSinglePerson() {
        return singlePerson;
    }

   /**
     * The wonderful default constructor
     */
    public EventRequest(){
    }
}
