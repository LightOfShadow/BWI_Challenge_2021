public class Truck {
    private final String name;
    private final int payload;    //freie Nutzlast in Gramm (Fahrer bereits eingerechnet)

    public Truck(Driver driver, String name, int capacity) {
        this.name = name;
        this.payload = capacity - driver.getWeight();
    }

    public int getPayload() {
        return payload;
    }

    public String getName() {
        return name;
    }
}
