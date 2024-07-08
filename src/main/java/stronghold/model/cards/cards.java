package stronghold.model.cards;

import stronghold.model.specialCards.specialCards;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class cards {
    private String name;
    private double defence;
    private double attack;
    private int duration;
    private double damage;
    private double price;
    private int level;
    private double upgradeCost;


    public cards(String name, double defence, double attack, int duration, double damage, int level, double upgradeCost) {
        this.name = name;
        this.defence = defence;
        this.attack = attack;
        this.duration = duration;
        this.damage = damage;
        this.level = level;
        this.upgradeCost = upgradeCost;
    }

    public double getPrice() {
        setPrice();
        return price;
    }

    public void setPrice() {
        this.price = 2* Math.max(defence,attack) - (damage - duration);
    }

    public String getName() {
        return name;
    }

    public double getDefence() {
        return defence;
    }

    public double getAttack() {
        return attack;
    }

    public int getDuration() {
        return duration;
    }

    public double getDamage() {
        return damage;
    }

    public int getLevel() {
        return level;
    }

    public double getUpgradeCost() {
        setUpgradeCost(upgradeCost);
        return upgradeCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public cards(cards card){
        this.name = card.getName();
        this.defence = card.getDefence();
        this.attack = card.getAttack();
        this.duration = card.getDuration();
        this.damage =card.getDamage();
        this.level = card.getLevel();
        this.upgradeCost = card.getUpgradeCost();
    }

    @Override
    public String toString() {
        String output="Name: "+this.getName()+" Defence: "+String.valueOf(this.getDefence())+" Attack: "+String.valueOf(this.getAttack())+" Duration: "+String.valueOf(this.getDuration())+" Damage: "+String.valueOf(this.getDamage())+" Level: "+String.valueOf(this.getLevel())+" UpgradeCost: "+String.valueOf(this.getUpgradeCost()+ " Price: " + String.valueOf(this.getPrice()) +"\n");
        return output;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setUpgradeCost(double upgradeCost) {
        this.upgradeCost = (defence + attack + 10*damage)/duration;
    }
    public void DeCreaseDamage(){
        int newDamage=(int)Math.ceil(this.getDamage()*0.7);
        this.setDamage(newDamage);
    }
    public void DeCreasePower(){
        int newAttack=(int)Math.ceil(this.getAttack()*0.7);
        this.setAttack(newAttack);
        int newDefence=(int)Math.ceil(this.getDefence()*0.7);
        this.setDefence(newDefence);
    }
    public void powerUp(){
        int newAttack=(int)Math.ceil(this.getAttack()*1.5);
        this.setAttack(newAttack);
        int newDefence=(int)Math.ceil(this.getDefence()*1.5);
        this.setDefence(newDefence);
    }
    public static cards GetCardByName(List<cards> cards, String name){
        for (cards card : cards) {
            if(card.getName().equals(name)){
                return card;
            }
        }
        return null;

    }
    public static void ShowCards(ArrayList<cards> cards, ArrayList<specialCards> specialCards){
        StringBuilder output=new StringBuilder();
        int Index=0;
        for (cards playerCard : cards) {
            Index++;
            output.append(Index+"."+playerCard.toString()+"\n");
        }
        for (specialCards playerSpecialCard : specialCards) {
            Index++;
            output.append(Index+"."+playerSpecialCard.toString()+"\n");
        }
        System.out.print(output);
    }
    public static void ShuffleCards(ArrayList<cards> cards,ArrayList<specialCards> specialCards){
        Collections.shuffle(cards);
        Collections.shuffle(specialCards);
    }
}
