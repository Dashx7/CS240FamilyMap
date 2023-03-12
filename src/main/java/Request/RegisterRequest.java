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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
