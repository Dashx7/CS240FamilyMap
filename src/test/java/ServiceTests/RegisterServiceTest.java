package ServiceTests;

import Service.ClearService;
import Service.LoginService;
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
    }




