package Request;

import com.google.gson.Gson;

/**
 * Request to make a new registration
 */
public class RegisterRequest {
    //Variables from the Json request
    //String requestBody;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender; //Will be either f or m

    /**
     * The wonderful default constructor
     */
    public RegisterRequest(){
    }
    /**
     * Constructor with request body
     */
    public RegisterRequest(String requestBody){
        Gson gson = new Gson();
        gson.fromJson(requestBody,this.getClass());
    }


}
