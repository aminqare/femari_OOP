package model.specialCards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.components.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public abstract class specialCards implements Serializable {
    private String name;
    private int level;
    private int duration;

    public String getName() {
        return name;
    }

    abstract void run();


}
