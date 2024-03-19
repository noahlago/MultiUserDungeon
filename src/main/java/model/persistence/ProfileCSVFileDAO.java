package model.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import model.User;

public class ProfileCSVFileDAO implements ProfileDAO{
    private HashMap<String, User> profiles;
    private String filename;

    public ProfileCSVFileDAO() throws IOException{
        this.filename = "data/profiles.csv";
        this.profiles = new HashMap<>();

        load();
    }

    private void load() throws IOException{
        File file = new File(this.filename);
        Scanner scanner = new Scanner(file);
        scanner.nextLine();
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] values = line.split(",");
            User user = new User(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]));
            profiles.put(values[0], user);
        }
        scanner.close();
    }

    @Override
    public Collection<User> getAllUsers() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }

    @Override
    public User getUser(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

    @Override
    public boolean newUser(User newUser) {
        throw new UnsupportedOperationException("Unimplemented method 'newUser'");
    }

    @Override
    public boolean deleteUser(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public User logIn(String username, String password) {
        throw new UnsupportedOperationException("Unimplemented method 'logIn'");
    }
    
}
