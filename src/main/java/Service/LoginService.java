package Service;

import Request.LoginRequest;
import Result.LoginResult;

/**
 * Service to login
 */
public class LoginService {
    /**
     * The wonderful default constructor
     */
    public LoginService(){
        LoginRequest myRequest = new LoginRequest();
        LoginResult myResult = new LoginResult(myRequest);
    }
}
