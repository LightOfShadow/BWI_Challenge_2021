public class ItemNode implements Comparable<ItemNode> {
    public int level;           //Tiefe im Baum
    public int totalValue1;     //Wert aller Items bis zu dieser Note
    public float totalWeight1;  //Gewicht aller Items bis zu dieser Note
    public int totalValue2;     //Wert aller Items bis zu dieser Note
    public float totalWeight2;  //Gewicht aller Items bis zu dieser Note
    public float upperBound;    //Bestmöglicher Wert, ab diesem Status
    public float lowerBound;    //Bestmöglicher Wert, ab diesem Status, ohne Auffüllen der Kapazität
    public boolean isChoosen1;  //letztes Item in LKW1 geladen
    public boolean isChoosen2;  //letztes Item in LKW2 geladen

    /**
     * Paramter Constructor
     */
    public ItemNode(int level, int totalValue1, float totalWeight1, int totalValue2, float totalWeight2,
                    float upperBound, float lowerBound, boolean isChoosen1, boolean isChoosen2) {
        this.level = level;
        this.totalValue1 = totalValue1;
        this.totalWeight1 = totalWeight1;
        this.totalValue2 = totalValue2;
        this.totalWeight2 = totalWeight2;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.isChoosen1 = isChoosen1;
        this.isChoosen2 = isChoosen2;
    }

    /**
     * Copy Constructor
     */
    public ItemNode(ItemNode parentNode) {
        level = parentNode.level;
        upperBound = parentNode.upperBound;
        lowerBound = parentNode.lowerBound;
        totalValue1 = parentNode.totalValue1;
        totalWeight1 = parentNode.totalWeight1;
        totalValue2 = parentNode.totalValue2;
        totalWeight2 = parentNode.totalWeight2;
        isChoosen1 = parentNode.isChoosen1;
        isChoosen2 = parentNode.isChoosen2;
    }

    /**
     * Implementiert Vergleichslogik für PriorityQueue.
     * @param o andere ItemNode
     * @return 1 wenn lowerBound gößer, oder lowerBound gleich groß und upperBound größer, -1 sonst
     */
    @Override
    public int compareTo(ItemNode o) {
        //für PriorityQueue
        if (this.lowerBound == o.lowerBound) {
            return this.upperBound > o.upperBound ? 1 : -1;
        }
        return this.lowerBound > o.lowerBound ? 1 : -1;
    }

    /**
     * State ist gleich.
     * @param obj anderer State
     * @return true, wenn dieser state dem anderen State entspricht
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemNode) {
            ItemNode iNode = (ItemNode) obj;
            return level == iNode.level &&
                    totalValue1 == iNode.totalValue1 &&
                    totalValue2 == iNode.totalValue2 &&
                    totalWeight1 == iNode.totalWeight1 &&
                    totalWeight2 == iNode.totalWeight2 &&
                    isChoosen1 == iNode.isChoosen1 &&
                    isChoosen2 == iNode.isChoosen2;
        }
        return false;
    }

    /**
     * Berechnet und setzt upperBound und lowerBound der ItemNode
     * @param items Liste aller items
     * @param capacity1 Kapazität LKW 1
     * @param capacity2 Kapazität LKW 2
     */
    public void setBound(Item[] items, float capacity1, float capacity2) {
        float w1 = totalWeight1;
        float w2 = totalWeight2;
        int v1 = totalValue1;
        int v2 = totalValue2;
        float ub_1 = 0;
        float ub_2 = 0;
        boolean finished_1 = false;
        boolean finished_2 = false;

        for (int i = level; i < items.length; i++) {
            if (w1 + items[i].weight < capacity1) {//lade LKW1
                v1 -= items[i].value;
                w1 += items[i].weight;
                continue;
            } else if (!finished_1) {
                ub_1 = v1 - (capacity1 - w1) / items[i].weight * items[i].value;
                finished_1 = true;
            }
            if (w2 + items[i].weight < capacity2) {//lade LKW2
                v2 -= items[i].value;
                w2 += items[i].weight;
            } else if(!finished_2){
                ub_2 = v2 - (capacity2 - w2) / items[i].weight * items[i].value;
                finished_2 = true;
            }
        }
        lowerBound = v1 + v2;
        upperBound = ub_1 + ub_2;
        if (upperBound > lowerBound){
            upperBound = lowerBound;
        }
    }
}
