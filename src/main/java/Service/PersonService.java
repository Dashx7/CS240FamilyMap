package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
//import DataAccess.EventDao;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
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
    private AuthToken myAuthToken;
    Person singularPerson;
    List<Person> listOfPeople = new ArrayList<>();
    Person[] listOfPeopleFinal;


    public PersonService(AuthToken theAuthToken) throws DataAccessException {
        //Opening the database and the Dao connections
        Database myDatabase = new Database();
        myDatabase.openConnection();
        Connection myConnection = myDatabase.getConnection();
        myPersonDao = new PersonDao(myConnection);

        myAuthToken = theAuthToken;
        //Create the person from finding it in the database using its name
        singularPerson = myPersonDao.find(myAuthToken.getUserName());
        generateAllRelatives(singularPerson);
        listOfPeopleFinal = (Person[]) listOfPeople.toArray();

        myDatabase.closeConnection(false);
    }
    public PersonService(AuthToken theAuthToken, String personIDToFind) throws DataAccessException {
        //Opening the database and the Dao connections
        Database myDatabase = new Database();
        myDatabase.openConnection();
        Connection myConnection = myDatabase.getConnection();
        myPersonDao = new PersonDao(myConnection);

        myAuthToken = theAuthToken;
        //Create the person from finding it in the database using its name
        singularPerson = myPersonDao.find(myAuthToken.getUserName());
        Person toFind = myPersonDao.find(personIDToFind, "ForIDs");
        generateAllRelatives(singularPerson);
        listOfPeopleFinal = (Person[]) listOfPeople.toArray();

        myDatabase.closeConnection(false);
    }

    private void generateAllRelatives(Person person) throws DataAccessException {
        //If they have a parent add them to the list of people and then generate it for them
        if(person.getFatherID()!=null){
            Person dad = myPersonDao.find(person.getFatherID(),"ForIDs");
            listOfPeople.add(dad);
            generateAllRelatives(dad);
        }
        if(person.getMotherID()!=null){
            Person mom = myPersonDao.find(person.getMotherID(),"ForIDs");
            listOfPeople.add(mom);
            generateAllRelatives(mom);
        }
    }
    boolean stop;

    public Person getSingularPerson() {
        return singularPerson;
    }

    public Person[] getListOfPeopleFinal() {
        return listOfPeopleFinal;
    }

    private void generateAllRelativesLookingFor(Person person, Person toFind) throws DataAccessException{
        if(!stop) {
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
}
