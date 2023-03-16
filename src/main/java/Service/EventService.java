package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Result.EventResult;

import java.util.ArrayList;

/**
 * looking up an Event service
 */
public class EventService {
    /**
     * The wonderful default constructor
     */
    Database myDatabase= new Database();
    ArrayList<Event> listOfEvents = new ArrayList<>();
    EventResult myResult = new EventResult();


    public void EventServiceAll(AuthToken theAuthToken) {
        try {
            //Opening the database and the Dao connections
            myDatabase.openConnection();
            EventDao myEventDao = new EventDao(myDatabase.getConnection());

            //Find all events associated with the authtoken's username and set them to my result
            listOfEvents = myEventDao.findAll(theAuthToken.getUserName(), "associatedUsername");
            myDatabase.closeConnection(true);

            myResult.setEventList(listOfEvents);
            myResult.setSuccess(true);


        } catch (DataAccessException e) {
            //e.printStackTrace();
            myResult.setSuccess(false);
            myResult.setMessage("Error, failed to connect because" + e.toString());
        }
    }

    public void EventServiceSingular(String username, String EventID) {
        try {
            //Opening the database and the Dao connections
            myDatabase.openConnection();
            EventDao myEventDao = new EventDao(myDatabase.getConnection());

            Event theEvent = myEventDao.find(EventID, "eventID");
            myDatabase.closeConnection(true);

            if(theEvent==null){
                throw new DataAccessException("No event with this eventID");
            }
            else if (theEvent.getAssociatedUsername().compareToIgnoreCase(username)==0) { //Matches the username and puts it in the result
                myResult.setSuccess(true);
                myResult.setSingularEvent(theEvent);
            } else {
                throw new DataAccessException("Error, event not associated with your authtoken username");
            }
        } catch (DataAccessException e) {
            //e.printStackTrace();
            myResult.setSuccess(false);
            myResult.setMessage("Error, failed to connect because" + e.toString() + ", " + e.returnMessage());
        }
    }

    //Getter Setters

    public EventResult getMyResult() {
        return myResult;
    }
}
