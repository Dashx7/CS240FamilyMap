package Service;

import DataAccess.*;
//import DataAccess.EventDao;
import Model.AuthToken;
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
    private PersonDao myPersonDao;
    private AuthTokenDao myAuthtokenDao;
    private AuthToken myAuthToken;
    Person singularPerson;
    List<Person> listOfPeople = new ArrayList<>();
    Person[] listOfPeopleFinal;

    PersonResult result = new PersonResult();


    public PersonService(AuthToken theAuthToken) throws DataAccessException {
        try {
            //Opening the database and the Dao connections
            Database myDatabase = new Database();
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myPersonDao = new PersonDao(myConnection);

            myAuthToken = theAuthToken;
            //Create the person from finding it in the database using its name
            singularPerson = myPersonDao.find(myAuthToken.getUserName());
            if (singularPerson != null) {
                generateAllRelatives(singularPerson);
                listOfPeopleFinal = listOfPeople.toArray(new Person[listOfPeople.size()]);
                result.setSuccess(true);
                result.setPersonList(listOfPeopleFinal);

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
            result.setMessage("Error: " + e.toString() + ", " + e.returnMessage());
            result.setSuccess(false);
        }
    }

    public PersonService(AuthToken theAuthToken, String personIDToFind) {
        try{
            //Opening the database and the Dao connections
            Database myDatabase = new Database();
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myPersonDao = new PersonDao(myConnection);

            myAuthToken = theAuthToken;
            //Create the person from finding it in the database using its name
            singularPerson = myPersonDao.find(myAuthToken.getUserName());
            Person toFind = myPersonDao.find(personIDToFind, "ForIDs");
            if(toFind!=null){
                result.setSuccess(true);
                result.setSingularPerson(toFind);
            }
            else {
                result.setSuccess(false);
                result.setMessage("Error, could not find person with that ID");
            }


            myDatabase.closeConnection(false);
        } catch (DataAccessException e) {
            result.setMessage(e.toString());
            result.setSuccess(false);
        }
    }

    private void generateAllRelatives(Person person) throws DataAccessException {
        //If they have a parent add them to the list of people and then generate it for them
        Person dad = myPersonDao.find(person.getFatherID(), "ForIDs"); //Finding dad
        if (dad != null) { //If he exists, regenerate with him in the list
            listOfPeople.add(dad);
            generateAllRelatives(dad);
        }

        Person mom = myPersonDao.find(person.getMotherID(), "ForIDs"); //Finding mom
        if (mom != null) {//If she exists, regenerate with her in the list
            listOfPeople.add(mom);
            generateAllRelatives(mom);
        }


//        if(person.getFatherID()!=null && !person.getFatherID().contains("")){
//            Person dad = myPersonDao.find(person.getFatherID(),"ForIDs");
//            listOfPeople.add(dad);
//            generateAllRelatives(dad);
//        }
//        if(person.getMotherID()!=null && person.getMotherID()!=""){
//            Person mom = myPersonDao.find(person.getMotherID(),"ForIDs");
//            listOfPeople.add(mom);
//            generateAllRelatives(mom);
//        }
    }

    boolean stop;

    public Person getSingularPerson() {
        return singularPerson;
    }

    public Person[] getListOfPeopleFinal() {
        return listOfPeopleFinal;
    }

    private void generateAllRelativesLookingFor(Person person, Person toFind) throws DataAccessException {
        if (!stop) {
            //If they have a parent add them to the list of people and then generate it for them
            //Also looks for the person
            if (person.getFatherID() != null) {
                Person dad = myPersonDao.find(person.getFatherID(), "ForIDs");
                if (dad == toFind) {
                    singularPerson = toFind;
                    stop = true;
                }
                listOfPeople.add(dad);
                generateAllRelatives(dad);
            }
            if (person.getMotherID() != null) {
                Person mom = myPersonDao.find(person.getMotherID(), "ForIDs");
                if (mom == toFind) {
                    singularPerson = toFind;
                    stop = true;
                }
                listOfPeople.add(mom);
                generateAllRelatives(mom);
            }
        }

    }

    public PersonResult getResult() {
        return result;
    }

}
