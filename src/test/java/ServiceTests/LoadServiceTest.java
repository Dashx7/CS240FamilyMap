package ServiceTests;

        import Request.LoadRequest;
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


public class LoadServiceTest {
    Gson gson = new Gson();
    loadData newLoad = new loadData();
    LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);

    @BeforeEach
    public void setUp(){
        ClearService myClearService = new ClearService();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void loadPositive() {
        Gson gson = new Gson();
        loadData newLoad = new loadData();
        LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);
        LoadService service = new LoadService(request);
        LoadResult result = service.getMyResult();
        assertTrue(result.isSuccess());
    }

    @Test
    public void loadNegative(){
        Gson gson = new Gson();
        loadData newLoad = new loadData();
        LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);
        LoadService service = new LoadService(request);
        LoadResult result = service.getMyResult();
        assertFalse(result.getMessage().contains("Error"));
    }
}

