package model.persistence;

import java.io.IOException;
import java.util.Collection;

import model.User;

/**
 * This class defines the behavior for a FileDAO to manage all user profiles that have been created for the MUD game. Instances of this class will allow user profiles to be 
 * accessed, created, deleted, and logged in to. 
 * @author Noah Lago ndl3389@rit.edu
 */
public interface ProfileDAO {
    public Collection<User> getAllUsers();
    public User getUser(String username) throws IOException;
    public boolean newUser(User newUser) throws IOException;
    public boolean deleteUser(String username)  throws IOException;
    public User logIn(String username, String password) throws IOException;
}
