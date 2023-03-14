package ServiceTests;

import Model.Person;
import Request.RegisterRequest;
import Service.ClearService;
import Service.LoginService;
import Service.RegisterService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


    public class RegisterServiceTest {

        @BeforeEach
        public void setUp(){
            ClearService myClearService = new ClearService();
        }

        @AfterEach
        public void tearDown() {
        }

        @Test
        public void registerPositive() {
            loadData newData = new loadData();
            Gson gson = new Gson();
            RegisterRequest myRegisterRequest = gson.fromJson(newData.getRegister(),RegisterRequest.class);
            RegisterService newRegisterService = new RegisterService(myRegisterRequest);
            assertTrue(newRegisterService.getMyResult().isSuccess());
        }

        @Test
        public void registerNegative(){
            loadData newData = new loadData();
            Gson gson = new Gson();
            RegisterRequest myRegisterRequest = gson.fromJson(newData.getRegisterBad(),RegisterRequest.class);
            RegisterService newRegisterService = new RegisterService(myRegisterRequest);
            assertFalse(newRegisterService.getMyResult().isSuccess());
        }
    }




