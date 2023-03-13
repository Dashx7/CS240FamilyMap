package ServiceTests;

import Request.LoadRequest;
import Result.LoadResult;
import Service.ClearService;
import Service.LoadService;
import Service.LoginService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ClearServiceTest {

    @BeforeEach
    public void setUp(){
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void clearPositive() {
        //LoginService newLogin = new LoginService();
        ClearService myClearService = new ClearService();
        assertTrue(myClearService.getMyResults().isSuccess());
    }

    @Test
    public void clearNegative(){
        ClearService myClearService = new ClearService();
        assertNotNull(myClearService.getMyResults());

    }
    @Test
    public void clearPositive2(){
        Gson gson = new Gson();
        loadData newLoad = new loadData();
        LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);
        LoadService service = new LoadService(request);
        LoadResult result = service.getMyResult();

        ClearService myClearService = new ClearService();
        assertTrue(myClearService.getMyResults().isSuccess());
    }
}
