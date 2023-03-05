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

    public void setSinglePerson(boolean singlePerson) {
        this.singlePerson = singlePerson;
    }

    public Event getMyEvent() {
        return myEvent;
    }

    public void setMyEvent(Event myEvent) {
        this.myEvent = myEvent;
    }


    public AuthToken getUserAuthToken() {
        return userAuthToken;
    }

    public void setUserAuthToken(AuthToken userAuthToken) {
        this.userAuthToken = userAuthToken;
    }
    /**
     * The wonderful default constructor
     */
    public EventRequest(){
    }
}
