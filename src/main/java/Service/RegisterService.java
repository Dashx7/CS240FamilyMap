package Service;

import DataAccess.*;
import Model.AuthToken;
//import Model.Person;
import Model.User;
import Result.RegisterResult;
import Request.RegisterRequest;

import java.sql.Connection;
//import java.util.Set;
import java.util.UUID;

/**
 * The service to register new members
 */
public class RegisterService {
    PersonDao myPersonDao;
    UserDao myUserDao;
    AuthTokenDao myAuthTokenDao;
    RegisterResult myResult = new RegisterResult();

    /**
     * The wonderful default constructor
     */

    public RegisterService(RegisterRequest theRequest) throws DataAccessException {
        //Opening the database and the Dao connections
        Database myDatabase = new Database();
        myDatabase.openConnection();
        Connection myConnection = myDatabase.getConnection();
        myPersonDao = new PersonDao(myConnection);
        myUserDao = new UserDao(myConnection);
        myAuthTokenDao = new AuthTokenDao(myConnection);

        String personID = UUID.randomUUID().toString().substring(0, 8); //Makes a unique ID
        String authToken = UUID.randomUUID().toString().substring(0, 8); //Makes a unique authToken
        User newUser = new User(theRequest, personID);
        try {
            //Put the new user in the database
            myUserDao.insert(newUser);
            //Set the results
            myResult.setUsername(theRequest.getUsername());
            myResult.setPersonID(personID);
            myResult.setAuthToken(authToken);
            myResult.setSuccess(true);


            //Then Putting that Authtoken in
            AuthToken toInsert = new AuthToken();
            toInsert.setAuthToken(authToken);
            toInsert.setUserName(newUser.getUsername());
            myAuthTokenDao.insert(toInsert);
            myDatabase.closeConnection(true);

            //myUserDao.ins
            FillService myFillService = new FillService(newUser.getUsername(), 4);


        } catch (DataAccessException e) {
            myResult.setSuccess(false);
            myResult.setMessage("Register Failed because" + e + ", " + e.returnMessage());
            myDatabase.closeConnection(false);
            //throw new RuntimeException(e);
        }

    }

    public RegisterResult getMyResult() {
        return myResult;
    }
}