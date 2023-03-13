package Service;

import DataAccess.*;
//import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Result.PersonResult;
//import Request.LoginRequest;
//import Request.PersonRequest;
//import Result.LoginResult;
//import Result.PersonResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * looking up a Person service
 */
public class PersonService {
    /**
     * The wonderful default constructor
     */
    Database myDatabase = new Database();
    private PersonDao myPersonDao;
    private AuthTokenDao myAuthtokenDao;
    private AuthToken myAuthToken;
    Person singularPerson;
    ArrayList<Person> listOfPeople = new ArrayList<>();

    PersonResult result;


    public PersonService(AuthToken theAuthToken) throws DataAccessException {
        try {
            result = new PersonResult();
            //Opening the database and the Dao connections
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myPersonDao = new PersonDao(myConnection);
            myAuthtokenDao = new AuthTokenDao(myConnection);

            myAuthToken = theAuthToken;
            //Create the person from finding it in the database using its name
            singularPerson = myPersonDao.find(myAuthToken.getUserName(),"");
            if (singularPerson != null) {
                generateAllRelatives(singularPerson);
                result.setSuccess(true);
                result.setData(listOfPeople);

                myDatabase.closeConnection(true);
            } else {
                if (myAuthtokenDao.find(myAuthToken.getAuthToken()) == null) {
                    throw new DataAccessException("Error: Not a valid authtoken");
                }
                else{
                    throw new DataAccessException("Error: No People are associated with this authtoken");
                }
            }
        } catch (DataAccessException e) {
            result.setMessage("Error: " + e + ", " + e.returnMessage());
            result.setSuccess(false);
            myDatabase.closeConnection(false);
        }
    }

    public PersonService(AuthToken theAuthToken, String personIDToFind) {
        try{
            result = new PersonResult();
            //Opening the database and the Dao connections
            Database myDatabase = new Database();
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myPersonDao = new PersonDao(myConnection);

            myAuthToken = theAuthToken;
            //Create the person from finding it in the database using its name
            //singularPerson = myPersonDao.find(personIDToFind);
            Person toFind = myPersonDao.find(personIDToFind); //ForID
            if(toFind!=null){
                result.setSuccess(true);
                result.setSingularPerson(toFind);
                myDatabase.closeConnection(false);
            }
            else {
                throw new DataAccessException("Error, could not find person with that ID");
            }

        } catch (DataAccessException e) {
            result.setMessage("Failed because: " + e.toString() + ", " + e.returnMessage());
            result.setSuccess(false);
            myDatabase.closeConnection(false);
        }
    }

    private void generateAllRelatives(Person person) throws DataAccessException {
        //If they have a parent add them to the list of people and then generate it for them
        Person dad = myPersonDao.find(person.getFatherID()); //Finding dad //For ID
        if (dad != null) { //If he exists, regenerate with him in the list
            listOfPeople.add(dad);
            generateAllRelatives(dad);
        }

        Person mom = myPersonDao.find(person.getMotherID()); //Finding mom //For ID
        if (mom != null) {//If she exists, regenerate with her in the list
            listOfPeople.add(mom);
            generateAllRelatives(mom);
        }
    }

    public Person getSingularPerson() {
        return singularPerson;
    }

    public ArrayList<Person> getListOfPeopleFinal() {
        return listOfPeople;
    }

    public PersonResult getResult() {
        return result;
    }

}
