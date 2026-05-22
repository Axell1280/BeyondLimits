package net.axell.createbeyondlimits.block.custom;

public enum CheeseType {
    BLUE("blue_cheese", 1, 1),
    NORMAL("cheese", 1, 0),
    PARMESAN("parmesan_cheese", 2, 2);

    private final String name;
    private final int saturationLevel;
    private final int resistanceLevel;

    CheeseType(String name, int saturationLevel, int resistanceLevel) {
        this.name = name;
        this.saturationLevel = saturationLevel;
        this.resistanceLevel = resistanceLevel;
    }

    public String getName() {
        return name;
    }

    public int getSaturationLevel() {
        return saturationLevel;
    }

    public int getResistanceLevel() {
        return resistanceLevel;
    }
}
