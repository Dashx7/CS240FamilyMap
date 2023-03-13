package ServiceTests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Request.LoadRequest;
import Service.ClearService;
import Service.EventService;
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

        EventService myEventService = new EventService();

        myEventService.EventServiceSingular("aaronstarky", "aaronBirthID");
        assertTrue(myEventService.getMyResult().isSuccess());

    }

    @Test
    public void eventNegative() {
        ClearService myClearService = new ClearService();
        assertNotNull(myClearService.getMyResults());


    }
}
