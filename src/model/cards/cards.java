package model.cards;

public class cards {
    private String name;
    private double defence;
    private double attack;
    private double damage;
    private int level;
    private double upgradeCost;

    public cards(String name, double defence, double attack, double damage, int level, double upgradeCost) {
        this.name = name;
        this.defence = defence;
        this.attack = attack;
        this.damage = damage;
        this.level = level;
        this.upgradeCost = upgradeCost;
    }
}
