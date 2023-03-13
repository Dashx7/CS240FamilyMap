package Result;

import Model.Person;

import java.util.ArrayList;


/**
 * looking up a Person result
 */
public class PersonResult {

    String message; //Message only when fail
    boolean success;
    ArrayList<Person> data;

    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this. data = new ArrayList<>();
        this.data = data;
    }

//    public Person getSingularPerson() {
//        return data.get(0);
//    }

//    public void setSingularPerson(Person singularPerson) {
//        this.data = new ArrayList<>();
//        this.data.add(singularPerson);
//    }
    public void setSingularPerson(Person myPerson) {
        this.personID = myPerson.getPersonID();
        this.associatedUsername = myPerson.getAssociatedUsername();
        this.firstName = myPerson.getFirsName();
        this.lastName = myPerson.getLastName();
        this.gender = myPerson.getGender();
        this.fatherID = myPerson.getFatherID();
        this.motherID = myPerson.getMotherID();
        this.spouseID = myPerson.getSpouseID();
    }

    /**
     * The wonderful default constructor
     */
    public PersonResult(){
    }

}
