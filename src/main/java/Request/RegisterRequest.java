package Request;

/**
 * Request to make a new registration
 */
public class RegisterRequest {
    String requestBody;
    /**
     * The wonderful default constructor
     */
    public RegisterRequest(){
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    /**
     * request with body
     */
    public RegisterRequest(String requestBody){
        this.requestBody = requestBody;
    }
}
