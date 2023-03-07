package Service;

import DataAccess.UserDao;
import Model.AuthToken;
import Result.RegisterResult;
import Request.RegisterRequest;
/**
 * The service to register new members
 */
public class RegisterService {
    AuthToken userToken;
    RegisterRequest myRequest;
    /**
     * The wonderful default constructor
     */
    private UserDao myUserDao;
    public RegisterService(){
        //myUserDao.insert(myRequest);

        LoginService newLogin = new LoginService(); // idk
    }

}
