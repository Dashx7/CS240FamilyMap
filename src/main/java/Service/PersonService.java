package Service;

import DataAccess.PersonDao;
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
    private PersonDao myPersonDao;

    public PersonService(){
        PersonRequest myRequest = new PersonRequest();
        PersonResult myResult = new PersonResult(myRequest);
    }

}
