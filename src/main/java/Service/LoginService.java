package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

/**
 * Service to login
 */
public class LoginService {
    private AuthToken userToken;
    private UserDao myUserDao;
    private AuthTokenDao myAuthTokenDao;
    LoginRequest myRequest;
    private LoginResult myResult;
    /**
     * The wonderful default constructor, dont use
     */
    public LoginService(){}
    /**
     * Logs you in baby
     * @param myRequest
     */
    public LoginService(LoginRequest myRequest) throws DataAccessException {
        try{
            this.myRequest = myRequest;
            User myUser = myUserDao.find(myRequest.getUsername());
            if (myUser.getPassword()== myRequest.getPassword()){
                userToken = myAuthTokenDao.find(myRequest.getUsername(),"username");
            }
            myResult.success();
        } catch (DataAccessException e) {
            myResult.fail(e);
            throw new RuntimeException(e);
        }
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
