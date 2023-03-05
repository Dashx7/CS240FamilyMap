package Service;

import DataAccess.EventDao;
import Request.EventRequest;
import Request.FillRequest;
import Result.EventResult;
import Result.FillResults;

/**
 * Event service
 */
public class EventService {
    private EventDao myDao;
    /**
     * The wonderful default constructor
     */
    public EventService() {
        EventRequest myRequest = new EventRequest();
        EventResult myResults = new EventResult(myRequest);
    }
}
