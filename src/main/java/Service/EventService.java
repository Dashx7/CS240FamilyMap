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
    //private PersonDao myPersonDao;
    private PersonService myPersonService;
    //private AuthToken myAuthToken;
    Event singularEvent;
    List<Event> listOfEvents = new ArrayList<Event>();
    Person[] listOfPeople;
    EventResult myResult = new EventResult();


    public void EventServiceAll(AuthToken theAuthToken) throws DataAccessException {
        //Opening the database and the Dao connections
        Database myDatabase = new Database();
        myDatabase.openConnection();
        Connection myConnection = myDatabase.getConnection();
        myEventDao = new EventDao(myConnection);
        myPersonService = new PersonService(theAuthToken);

        listOfPeople = myPersonService.getListOfPeopleFinal();
        for(int i = 0; i < listOfPeople.length; i++){
            EventServiceSingular(theAuthToken.getUserName(), "");
        }
        myDatabase.closeConnection(true);
    }
    public void EventServiceSingular(String username, String EventID) throws DataAccessException {
        //Opening the database and the Dao connections
        Database myDatabase = new Database();
        myDatabase.openConnection();
        Connection myConnection = myDatabase.getConnection();
        if(EventID ==""){
            for(Event myEvent: myEventDao.findAll(username, "associatedUsername")){
                listOfEvents.add(myEvent);
            }
        }
        else {
            listOfEvents.add(myEventDao.find(EventID, "eventID"));
        }
        myDatabase.closeConnection(true);

    }

    //Getter Setter
    public Event getSingularEvent() {
        return listOfEvents.get(0);
    }

    public Event[] getListOfEventsFinal() {
        return (Event[])listOfEvents.toArray();
    }

    public EventResult getMyResult() {
        return myResult;
    }
}
