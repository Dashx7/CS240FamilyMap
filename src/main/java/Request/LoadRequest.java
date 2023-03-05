package Request;

import Model.AuthToken;

/**
 * Load request,
 */
public class LoadRequest {
    String requestBody;


    /**
     * The wonderful default constructor
     */
    public LoadRequest(){
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    /**
     * Load with request body
     */
    public LoadRequest(String requestBody){
        this.requestBody = requestBody;
    }
}
