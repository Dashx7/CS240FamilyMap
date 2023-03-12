package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Request.EventRequest;
import Result.EventResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
//import Request.PersonRequest;


/**
 * looking up a Event service
 */
public class EventService {
    /**
     * The wonderful default constructor
     */
    private EventDao myEventDao;
    Database myDatabase= new Database();;
    private PersonService myPersonService;
    Event singularEvent;
    List<Event> listOfEvents = new ArrayList<>();
    Event [] eventArray;
    EventResult myResult = new EventResult();


    public void EventServiceAll(AuthToken theAuthToken) {
        try {
            //Opening the database and the Dao connections
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myEventDao = new EventDao(myConnection);

            //Find all events associated with the authtoken's username and set them to my result
            eventArray = myEventDao.findAll(theAuthToken.getUserName(), "associatedUsername");
            myResult.setEventList(eventArray);
            myResult.setSuccess(true);
            myDatabase.closeConnection(true);

        } catch (DataAccessException e) {
            myResult.setSuccess(false);
            myResult.setMessage("Error, failed to connect because" + e.toString());
            myDatabase.closeConnection(false);
        }
    }

    public void EventServiceSingular(String username, String EventID) {
        try {
            //Opening the database and the Dao connections
            myDatabase = new Database();
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myEventDao = new EventDao(myConnection);

            Event theEvent = myEventDao.find(EventID, "eventID");
            //String a1 = theEvent.getAssociatedUsername().toString().toLowerCase();
            //String userName = username.toString().toLowerCase();
            if(theEvent==null){
                throw new DataAccessException("No event with this eventID");
            }
            else if (theEvent.getAssociatedUsername().compareToIgnoreCase(username)==0) { //Matches the username and puts it in the result
                myResult.setSuccess(true);
                myResult.setSingularEvent(theEvent);
                myDatabase.closeConnection(true);
            } else {
                throw new DataAccessException("Error, event not associated with your authtoken username");
            }
        } catch (DataAccessException e) {
            myResult.setSuccess(false);
            myResult.setMessage("Error, failed to connect because" + e.toString() + ", " + e.returnMessage());
            myDatabase.closeConnection(false);
        }
    }

    //Getter Setter

    public Event[] getListOfEventsFinal() {
        return (Event[]) listOfEvents.toArray();
    }

    public EventResult getMyResult() {
        return myResult;
    }
}
