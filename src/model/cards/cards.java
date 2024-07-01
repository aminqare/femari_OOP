package model.cards;

public class cards {
    private String name;
    private double defence;
    private double attack;
    private int duration;
    private double damage;
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
    public cards(cards card){
        this.name = card.getName();
        this.defence = card.getDefence();
        this.attack = card.getAttack();
        this.duration = card.getDuration();
        this.damage =card.getDamage();
        this.level = card.getLevel();
        this.upgradeCost = card.getUpgradeCost();
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
        return upgradeCost;
    }

    @Override
    public String toString() {
        String output="Name: "+this.getName()+" Defence: "+String.valueOf(this.getDefence())+" Attack: "+String.valueOf(this.getAttack())+" Duration: "+String.valueOf(this.getDuration())+" Damage: "+String.valueOf(this.getDamage())+" Level: "+String.valueOf(this.getLevel())+" UpgradeCost: "+String.valueOf(this.getUpgradeCost()+"\n");
        return output;
    }
}
