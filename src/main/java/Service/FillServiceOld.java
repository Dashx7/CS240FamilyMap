package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Result.FillResults;

import java.sql.Connection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Service to fill database
 */
public class FillServiceOld {
    private PersonDao myPersonDao;
    private UserDao myUserDao;
    private EventDao myEventDao;
    private FillResults myResults = new FillResults();
    private Set<Person> familyTreeLatest = new TreeSet<>();
    private Set<Person> familyTreeAll = new TreeSet<>();

    private Set<Event> events = new TreeSet<>();
    String username;
    int generations;

    /**
     * The wonderful default constructor
     */
    public FillServiceOld(String username, int generations) throws DataAccessException {
        //Opening the database and the Dao connections
        Database myDatabase = new Database();
        myDatabase.openConnection();
        Connection myConnection = myDatabase.getConnection();
        myPersonDao = new PersonDao(myConnection);
        myUserDao = new UserDao(myConnection);
        myEventDao = new EventDao(myConnection);

        this.username = username;
        this.generations = generations;
        if (myUserDao.find(username) != null) {
            familyTreeLatest.add(myPersonDao.find(username));
            generateTree();
            //Actually put them in
            for(Person thePerson: familyTreeAll){
                myPersonDao.insert(thePerson);
            }
            for(Event theEvent: events){
                myEventDao.insert(theEvent);
            }
            myResults.success();
        } else{
            myResults.fail();
        }
        myDatabase.closeConnection(false);

    }

    public FillResults getMyResults() {
        return myResults;
    }

    public void generateTree() throws DataAccessException {
        if (generations > 0) {
            generations--;
            Set<Person> familyTreeLatestTemp = familyTreeLatest;
            familyTreeLatest.clear();
            for (Person thePerson : familyTreeLatestTemp) {
                //For father
                familyTreeLatest.add(myPersonDao.find(thePerson.getFatherID(), "ID"));
                familyTreeAll.add(myPersonDao.find(thePerson.getFatherID(), "ID")); //Family tree all contains all of the family
                //For Mother
                familyTreeLatest.add(myPersonDao.find(thePerson.getMotherID(), "ID"));
                familyTreeAll.add(myPersonDao.find(thePerson.getMotherID(), "ID"));
            }
            generateTree();
        } else {
            //Finished the
            generateEvents();
        }
    }

    /**
     * for each of the people in the family tree
     * @throws DataAccessException
     */
    public void generateEvents() throws DataAccessException{
        for (Person thePerson : familyTreeAll) {
            EventService myEventService = new EventService();
            myEventService.EventServiceSingular(thePerson.getAssociatedUsername(), "");
            for(Event myEvent: myEventService.getListOfEventsFinal()){
                events.add(myEvent);
            }
        }
    }

    public Set<Person> getFamilyTreeAll() {
        return familyTreeAll;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }
}
