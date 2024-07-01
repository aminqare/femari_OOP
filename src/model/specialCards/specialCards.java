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
    private int level=1;
    private int duration;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    abstract void run(Object param);

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String output="Name: "+this.getName()+" Level: "+String.valueOf(this.getLevel())+" Duration: "+String.valueOf(this.getDuration())+"\n";
        return output;
    }


}
