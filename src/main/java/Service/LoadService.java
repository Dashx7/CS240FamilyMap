package Service;

import DataAccess.*;
import Request.LoadRequest;
import Result.LoadResult;
import Model.*;

import java.sql.Connection;

/**
 * Load service
 */
public class LoadService {
    private PersonDao myPersonDao;
    private EventDao myEventDao;
    private UserDao myUserDao;
    private Database myDatabase;
    LoadResult myResult = new LoadResult();
    /**
     * The wonderful default constructor
     */
    public LoadService() {
    }

    /**
     * with request body, automatically loads it
     */
    public LoadService(LoadRequest request){
        try {
            //Opening the database and the Dao connections
            myDatabase = new Database();
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myEventDao = new EventDao(myConnection);
            myPersonDao = new PersonDao(myConnection);
            myUserDao = new UserDao(myConnection);

            User[] users = request.getUsers();
            Person[] persons = request.getPersons();
            Event[] eventList = request.getEvents();

            for (User theUser : users) {
                myUserDao.insert(theUser);
            }
            for (Person thePerson : persons) {
                myPersonDao.insert(thePerson);
            }
            for (Event theEvent : eventList) {
                myEventDao.insert(theEvent);
            }
            myResult.success(users.length, persons.length, eventList.length);
            myDatabase.closeConnection(true);

        } catch (DataAccessException e) {
            myResult.failure(e.toString());
            myDatabase.closeConnection(false);
        }
    }
    public LoadResult getMyResult() {
        return myResult;
    }

}
