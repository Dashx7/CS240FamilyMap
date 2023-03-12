package Result;

import Model.Person;


/**
 * looking up a Person result
 */
public class PersonResult {

    String message; //Message only when fail
    boolean success;
    Person singularPerson;
    Person [] personList;

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

    public Person[] getPersonList() {
        return personList;
    }

    public void setPersonList(Person[] personList) {
        this.personList = personList;
    }

    public Person getSingularPerson() {
        return singularPerson;
    }

    public void setSingularPerson(Person singularPerson) {
        this.singularPerson = singularPerson;
    }

    /**
     * The wonderful default constructor
     */
    public PersonResult(){
    }

}
