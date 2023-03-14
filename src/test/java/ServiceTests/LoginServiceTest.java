package ServiceTests;

import DataAccess.DataAccessException;
import Request.LoadRequest;
import Request.LoginRequest;
import Result.LoadResult;
import Service.ClearService;
import Service.FillService;
import Service.LoadService;
import Service.LoginService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


    public class LoginServiceTest {

        @BeforeEach
        public void setUp(){
            ClearService myClearService = new ClearService();
        }

        @AfterEach
        public void tearDown() {
        }

        @Test
        public void LoginPositive() {
            Gson gson = new Gson();
            loadData newLoad = new loadData();
            LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);
            LoadService service = new LoadService(request);
            LoadResult result = service.getMyResult();
            assertTrue(result.isSuccess());

            LoginRequest myLoginRequest = gson.fromJson(newLoad.getLogin1(),LoginRequest.class);
            LoginService myLoginService;
            try {
                myLoginService = new LoginService(myLoginRequest);
            } catch (DataAccessException e) {
                fail();
            }
        }

        @Test
        public void loginNegative(){
            Gson gson = new Gson();
            loadData newLoad = new loadData();
            LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);
            LoadService service = new LoadService(request);
            LoadResult result = service.getMyResult();
            assertTrue(result.isSuccess());

            LoginRequest myLoginRequest = gson.fromJson(newLoad.getLogin1(),LoginRequest.class);
            LoginService myLoginService;
            try {
                myLoginService = new LoginService(myLoginRequest);
            } catch (DataAccessException e) {
                fail();
            }
        }
    }

