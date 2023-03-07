package Service;

import DataAccess.DataAccessException;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Request.LoadRequest;
import Result.LoadResult;
import Model.*;

/**
 * Load service
 */
public class LoadService {
    private PersonDao myPersonDao;
    private EventDao myEventDao;
    private UserDao myUserDao;
    LoadResult myResult = new LoadResult();
    public LoadResult getMyResult() {
        return myResult;
    }

    /**
     * The wonderful default constructor
     */
    public LoadService() {
    }
    /**
     * with request body, automatically loads it
     */
    public LoadService(LoadRequest request) {

        User[] users = request.getUsers();
        Person[] persons = request.getPersons();
        Event[] eventList = request.getEvents();
        try{
            for (User theUser: users) {
                myUserDao.insert(theUser);
            }
            for (Person thePerson: persons) {
                myPersonDao.insert(thePerson);
            }
            for (Event theEvent: eventList) {
                myEventDao.insert(theEvent);
            }
            myResult.success(users.length, persons.length, eventList.length);

        } catch (DataAccessException e) {
            myResult.failure(e.toString());
            //throw new RuntimeException(e);
        }


    }


}
