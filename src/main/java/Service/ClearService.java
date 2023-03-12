package Service;

import Model.AuthToken;
import Model.Person;
import Model.User;
import Result.ClearResult;
import DataAccess.*;

import java.sql.Connection;

/**
 * Service to clear the database
 */

//They are pretty much a wrapper class to the DAO allowing you to do requests and get results
public class ClearService {
    private ClearResult myResults = new ClearResult();
    private AuthTokenDao myAuthTokenDao;
    private EventDao myEventDao;
    private PersonDao myPersonDao;
    private UserDao myUserDao;

    /**
     * The wonderful default constructor
     * All the services need a Dao to manipulate the data that they use
     */
    public ClearService(){
        Database myDatabase = new Database();;
        try{
            //Opening the database and the Dao connections
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myAuthTokenDao = new AuthTokenDao(myConnection);
            myAuthTokenDao.clear();
            myEventDao = new EventDao(myConnection);
            myEventDao.clear();
            myPersonDao = new PersonDao(myConnection);
            myPersonDao.clear();
            myUserDao = new UserDao(myConnection);
            myUserDao.clear();
            myDatabase.closeConnection(true);
            myResults.success();
        }
        catch (DataAccessException e) {
            myResults.error("It failed dataAccessException");
            myDatabase.closeConnection(false);
            throw new RuntimeException(e);
        }
    }

    public ClearResult getMyResults() {
        return myResults;
    }

    public void setMyResults(ClearResult myResults) {
        this.myResults = myResults;
    }
}
