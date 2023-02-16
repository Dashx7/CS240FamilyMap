package Service;

import Result.RegisterResult;
import Request.RegisterRequest;
/**
 * The service to register new members
 */
public class RegisterService {
    /**
     * The wonderful default constructor
     */
    public RegisterService(){
        RegisterRequest myRequest = new RegisterRequest();
        RegisterResult myResult = new RegisterResult(myRequest);
    }

}
