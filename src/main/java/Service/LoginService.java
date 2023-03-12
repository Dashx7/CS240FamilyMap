package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.Connection;

/**
 * Service to login
 */
public class LoginService {
    private AuthToken userToken;
    private UserDao myUserDao;
    private AuthTokenDao myAuthTokenDao;
    LoginRequest myRequest;
    private LoginResult myResult = new LoginResult();
    /**
     * The wonderful default constructor, dont use
     */
    public LoginService(){}
    /**
     * Logs you in baby
     * @param myRequest
     */
    public LoginService(LoginRequest myRequest) throws DataAccessException {
        //Opening the database and the Dao connections
        Database myDatabase = new Database();
        myDatabase.openConnection();
        Connection myConnection = myDatabase.getConnection();
        myUserDao = new UserDao(myConnection);
        myAuthTokenDao = new AuthTokenDao(myConnection);

        try{
            this.myRequest = myRequest;
            User myUser = myUserDao.find(myRequest.getUsername());
            if(myUser==null){
                throw new DataAccessException("Username does not exist");
            }
            if (myUser.getPassword()== myRequest.getPassword()){
                userToken = myAuthTokenDao.find(myRequest.getUsername(),"username");
            }
            else{
                throw new DataAccessException("Username and password don't match");
            }
            myResult.success();
        } catch (DataAccessException e) {
            myResult.fail(e);
        }
        myDatabase.closeConnection(false);
    }

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
