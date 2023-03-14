package ServiceTests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;
import Service.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class PersonServiceTest {

    @BeforeEach
    public void setUp() {
        ClearService myClearService = new ClearService();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void PersonPositive() {
        Gson gson = new Gson();
        loadData newLoad = new loadData();
        LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);
        LoadService service = new LoadService(request);
        LoadResult result = service.getMyResult();
        assertTrue(result.isSuccess());

        loadData newData = new loadData();
        RegisterRequest myRegisterRequest = gson.fromJson(newData.getRegister(),RegisterRequest.class);
        RegisterService newRegisterService = new RegisterService(myRegisterRequest);
        AuthToken token = new AuthToken();
        token.setAuthToken(newRegisterService.getMyResult().getAuthtoken());
        token.setUserName(myRegisterRequest.getUsername());
        try {
            PersonService myPersonService = new PersonService(token);
            assertTrue(myPersonService.getResult().isSuccess());
        } catch (DataAccessException e) {
            fail();
        }


    }

    @Test
    public void personNegative() {
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
