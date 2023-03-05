package Service;

import DataAccess.UserDao;
import Model.AuthToken;
import Request.LoginRequest;
import Result.LoginResult;

/**
 * Service to login
 */
public class LoginService {
    private AuthToken userToken;
    private UserDao myUserDao;
    /**
     * The wonderful default constructor
     */
    public LoginService(){
        LoginRequest myRequest = new LoginRequest();
        LoginResult myResult = new LoginResult(myRequest);
        userToken = myResult.getUserAuthtoken();
    }

    public AuthToken getUserToken() {
        return userToken;
    }

    public void setUserToken(AuthToken userToken) {
        this.userToken = userToken;
    }

    public UserDao getMyUserDao() {
        return myUserDao;
    }

    public void setMyUserDao(UserDao myUserDao) {
        this.myUserDao = myUserDao;
    }
}
