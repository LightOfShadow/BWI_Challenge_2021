import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Util {

    private static BufferedReader reader;

    public static int getMassFromStdin(String question) throws ExecutionException{
        try  {
            System.out.println(question);
            String raw = getReaderInstance().readLine();
            if (raw.equals("")){
                return -1;
            }
            return KgToG(Float.parseFloat(raw));
        } catch (NumberFormatException e) {
            /* nochmal versuchen */
            System.out.println("Die Eingabe konnte nicht verarbeitet werden, bitte versuche es nochmal.");
            return getMassFromStdin(question);
        } catch (IOException io) {
            throw new ExecutionException(io.getCause());
        }
    }

    public static String getStringFromStdin(String question) throws ExecutionException{
        try  {
            System.out.println(question);
            return getReaderInstance().readLine();
        } catch (NumberFormatException e) {
            /* nochmal versuchen */
            System.out.println("Die Eingabe konnte nicht verarbeitet werden, bitte versuche es nochmal.");
            return getStringFromStdin(question);
        } catch (IOException io) {
            throw new ExecutionException(io.getCause());
        }
    }

    public static int KgToG(float Kg) {
        return (int) (Kg * 1000);
    }

    public static ItemBox[] readCsv(String filename) throws ExecutionException{
        List<ItemBox> allBoxes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine(); /* Kopfzeile überspringen */
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                allBoxes.add(ItemBox.stringToItemBox(values));
            }
        } catch (FileNotFoundException fnfe){ //TODO EH
            System.out.println("Datei nicht gefunden!");
            throw new ExecutionException(fnfe.getCause());
        } catch (IOException ioe){
            System.out.println("2");
        }
        return allBoxes.toArray(new ItemBox[0]);
    }


    private static BufferedReader getReaderInstance(){
        if (reader == null){
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        return reader;
    }
}
