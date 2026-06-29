public class Weapon {

    public enum Type {
        NORMAL,
        RADAR,
        ARTILLERY,
        AIRSTRIKE,
        EMP
    }

    private final Type type;
    private final String name;
    private final int unlockStreak;
    private final String description;

    public Weapon(Type type, String name, int unlockStreak, String description) {

        this.type = type;
        this.name = name;
        this.unlockStreak = unlockStreak;
        this.description = description;
    }
    

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getUnlockStreak() {
    return unlockStreak;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {

        return name + " - " + description;
    }

    // Factory methods

    public static Weapon normalShot() {

        return new Weapon(
                Type.NORMAL,
                "Normal Shot",
                0,
                "Attack a single square.");
    }

    public static Weapon radar() {

        return new Weapon(
                Type.RADAR,
                "Radar",
                3,
                "Reveal a 3x3 area without damaging ships.");
    }

    public static Weapon artillery() {

        return new Weapon(
                Type.ARTILLERY,
                "Artillery",
                5,
                "Attack every square in a 2x2 area.");
    }

    public static Weapon airstrike() {

        return new Weapon(
                Type.AIRSTRIKE,
                "Airstrike",
                7,
                "Attack an entire row or column.");
    }

    public static Weapon emp() {

        return new Weapon(
                Type.EMP,
                "EMP",
                10,
                "Disable the opponent's special weapons for one turn.");
    }
}