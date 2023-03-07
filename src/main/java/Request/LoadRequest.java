package Request;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;

/**
 * Load request,
 */
public class LoadRequest {
    User[] users;
    Person[] persons;
    Event [] events;

    /**
     * The wonderful default constructor
     */
    public LoadRequest(){
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
