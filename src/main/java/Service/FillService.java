package Service;

import DataAccess.PersonDao;
import Request.FillRequest;
import Result.FillResults;

/**
 * Service to fill database
 */
public class FillService {
    private PersonDao myPersonDao;
    /**
     * The wonderful default constructor
     */
    public FillService(){

        FillRequest myRequest = new FillRequest();
        FillResults myResults = new FillResults(myRequest);
    }
}
