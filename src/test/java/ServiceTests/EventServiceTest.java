package ServiceTests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Request.LoadRequest;
import Result.LoadResult;
import Service.ClearService;
import Service.EventService;
import Service.LoadService;
import Service.LoginService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class EventServiceTest {

    @BeforeEach
    public void setUp() {
        ClearService myClearService = new ClearService();

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void EventPositive() {
        Gson gson = new Gson();
        loadData newLoad = new loadData();
        LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);
        LoadService service = new LoadService(request);
        LoadResult result = service.getMyResult();
        assertTrue(result.isSuccess());

        EventService myEventService = new EventService();
        myEventService.EventServiceSingular("aaronstarky", "aaronBirthID");
        assertTrue(myEventService.getMyResult().isSuccess());
    }

    @Test
    public void eventNegative() {
        Gson gson = new Gson();
        loadData newLoad = new loadData();
        LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);
        LoadService service = new LoadService(request);
        LoadResult result = service.getMyResult();
        assertTrue(result.isSuccess());

        EventService myEventService = new EventService();
        myEventService.EventServiceSingular("aaronstarky", "aaronDoesACoolFlip");
        assertFalse(myEventService.getMyResult().isSuccess());
    }
}
