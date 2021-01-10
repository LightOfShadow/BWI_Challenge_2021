import java.util.LinkedList;

public class ItemBox implements Comparable<ItemBox> {

    private final String name;
    private final int weight;  //Gewicht pro Einheit in Gramm
    private final int value;     //Wert pro Einheit
    private int quantity;  //Anzahl an Items in der Box

    public ItemBox(String name, int weight, int value, int quantity) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.quantity = quantity;
    }

    /**
     * Natürliche Sortierung von größter zu kleinster Box, nach Wert pro Gewicht.
     * @param o ItemBox, gegen die verglichen wird.
     * @return -1 wenn größer, 1 sonst.
     */
    @Override
    public int compareTo(ItemBox o) {
        boolean temp = ((float) this.value / this.weight) > ((float) o.value / o.weight);
        return temp ? -1 : 1;
    }

    /**
     * Generiert eine ItemBox Instanz aus den Werten in arr.
     * @param arr Werte als String [quantity, weight, value]
     * @return fertige ItemBox Instanz
     * @throws RuntimeException wenn es ein Fehler bei der Umwandlung gab.
     */
    public static ItemBox stringToItemBox(String[] arr) throws RuntimeException {
        try{
            int quantity = Integer.parseInt(arr[1]);
            int weight = Integer.parseInt(arr[2]);
            int value = Integer.parseInt(arr[3]);
            return new ItemBox(arr[0], weight, value, quantity);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe){
            System.out.println("[ERROR] Fehler im Format der CSV-Datei!");
            throw new RuntimeException(nfe.getMessage());
        }
    }

    /**
     * Wandelt eine Liste von ItemBoxen in eine Liste von Items um.
     * @param allBoxes Liste von Boxen, aus denen Items genommern werden können
     * @return Liste aller Items, in der Reihenfolge, wie in allBoxes vorkommend
     */
    public static Item[] openBoxes(ItemBox[] allBoxes) {

        LinkedList<Item> items = new LinkedList<>();
        int[] catStarts = new int[allBoxes.length];
        int index = 0;
        int catIndex = 0;
        for (ItemBox box : allBoxes) {
            for (int j = 0; j < box.quantity; j++) {
                items.addLast(new Item(box.name, box.weight, box.value, index));
                index++;
            }
            catStarts[catIndex] = index;
            catIndex++;
        }
        Item[] items_arr = items.toArray(new Item[0]);
        int currentNextCat = 0;
        for (int i = 0; i < items_arr.length; i++) {
            if (i == catStarts[currentNextCat]) {
                currentNextCat++;
            }
            items_arr[i].nextBox = catStarts[currentNextCat];
        }
        return items_arr;
    }

    /**
     * Evaluiert, ob sich in der Liste and Boxen bereits eine Box für das Item befindet
     * und gibt den Index der Box zurück, falls vorhanden.
     * @param boxes Liste aller Boxen
     * @param item Item, zu der die Box gesucht wird
     * @return Index der passenden Box, -1 wenn nicht vorhanden
     */
    public static int hasBoxForItem(final LinkedList<ItemBox> boxes, final Item item) {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).name.equals(item.category)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Fasst alle Items in eine Liste von ItemBoxen zusammen.
     * @param items Liste aller Items
     * @return Liste mit gefüllten ItemBoxen
     */
    public static ItemBox[] packItems(Item[] items) {
        LinkedList<ItemBox> boxes = new LinkedList<>();
        int index;
        for (Item item : items) {
            index = ItemBox.hasBoxForItem(boxes, item);
            if (index == -1) {
                boxes.addLast(new ItemBox(item.category, item.weight, item.value, 0));
                index = boxes.size() - 1;
            }
            boxes.get(index).quantity++;
        }
        return boxes.toArray(new ItemBox[0]);
    }

    /**
     * Gibt Ladeanweisung zu einer Liste von ItemBoxen aus.
     * @param boxes alle Boxen, die geladen werden sollen
     */
    public static void printAsInstruction(ItemBox[] boxes){
        for (ItemBox box: boxes) {
            System.out.println("    <> \"" + box.name + "\" - " + box.quantity + " Stk.");
        }
        System.out.println();
    }
}
