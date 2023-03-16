package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Result.FillResults;
import com.google.gson.Gson;
import json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

/**
 * Service to fill database
 */
public class FillService {
    //The list of names
    Gson gson = new Gson();
    Fnames myFnames;
    Mnames myMnames;
    Snames mySnames;
    LocationData myLocationData;


    //List of DAOs used
    Database myDatabase = new Database();
    private PersonDao myPersonDao;
    private UserDao myUserDao;
    private EventDao myEventDao;


    private FillResults myResults = new FillResults();

    //private Set<Person> familyList = new TreeSet<>();
    //private Set<Event> eventList = new TreeSet<>();
    //private Set<Event> events = new TreeSet<>();

    String username;
    int generations;

    User theUser;

    //Little rough in my generation I know
    int MINMARRIAGEAGE = 14, MINPARENTAGE = 14;
    int MAXMARRIAGEAGE = 49, MAXPARENTAGE = 49, MINDEATHAGE = 49;
    int MAXMARRIAGETODEATH = 69;
    int CURRENTYEAR = 2023;
    int totalEvents = 0, totalPeople = 0;
    /**
     * The wonderful default constructor
     */
    public FillService(String username, int generations){
        intilizeJson();
        try {
            this.username = username;
            this.generations = generations;

            //Opening the database and the Dao connections
            myDatabase.openConnection();
            Connection myConnection = myDatabase.getConnection();
            myPersonDao = new PersonDao(myConnection);
            myUserDao = new UserDao(myConnection);
            myEventDao = new EventDao(myConnection);

            //Finding the user
            theUser = myUserDao.find(username);
            if (theUser != null) {

                //Sets up person
                Person thePerson = generatePerson(theUser.getGender());
                thePerson.setPersonID(theUser.getPersonID());
                thePerson.setAssociatedUsername(theUser.getUsername());

                //Clear out anything that was already there
                myEventDao.clearAll(theUser.getUsername());
                myPersonDao.clearAll(theUser.getUsername());

                String userBirthID = CreateEvent("birth", theUser.getPersonID(), CURRENTYEAR);

                //Start generation
                generateTree(generations, thePerson, userBirthID, theUser);


                myResults.success();
                totalEvents+=1;
                totalPeople+=1;
                myResults.setMessage("Successfully added "+ totalPeople +" persons and " + totalEvents + " events to the database.");

                myDatabase.closeConnection(true);
            }
            else {
                myResults.fail(); //User not found
                myDatabase.closeConnection(false);
            }
        } catch (DataAccessException e) {
            myResults.fail(e); //User not found
            myDatabase.closeConnection(false);
        }
    }

    public void generateTree(int generations, Person child, String childBirthID, User theUser) throws DataAccessException {
        Random myRand = new Random();
        int alterAmount;
        int childBirth = myEventDao.find(childBirthID).getYear();
        if (generations > 0) {
            totalEvents +=6;
            totalPeople+=2;

            //Generate mother
            Person mother = generatePerson("f");
            mother.setAssociatedUsername(child.getAssociatedUsername());
            child.setMotherID(mother.getPersonID());
            alterAmount = (Math.abs(myRand.nextInt())%(MAXPARENTAGE-MINPARENTAGE))+MINPARENTAGE; //13-50 alter
            int motherBornYear = childBirth - alterAmount;
            String motherBornEventID = CreateEvent("birth", mother.getPersonID(),motherBornYear);// Birth mother

            //Generate father
            Person father = generatePerson("m");
            father.setAssociatedUsername(child.getAssociatedUsername());
            child.setFatherID(father.getPersonID());
            alterAmount = (Math.abs(myRand.nextInt())%(MAXPARENTAGE-MINPARENTAGE))+MINPARENTAGE; //13-50 alter
            int fatherBornYear = childBirth - alterAmount;
            String fatherBornEventID = CreateEvent("birth", father.getPersonID(),fatherBornYear); //Birth father

            if(theUser!=null){ //Base case
                child.setPersonID(theUser.getPersonID());
                child.setFirsName(theUser.getFirstName());
                child.setLastName(theUser.getLastName());
            }
            myPersonDao.insert(child); // Inserting child before new generation and after mother/father ID is set

            //They married now
            father.setSpouseID(mother.getPersonID()); //Set spouse IDs to each other
            mother.setSpouseID(father.getPersonID());
            //Pick their wedding location and year
            Random myRandom = new Random();
            int randIndex = Math.abs(myRandom.nextInt())% myLocationData.getData().length;
            Location myLocation = myLocationData.getData()[randIndex]; //rand location

            int positive = Math.abs(myRand.nextInt());
            alterAmount = (positive%(MAXMARRIAGEAGE-MINMARRIAGEAGE))+MINMARRIAGEAGE; //13-50 alter
            int marriageYear = childBirth+alterAmount;

            CreateEvent("marriage", father.getPersonID(),marriageYear ,myLocation);
            CreateEvent("marriage", mother.getPersonID(),marriageYear ,myLocation);

            //Kill them
            alterAmount = (Math.abs(myRand.nextInt())%(MAXMARRIAGETODEATH))+MINDEATHAGE; //50-120 alter
            int fatherDeathYear = fatherBornYear + alterAmount;
            alterAmount = (Math.abs(myRand.nextInt())%(MAXMARRIAGETODEATH))+MINDEATHAGE; //50-120 alter
            int motherDeathYear = motherBornYear + alterAmount;
            CreateEvent("death",father.getPersonID(),fatherDeathYear);
            CreateEvent("death",mother.getPersonID(),motherDeathYear);


            //Generate next generation
            generateTree(generations-1,mother,motherBornEventID, null);
            generateTree(generations-1,father,fatherBornEventID, null);
        }
        else{
            myPersonDao.insert(child); // Inserting the last gen
        }
    }

    public Person generatePerson(String gender) throws DataAccessException {
        Person myPerson = new Person();

        //Generate personId and lastName
        String personID = UUID.randomUUID().toString().substring(0,8);
        Random myRandom = new Random();
        int postive = Math.abs(myRandom.nextInt());
        int randIndex = postive%mySnames.getData().length;
        String lastname = mySnames.getData()[randIndex];

        //Generate myPerson based on gender
        if (gender.compareToIgnoreCase("f")==0){
            randIndex = Math.abs(myRandom.nextInt())% myFnames.getData().length;
            String firstname = myFnames.getData()[randIndex];
            myPerson = new Person(personID,"",firstname,lastname,"f","","","");
        }
        else if(gender.compareToIgnoreCase("m")==0){
            randIndex = Math.abs(myRandom.nextInt())% myMnames.getData().length;
            String firstname = myMnames.getData()[randIndex];
            myPerson = new Person(personID,"",firstname,lastname,"m","","","");
        }
        return myPerson;
    }

    //Create event for randomized locations
    private String CreateEvent(String type, String ID, int date) throws DataAccessException {
        Random myRandom = new Random();
        int positive = Math.abs(myRandom.nextInt());
        int randIndex = positive% myLocationData.getData().length;
        Location myLocation = myLocationData.getData()[randIndex];

        String eventID = UUID.randomUUID().toString().substring(0,8);

        Event event = new Event(eventID,theUser.getUsername(), ID,
                myLocation.getLatitude(),myLocation.getLongitude(),
                myLocation.getCountry(),myLocation.getCity(),type, date);
        myEventDao.insert(event);
        return event.getEventID();
    }
    //Create event for fixed locations
    private void CreateEvent(String type, String ID, int date, Location myLocation) throws DataAccessException {
        String eventID = UUID.randomUUID().toString().substring(0,8);

        Event event = new Event(eventID,theUser.getUsername(), ID,
                myLocation.getLatitude(),myLocation.getLongitude(),
                myLocation.getCountry(),myLocation.getCity(),type, date);
        myEventDao.insert(event);
    }

    //Turing a filepath into a object
    private Object make(String filePath, Class theClass) throws FileNotFoundException {
        File file = new File(filePath);
        FileReader myFileReader = new FileReader(file);
        return gson.fromJson(myFileReader,theClass);
    }
    //Initializing all the Json file I need
    private void intilizeJson() {
        try {
            myFnames = (Fnames) make("src/main/java/json/fnames.json", Fnames.class);
            myMnames = (Mnames) make("src/main/java/json/mnames.json", Mnames.class);
            mySnames = (Snames) make("src/main/java/json/snames.json", Snames.class);
            myLocationData = (LocationData) make("src/main/java/json/locations.json", LocationData.class);

        } catch (FileNotFoundException e) {
            myResults.fail();
        }
    }

    //Getter Setters

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }
    public FillResults getMyResults() {
        return myResults;
    }
}
