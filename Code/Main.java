import java.util.Arrays;

public class Main {

    /* Standardwerte */
    private final static float DEFAULT_WEIGHT_D1 = 72.4f;
    private final static float DEFAULT_WEIGHT_D2 = 85.7f;
    private final static float DEFAULT_PAYLOAD = 1100f;
    private final static String DEFAULT_NAME_1 = "Konstantin";
    private final static String DEFAULT_NAME_2 = "Nicholas";

    public static void main(String[] args) {
        ItemBox[] boxes;
        Item[] items;
        Truck truck1;
        Truck truck2;
        Driver driver1;
        Driver driver2;
        double solveTime;
        Solution solution;

        /* Hole benötigte Parameter */
        try {
            System.out.println("Für die Standardwerte der Challenge bitte einfach jede Frage mit \"Enter\" bestätigen.");
            String str_temp = Util.getStringFromStdin("Wie ist der Name deines besten Freundes (1. Fahrer)?");
            int mass_temp = Util.getMassFromStdin("Und wieviel Kilo wiegt er/sie (mit \".\" statt Komma)?");
            str_temp = str_temp.equals("") ? DEFAULT_NAME_1 : str_temp;
            mass_temp = mass_temp == -1 ? Util.KgToG(DEFAULT_WEIGHT_D1) : mass_temp;
            driver1 = new Driver(str_temp, mass_temp);

            str_temp = Util.getStringFromStdin("Wie ist der Name deines zweit-besten Freundes (2. Fahrer)?");
            mass_temp = Util.getMassFromStdin("Und wieviel Kilo wiegt er/sie (mit \".\" statt Komma)?");
            str_temp = str_temp.equals("") ? DEFAULT_NAME_2 : str_temp;
            mass_temp = mass_temp == -1 ? Util.KgToG(DEFAULT_WEIGHT_D2) : mass_temp;
            driver2 = new Driver(str_temp, mass_temp);

            mass_temp = Util.getMassFromStdin("Wieviel Kilo kann der 1. Lkw transportieren? (ohne Fahrer)");
            mass_temp = mass_temp == -1 ? Util.KgToG(DEFAULT_PAYLOAD) : mass_temp;
            truck1 = new Truck(driver1, "Lkw 1", mass_temp);

            mass_temp = Util.getMassFromStdin("Wieviel Kilo kann der 2. Lkw transportieren? (ohne Fahrer)");
            mass_temp = mass_temp == -1 ? Util.KgToG(DEFAULT_PAYLOAD) : mass_temp;
            truck2 = new Truck(driver2, "Lkw 2", mass_temp);


            /* Lese Datei mit Items */
            String filename = Util.getStringFromStdin(
                    "Wie lautet der Name der Item-CSV-Datei (inkl. Endung, im gleichen Ordner)?\n" +
                            "[Enter für Werte aus der Challenge.]");
            if (filename.equals("")) {
                boxes = Util.readCsv("Items.csv");
            } else {
                boxes = Util.readCsv(filename);
            }

            /* Erstelle Liste aller Items */
            Arrays.sort(boxes);
            items = ItemBox.openBoxes(boxes);

            /* Starte Berechnung */
            long startTime = System.nanoTime();
            solution = Solver.solve(items, truck1, truck2);
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            solveTime = (double) totalTime / 1_000_000_000;

            /* Ausgabe der Werte */
            ItemBox[] payloadTruck1 = ItemBox.packItems(solution.getBoxesTruck1());
            ItemBox[] payloadTruck2 = ItemBox.packItems(solution.getBoxesTruck2());
            printHello(driver1, driver2);
            printLoadingInstructions(payloadTruck1, payloadTruck2, truck1, truck2);
            printInfosForAwesome_no_you_are_breathtaking_People(solution.getValueTruck1(), solution.getValueTruck2(),
                    solution.getWeightTruck1(), solution.getWeightTruck2(), solveTime, items.length, boxes.length);

        } catch (Exception e) {
            System.out.println("[ERROR] Es gab einen Fehler in der Ausführung:" + e.getMessage());
            System.exit(-1);
        }
    }

    private static void printHello(Driver driver1, Driver driver2){
        String name = driver1.getWeight() >= driver2.getWeight() ? driver1.getName() : driver2.getName();
        System.out.println("\n\n******************************************************************************************");
        System.out.println("Schönen guten Tag " + driver1.getName() + " und " + driver2.getName() + "!");
        System.out.println("Weiter unten finden Sie Ihre Ladeanweisungen für die heutige Fahrt nach Bonn.");
        System.out.println("Christian wünscht Ihnen eine angenehme Fahrt!\n");
        System.out.println("Wichtige Informationen:\n" + name +
                "! Für Sie befindet sich noch eine wichitige Nachricht im Postfach.\n" +
                "Betreff: Betriebliche Fitnessangebote für Jederman! Ja, auch für Sie " + name + "!\n");
        System.out.println("Bitten antworten Sie schnellstmöglich auf etwaige Anfragen - Management\n");
    }

    private static void printLoadingInstructions(ItemBox[] payloadTruck1, ItemBox[] payloadTruck2, Truck truck1, Truck truck2){
        System.out.println("\nBitte beladen Sie die LKWs wie folgt:");
        System.out.println("LkW \"" + truck1.getName() + "\":");
        ItemBox.printAsInstruction(payloadTruck1);
        System.out.println("LkW \"" + truck2.getName() + "\":");
        ItemBox.printAsInstruction(payloadTruck2);
    }

    private static void printInfosForAwesome_no_you_are_breathtaking_People(int v1, int v2, float w1, float w2, double time, int n_items, int n_boxes){
        System.out.println("\nInfos für Nerds:");
        System.out.printf("Wert Lkw 1: %-5d%n", v1);
        System.out.printf("Wert Lkw 2: %-5d%n", v2);
        System.out.printf("Gesamtwert: %-5d%n", v1 + v2);
        System.out.printf("Payload Lkw 1 [Kg]: %-5f%n", w2 / 1000);
        System.out.printf("Payload Lkw 2: %-5f%n", w1 / 1000);
        System.out.printf("Ausführungszeit: %-5f%n", time);
        System.out.printf("Anzahl an Items: %-5d%n", n_items);
        System.out.printf("Anzahl an Boxen: %-5d%n", n_boxes);
        System.out.println("\n");
    }


}
