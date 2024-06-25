package model;

import java.io.Serializable;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.components.User;
import model.utils.Encryption;
//import model.utils.Encryption;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UsersDB implements Serializable {
    private List<User> users;

    private final String pathToUsersDBJsonFile = "src/database/users.json";

    public static UsersDB usersDB = new UsersDB();

    public UsersDB(){
        this.users = new ArrayList<>();
        try {
            this.fromJSON();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fromJSON() throws IOException {
        JsonObject usersDBJson;
        Gson gson = new Gson();
        FileReader usersJSON = new FileReader(pathToUsersDBJsonFile);
        this.users = gson.fromJson(usersJSON, new TypeToken<List<User>>(){}.getType());
        usersJSON.close();
    }

    public void toJSON() throws IOException {
        Gson gson = new Gson();
        FileWriter usersJSON = new FileWriter(pathToUsersDBJsonFile);
        String jsonData = gson.toJson(this.users, new TypeToken<List<User>>(){}.getType());
        BufferedWriter writer = new BufferedWriter(usersJSON);
        writer.write(jsonData);
        writer.close();
    }


    public User getAtIndex(int index){
        return this.users.get(index);
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public User getUserByUsername(String username){
        for(User user: this.users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByEmail(String email){
        for(User user: this.users){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }

    public void removeUserByUsername(String username){
        this.users.remove(this.getUserByUsername(username));
    }

    public void update(User user){
        for(User iter: this.users){
            if(user.equals(user)){
                iter = user;
                return;
            }
        }
    }

    public List<User> sortByScore() {

        List<User> users = this.users;
        Collections.sort(users, new Comparator<User>() {
            public int compare(User u1, User u2) {
                return u2.getScore() - u1.getScore();
            }
        });
        return users;
    }

    public boolean tokenBasedAuth(String token){
        for(User user: this.users){
            String currentToken = Encryption.toSHA256(user.getUsername() + user.getPassword());
            if(currentToken.equals(token))
                return true;
        }
        return false;
    }

    public void updateByOldUser(User oldUser, User newUser){
        for(User iter: users){
            if(iter.getUsername().equals(oldUser.getUsername())){
                iter.set(newUser);
                return;
            }
        }
    }


}
