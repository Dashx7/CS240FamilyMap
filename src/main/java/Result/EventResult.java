package Result;

import Model.Event;
import Request.EventRequest;

import java.util.ArrayList;
/**
 * Results of the events
 */
public class EventResult {


    ArrayList<Event> data;
    String message; //Message only when it fails
    boolean success;

    private String eventID;
    private String associatedUsername;
    private String personID;

    private float latitude;
    private float longitude;

    private String country;
    private String city;
    private String eventType;
    private int year;

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
        return data.get(0);
    }

    public void setSingularEvent(Event singularEvent) {
        this.eventID = singularEvent.getEventID();
        this.associatedUsername = singularEvent.getAssociatedUsername();
        this.personID = singularEvent.getPersonID();;
        this.latitude = singularEvent.getLatitude();;
        this.longitude = singularEvent.getLongitude();;
        this.year = singularEvent.getYear();;
        this.country = singularEvent.getCountry();;
        this.city = singularEvent.getCity();;
        this.eventType = singularEvent.getEventType();;
    }

    public ArrayList<Event> getEventList() {
        return data;
    }
    public Event getEvent() {
        if(!data.isEmpty()){
            return data.get(0);
        }
        else return null;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.data = new ArrayList<>();
        this.data = eventList;
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
