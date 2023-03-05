package Request;

/**
 * Request to make a new login
 */
public class LoginRequest {
    String requestBody;
    /**
     * The wonderful default constructor
     */
    public LoginRequest(){
    }
    /**
     * Login with request body
     */
    public LoginRequest(String requestBody){
        this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
