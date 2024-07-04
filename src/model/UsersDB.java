package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.components.User;
import model.specialCards.SpecialCardsAdapter;
import model.specialCards.specialCards;
import model.utils.Encryption;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UsersDB implements Serializable {
    private List<User> users;
    private final String pathToUsersDBJsonFile = "src/database/users.json";

    public static UsersDB usersDB = new UsersDB();

    public UsersDB() {
        this.users = new ArrayList<>();
        try {
            this.fromJSON();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fromJSON() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(specialCards.class, new SpecialCardsAdapter())
                .create();
        FileReader usersJSON = new FileReader(pathToUsersDBJsonFile);
        this.users = gson.fromJson(usersJSON, new TypeToken<List<User>>(){}.getType());
        usersJSON.close();
    }

    public void toJSON() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(specialCards.class, new SpecialCardsAdapter())
                .create();
        FileWriter usersJSON = new FileWriter(pathToUsersDBJsonFile);
        String jsonData = gson.toJson(this.users, new TypeToken<List<User>>(){}.getType());
        BufferedWriter writer = new BufferedWriter(usersJSON);
        writer.write(jsonData);
        writer.close();
    }

    public User getAtIndex(int index) {
        return this.users.get(index);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public User getUserByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByEmail(String email) {
        for (User user : this.users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public void removeUserByUsername(String username) {
        this.users.remove(this.getUserByUsername(username));
    }

    public void update(User user){
        for(int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUsername().equals(user.getUsername())){
                this.users.set(i, user);
                return;
            }
        }
    }

    public List<User> sortByScore() {
        List<User> users = new ArrayList<>(this.users);
        users.sort(Comparator.comparingInt(User::getScore).reversed());
        return users;
    }

    public boolean tokenBasedAuth(String token) {
        for (User user : this.users) {
            String currentToken = Encryption.toSHA256(user.getUsername() + user.getPassword());
            if (currentToken.equals(token))
                return true;
        }
        return false;
    }

    public void updateByOldUser(User oldUser, User newUser) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(oldUser.getUsername())) {
                this.users.set(i, newUser);
                return;
            }
        }
    }
}
