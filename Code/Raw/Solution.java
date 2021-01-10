import java.util.LinkedList;

/**
 * Speichert alle wichtigen Werte der Lösung, zur weiteren Bearbeitung und Ausgabe.
 * Alle Berechnungen werden "On-Demand" gemacht, um die Ausführungszeit des Solvers nicht zu beeinflussen.
 */
public class Solution {
    private final boolean[] truck1;   //Ladeliste des 1. LKWs
    private final boolean[] truck2;   //Ladeliste des 2. LKWs
    private final Item[] items;       //Liste der Items zur Lösung

    public Solution(boolean[] truck1, boolean[] truck2, Item[] items) {
        this.truck1 = truck1;
        this.truck2 = truck2;
        this.items = items;

    }

    public Item[] getBoxesTruck1() {
        return getItems(truck1);
    }

    public Item[] getBoxesTruck2() {
        return getItems(truck2);
    }

    public int getValueTruck1() {
        return getValue(truck1);
    }

    public int getValueTruck2() {
        return getValue(truck2);
    }

    public int getWeightTruck1() {
        return getWeight(truck1);
    }

    public int getWeightTruck2() {
        return getWeight(truck2);
    }

    private int getValue(boolean[] truck) {
        int value = 0;
        Item[] items = getItems(truck);
        for (Item item : items) {
            value += item.value;
        }
        return value;
    }

    private int getWeight(boolean[] truck) {
        int weight = 0;
        Item[] items = getItems(truck);
        for (Item item : items) {
            weight += item.weight;
        }
        return weight;
    }

    private Item[] getItems(boolean[] truck) {
        LinkedList<Item> items = new LinkedList<>();
        for (int i = 0; i < truck.length; i++) {
            if (truck[i]) {
                items.addLast(this.items[i]);
            }
        }
        return items.toArray(new Item[0]);
    }
}
