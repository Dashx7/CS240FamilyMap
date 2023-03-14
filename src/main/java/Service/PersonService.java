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
    AuthToken myAuthToken;
    Person singularPerson;
    ArrayList<Person> listOfPeople = new ArrayList<>();

    PersonResult result;


    public PersonService(AuthToken theAuthToken) throws DataAccessException {
        try {
            myAuthToken = theAuthToken;
            result = new PersonResult();

            //Opening the database and the Dao connections
            myDatabase.openConnection();
            myPersonDao = new PersonDao(myDatabase.getConnection());
            myAuthtokenDao = new AuthTokenDao(myDatabase.getConnection());

            //Create the person from finding it in the database using its name
            singularPerson = myPersonDao.find(myAuthToken.getUserName(),"");
            myDatabase.closeConnection(true); //Close it immediately after Dao use

            if (singularPerson != null) {
                myDatabase.openConnection();
                myPersonDao = new PersonDao(myDatabase.getConnection());
                listOfPeople = myPersonDao.findAll(myAuthToken.getUserName()); //FIXME ?
                myDatabase.closeConnection(true);
//                listOfPeople.add(singularPerson); //Add in the base person
//                generateAllRelatives(singularPerson);
                result.setSuccess(true);
                result.setData(listOfPeople);

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
        }
    }

    public PersonService(AuthToken theAuthToken, String personIDToFind) {
        try{
            result = new PersonResult();
            //Opening the database and the Dao connections
            Database myDatabase = new Database();
            myDatabase.openConnection();
            myPersonDao = new PersonDao(myDatabase.getConnection());

            //myAuthToken = theAuthToken;
            Person toFind = myPersonDao.find(personIDToFind); //ForID
            myDatabase.closeConnection(true);

            if(toFind!=null){
                myDatabase.openConnection(); //Opening
                EventDao myEventDao = new EventDao(myDatabase.getConnection());
                String associatedUsername = myEventDao.find(toFind.getAssociatedUsername(), "associatedUsername").getAssociatedUsername(); //Grabbing who the event of the authtoken is realted to
                myDatabase.closeConnection(true); //Close immediately after

                if(associatedUsername.compareToIgnoreCase(theAuthToken.getUserName())==0){
                    result.setSuccess(true);
                    result.setSingularPerson(toFind);
                }
                else{
                    throw new DataAccessException("Error: Authtoken and username exist but aren't assoicated");
                }

            }
            else {
                throw new DataAccessException("Error, could not find person with that ID");
            }

        } catch (DataAccessException e) {
            result.setMessage("Failed because: " + e.toString() + ", " + e.returnMessage());
            result.setSuccess(false);
            System.out.println(result.toString());
        }
    }

//    private void generateAllRelatives(Person person) throws DataAccessException {
//        myDatabase.openConnection();
//        myPersonDao = new PersonDao(myDatabase.getConnection());
//        //If they have a parent add them to the list of people and then generate it for them
//        Person dad = myPersonDao.find(person.getFatherID()); //Finding dad //For ID
//        Person mom = myPersonDao.find(person.getMotherID()); //Finding mom //For ID
//        myDatabase.closeConnection(true); //Close it immediately afterwards
//
//        if (dad != null) { //If he exists, regenerate with him in the list
//            listOfPeople.add(dad);
//            generateAllRelatives(dad);
//        }
//        if (mom != null) {//If she exists, regenerate with her in the list
//            listOfPeople.add(mom);
//            generateAllRelatives(mom);
//        }
//    }

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
