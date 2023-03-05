package Service;

import Result.ClearResult;
import DataAccess.EventDao;

/**
 * Service to clear the database
 */

//They are pretty much a wrapper class to the DAO allowing you to do requests and get results
public class ClearService {
    private ClearResult myResults;
    private EventDao myDao;
    /**
     * The wonderful default constructor
     */
    public ClearService(){
        myResults = new ClearResult();
    }

    public ClearResult getMyResults() {
        return myResults;
    }

    public void setMyResults(ClearResult myResults) {
        this.myResults = myResults;
    }
}
