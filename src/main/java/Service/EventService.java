package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Event;
import Model.Person;

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
    private PersonDao myPersonDao;
    private PersonService myPersonService;
    private AuthToken myAuthToken;
    Event singularEvent;
    List<Event> listOfEvents = new ArrayList<Event>();
    Person[] listOfPeople;


    public void EventServiceAll(AuthToken theAuthToken) throws DataAccessException {
        //myPersonDao.find(theAuthToken.getUserName());
        myPersonService = new PersonService(theAuthToken);
        listOfPeople = myPersonService.getListOfPeopleFinal();
        for(int i = 0; i < listOfPeople.length; i++){
            EventServiceSingular(theAuthToken, "");
        }
    }
    public void EventServiceSingular(AuthToken theAuthToken, String EventID) throws DataAccessException {
        if(EventID ==""){
            for(Event myEvent: myEventDao.findAll(theAuthToken.getUserName(), "associatedUsername")){
                listOfEvents.add(myEvent);
            }
        }
        else {
            listOfEvents.add(myEventDao.find(EventID, "eventID"));
        }
    }
    public Event getSingularEvent() {
        return listOfEvents.get(0);
    }

    public Event[] getListOfEventsFinal() {
        return (Event[])listOfEvents.toArray();
    }

}
