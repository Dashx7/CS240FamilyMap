package Request;

import Model.AuthToken;

import javax.security.sasl.AuthorizeCallback;

/**
 * person request
 */
public class PersonRequest {
    //AuthToken userAuthToken;
    boolean isSingular;

    public boolean isSingular() {
        return isSingular;
    }

    public void setSingular(boolean singular) {
        isSingular = singular;
    }

    /**
     * The wonderful default constructor
     */
    public PersonRequest(){
    }
}
