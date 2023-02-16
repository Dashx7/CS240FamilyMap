package Service;

import Request.FillRequest;
import Result.FillResults;

/**
 * Service to fill database
 */
public class FillService {
    /**
     * The wonderful default constructor
     */
    public FillService(){
        FillRequest myRequest = new FillRequest();
        FillResults myResults = new FillResults(myRequest);
    }
}
