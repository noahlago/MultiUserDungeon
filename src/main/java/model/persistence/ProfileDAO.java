package model.persistence;

import java.util.Collection;

import model.User;

public interface ProfileDAO {
    public Collection<User> getAllUsers();
    public User getUser(String username);
    public boolean newUser(User newUser);
    public boolean deleteUser(String username);
    public User logIn(String username, String password);
}
