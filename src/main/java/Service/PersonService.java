package Service;

import Request.LoginRequest;
import Request.PersonRequest;
import Result.LoginResult;
import Result.PersonResult;

/**
 * looking up a Person service
 */
public class PersonService {
    /**
     * The wonderful default constructor
     */
    public PersonService(){
        PersonRequest myRequest = new PersonRequest();
        PersonResult myResult = new PersonResult(myRequest);
    }

}
