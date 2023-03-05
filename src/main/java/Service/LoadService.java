package Service;

import DataAccess.PersonDao;
import Request.LoadRequest;
import Result.LoadResult;

/**
 * Load service
 */
public class LoadService {
    private PersonDao myPersonDao;
    /**
     * The wonderful default constructor
     */
    public LoadService() {
        ClearService clearIt = new ClearService(); //IDK
        LoadRequest myLoadRequest = new LoadRequest();
        LoadResult myLoadResult = new LoadResult(myLoadRequest);
    }

}
