package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Result.RegisterResult;
import Request.RegisterRequest;

import java.sql.Connection;
import java.util.Set;
import java.util.UUID;

/**
 * The service to register new members
 */
public class RegisterService {
    AuthToken userToken;
    RegisterRequest myRequest;
    PersonDao myPersonDao;
    UserDao myUserDao;
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
        String personID = UUID.randomUUID().toString().substring(0,8); //Makes a unique ID I hope
        String authToken = UUID.randomUUID().toString().substring(0,8); //Makes a unique ID I hope
        User newUser = new User(theRequest, personID);
        try{
            myUserDao.insert(newUser); //Put the new user in the database
            LoginService newLogin = new LoginService(); // FIXME
            myResult.setUsername(theRequest.getUsername());
            myResult.setPersonID(personID);
            myResult.setAuthToken(authToken);
            myResult.setSuccess(true);
            myDatabase.closeConnection(true);

            //TODO how do we generate ancestors

        } catch (DataAccessException e) {
            myResult.setSuccess(false);
            myDatabase.closeConnection(false);
            //throw new RuntimeException(e);
        }

    }

    public RegisterResult getMyResult() {
        return myResult;
    }
//    public void generateTree(int generations) throws DataAccessException {
//        if (generations > 0) {
//            generations--;
//            Set<Person> familyTreeLatestTemp = familyTreeLatest;
//            familyTreeLatest.clear();
//            for (Person thePerson : familyTreeLatestTemp) {
//                familyTreeLatest.add(myPersonDao.find(thePerson.getFatherID(), "ID"));
//                familyTreeAll.add(myPersonDao.find(thePerson.getFatherID(), "ID"));
//                familyTreeLatest.add(myPersonDao.find(thePerson.getMotherID(), "ID"));
//                familyTreeAll.add(myPersonDao.find(thePerson.getMotherID(), "ID"));
//            }
//            generateTree();
//        } else {
//            //Finished the
//            generateEvents();
//        }
//    }
}
