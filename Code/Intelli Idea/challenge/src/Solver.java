import java.util.PriorityQueue;

public class Solver {

    /**
     * startet den LC-BnB Algorithmus.
     * @param arr ein, nach Profit sortiertes, Array aller Items
     */
    public static Solution solve(Item[] arr, Truck t1, Truck t2) {
        long start = System.currentTimeMillis();
        float c1 = t1.getPayload();
        float c2 = t2.getPayload();
        ItemNode current = new ItemNode(0, 0, 0, 0, 0, 0,
                0, false, false);
        ItemNode pickItem1;
        ItemNode pickItem2;
        ItemNode skipItem;

        int itemCount = arr.length;         //Anzahl an Items
        float minLB = 0;                    //bester Wert für diese Iteration
        float finalLB = Integer.MAX_VALUE;  //Momentan bester Wert

        //PQ sortiert von kleinstem zu größtem LowerBound
        PriorityQueue<ItemNode> queue = new PriorityQueue<>();

        //start Node einfügen
        queue.add(current);

        boolean[] currPath1 = new boolean[itemCount];
        boolean[] finalPath1 = new boolean[itemCount];
        boolean[] currPath2 = new boolean[itemCount];
        boolean[] finalPath2 = new boolean[itemCount];

        while (!queue.isEmpty()) {
            current = queue.poll();
            int clevel = current.level;

            if (current.upperBound > minLB || current.upperBound >= finalLB) {
                continue;
            }

            if (clevel != 0) {
                currPath1[current.level - 1] = current.isChoosen1;
                currPath2[current.level - 1] = current.isChoosen2;
            }

            //Alle Items durch
            if (clevel == itemCount) {
                if (current.lowerBound < finalLB) {
                    //Update besten final Path für Lkw 1 und 2
                    for (int i = 0; i < itemCount; i++) {
                        finalPath1[arr[i].index] = currPath1[i];
                        finalPath2[arr[i].index] = currPath2[i];
                    }
                    finalLB = current.lowerBound;
                }
                continue;
            }

            //nimm aktuelles Item in LKW1 mit:
            if (current.totalWeight1 + arr[current.level].weight <= c1) {
                pickItem1 = new ItemNode(clevel + 1, current.totalValue1 - arr[clevel].value,
                        current.totalWeight1 + arr[clevel].weight, current.totalValue2, current.totalWeight2,
                        current.upperBound, current.lowerBound, true, false);
                pickItem1.setBound(arr, c1, c2);
            } else { // Item passt nicht
                pickItem1 = new ItemNode(0, 0, 0, 0, 0,
                        minLB + 1, 0, false, false);
            }

            //nimm aktuelles Item in LKW2 mit:
            if (current.totalWeight2 + arr[current.level].weight <= c2) {
                pickItem2 = new ItemNode(clevel + 1, current.totalValue1, current.totalWeight1,
                        current.totalValue2 - arr[clevel].value, current.totalWeight2 + arr[clevel].weight,
                        current.upperBound, current.lowerBound, false, true);
                pickItem2.setBound(arr, c1, c2);
            } else { // Item passt nicht
                pickItem2 = new ItemNode(0, 0, 0, 0, 0,
                        minLB + 1, 0, false, false);
            }


            int skipLevel = arr[clevel].nextBox; //Skip to next categorie
            //Item wird "absichtlich" nicht mitgenommen für LKW1:
            skipItem = new ItemNode(skipLevel, current.totalValue1, current.totalWeight1, current.totalValue2,
                    current.totalWeight2, current.upperBound, current.lowerBound, false, false);
            skipItem.setBound(arr, c1, c2);

            //finde die aktuell beste der Alternativen (LKW1 / LKW2 / Skip):
            float bestLB = Math.min(Math.min(pickItem1.lowerBound, pickItem2.lowerBound), skipItem.lowerBound);
            minLB = Math.min(minLB, bestLB);

            //unnötige states (ItemNodes) aussortieren:
            if (minLB >= pickItem1.upperBound && !queue.contains(pickItem1))
                queue.add(new ItemNode(pickItem1));
            if (minLB >= pickItem2.upperBound && !queue.contains(pickItem2))
                queue.add(new ItemNode(pickItem2));
            if (minLB >= skipItem.upperBound && !queue.contains(skipItem))
                queue.add(new ItemNode(skipItem));
        }

        return new Solution(finalPath1, finalPath2, arr);
    }
}

