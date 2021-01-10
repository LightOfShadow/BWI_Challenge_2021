public class Item implements Comparable<Item> {
    public final String category;
    public int index;       //um das Item später wiederzufinden
    public int nextBox;     //Index von der nächsten Kategorie. Achtung, Items der letzten Kategorie zeigen auf Items.len!
    public int value;       //Wert des Items
    public int weight;      //Gewicht des Items in g

    public Item(String category, int weight, int value, int index) {
        this.category = category;
        this.index = index;
        this.value = value;
        this.weight = weight;
        this.nextBox = index + 1;
    }

    @Override
    public String toString() {
        return "(w: " + this.weight + ": v: " + this.value + ")";
    }

    /**
     * Natürliche Sortierung der Items nach Wert pro Gewicht, mit "bestem" Item zuerst.
     * @param o anderes Item
     * @return -1 wenn kleiner, sonst
     */
    @Override
    public int compareTo(Item o) {
        boolean temp = ((float)this.value / this.weight) > ((float)o.value / o.weight);
        return temp ? -1 : 1;
    }
}

