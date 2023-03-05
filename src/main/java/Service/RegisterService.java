package Service;

import Model.AuthToken;
import Result.RegisterResult;
import Request.RegisterRequest;
/**
 * The service to register new members
 */
public class RegisterService {
    AuthToken userToken;
    /**
     * The wonderful default constructor
     */
    public RegisterService(){
        RegisterRequest myRequest = new RegisterRequest();
        RegisterResult myResult = new RegisterResult(myRequest);
        userToken = myResult.getUserAuthToken();
        LoginService newLogin = new LoginService(); // idk
    }

}
