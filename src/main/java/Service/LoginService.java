package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.util.UUID;

/**
 * Service to login
 */
public class LoginService {
    private AuthToken userToken;
    Database myDatabase;

    LoginRequest myRequest;
    private LoginResult myResult = new LoginResult();

    /**
     * Logs you in baby
     */
    public LoginService(LoginRequest myRequest) throws DataAccessException {
        try {
            this.myRequest = myRequest;

            //Opening the database and the Dao connections
            myDatabase = new Database();
            myDatabase.openConnection();
            UserDao myUserDao = new UserDao(myDatabase.getConnection());
            AuthTokenDao myAuthTokenDao = new AuthTokenDao(myDatabase.getConnection());
            User myUser = myUserDao.find(myRequest.getUsername());

            if (myUser == null) {
                throw new DataAccessException("Username does not exist");
            }
            else if (myUser.getPassword().compareTo(myRequest.getPassword()) == 0) {
                if(myUser.getUsername().compareTo(myRequest.getUsername())==0){
                    userToken = myAuthTokenDao.find(myRequest.getUsername(), "username");

                    if (userToken == null) {
                        //If the user exists but not the authtoken we make the authtoken... probably
                        userToken = new AuthToken(UUID.randomUUID().toString().substring(0, 8), myRequest.getUsername());
                        myAuthTokenDao.insert(userToken);
                    }
                    //Setting the results when things worked
                    myResult.setSuccess(true);
                    myResult.setUsername(myRequest.getUsername());
                    myResult.setPersonID(myUser.getPersonID());
                    myResult.setAuthtoken(userToken.getAuthToken());
                    myDatabase.closeConnection(true);
                }
                else{
                    throw new DataAccessException("Error: Username not associated with authtoken");
                }
            }
            else {
                throw new DataAccessException("Username and password do not match");
            }
        } catch (DataAccessException e) {
            myResult.fail(e);
            myDatabase.closeConnection(false);
        }
    }

    //Getter setters

    public AuthToken getUserToken() {
        return userToken;
    }

    public void setUserToken(AuthToken userToken) {
        this.userToken = userToken;
    }

    public LoginResult getMyResult() {
        return myResult;
    }

}
