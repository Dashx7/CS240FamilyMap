package ServiceTests;

        import Request.LoadRequest;
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


    public class FillServiceTest {

        @BeforeEach
        public void setUp(){
            ClearService myClearService = new ClearService();
        }

        @AfterEach
        public void tearDown() {
        }

        @Test
        public void fill() {
            LoginService newLogin = new LoginService();
            Gson gson = new Gson();
            LoadRequest loadRequest = new LoadRequest();

            LoadService myLoadService = new LoadService();
            FillService myFillService = new FillService("username",4);
        }

        @Test
        public void clearPositive2() {
            Gson gson = new Gson();
            loadData newLoad = new loadData();
            LoadRequest request = (LoadRequest) gson.fromJson(newLoad.getLoad(), LoadRequest.class);

            FillService myFillService = new FillService("username",4);
            //assertTrue();
        }

        @Test
        public void clearNegative(){
            ClearService myClearService = new ClearService();
            assertNotNull(myClearService.getMyResults());

        }
    }


