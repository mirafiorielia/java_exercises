import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

// nome e cognome del candidato, matricola, data, numero della postazione

public class RubricaTester {
    public static void main(String[] args) {
        if (args.length != 2 || args[0].equals(args[1])) {
            System.out.println("Uso: $java RubricaTester filename1 filename2");
            System.out.println("Non usare stesso nome file in lettura/scrittura");
            System.exit(1);
        }

        String inputName = args[0];
        String outputName = args[1];

        Scanner inputFile = null;
        try {
            inputFile = new Scanner(new FileReader(inputName));
        } catch (FileNotFoundException e) {
            System.out.println("Problema in apertura File1! Termino");
            System.exit(1);
        }

        Rubrica rubrica = new Rubrica();
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            Scanner lineScanner = new Scanner(line);

            try {
                String name = lineScanner.next();
                String word;

                while (!(word = lineScanner.next()).equals(":")) {
                    name += " " + word;
                }

                long phone = Long.parseLong(lineScanner.next());
                System.out.println(name + " " + phone);

                rubrica.insert(name, phone);
            } catch (NoSuchElementException e) {
                // NoSuchElementException puo` essere lanciata
                // da next se non vengono trovati token
                System.out.println("Formato inserimento sbagliato");
            } catch (NumberFormatException e) {
                // NumberFormatException puo` essere lanciata da parseLong se la
                // stringa dopo i ":" non e` un intero
                System.out.println("Formato inserimento sbagliato");
            }
        }

        System.out.println(rubrica);
        inputFile.close();
    }
}

class Rubrica implements Map {

    private static int INIT_SIZE = 1;
    private int size;
    private Pair[] pairs;

    public Rubrica() {
        this.pairs = new Pair[INIT_SIZE];
        this.makeEmpty();
    }

    /*
     * Map
     * svuota la mappa
     */
    @Override
    public void makeEmpty() {
        this.size = 0;
    }

    /*
     * verifica se la mappa contiene almeno una coppia chiave/valore
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /*
     * Inserisce un elemento nella mappa. L'inserimento va sempre a buon fine.
     * Se la chiave non esiste la coppia key/value viene aggiunta alla mappa;
     * se la chiave esiste gia' il valore ad essa associato viene sovrascritto
     * con il nuovo valore; se key e` null viene lanciata IllegalArgumentException
     */
    @Override
    public void insert(Comparable key, Object value) {
        if (key == null || !(key instanceof String) || !(value instanceof Long))
            throw new IllegalArgumentException();

        /*
         * Provo a rimuovere la coppia
         * Se non esiste lancia un'eccezione,
         * questo significa che la coppia non era presente della rubrica
         */
        try {
            remove(key);
        } catch (MapItemNotFoundException e) {
            // System.err.println(e.getLocalizedMessage());
        }

        if (size == pairs.length)
            resize(false);

        // TODO riordinare?

        pairs[size - 1] = new Pair((String) key, (Long) value);
    }

    /*
     * Ridimensiono la lista di contatti
     * Aumento di uno se sto aggiungendo
     * Diminuisco di uno se sto rimuovendo
     */
    private void resize(boolean reduce) {
        size = reduce ? size - 1 : size + 1;
        Pair[] newPairs = new Pair[size];

        for (int index = 0; index < size; index++) {
            /*
             * Copio la vecchia lista in quella nuova
             * Se sto ingrandendo la lista,
             * l'ultimo valore mi lancerà IndexOutOfBoundsException
             */
            try {
                newPairs[index] = pairs[index];
            } catch (IndexOutOfBoundsException e) {
            }
        }

        pairs = newPairs;
    }

    /*
     * Rimuove dalla mappa l'elemento specificato dalla chiave key
     * Se la chiave non esiste viene lanciata MapItemNotFoundException
     */
    @Override
    public void remove(Comparable key) {
        int indexPair = binarySearch(0, size, key); // index da rimuovere

        /*
         * Copio gli elementi dall'index da rimuovere fino alla fine
         * in questo modo posso rimuovere l'ultimo index che sarà diventato l'emento da
         * rimuovere
         */
        for (int index = indexPair; index < size - 1; index++) {
            pairs[index] = pairs[index + 1];
        }

        resize(true); // rimuovo l'ultimo elemento
    }

    /*
     * Cerca nella mappa l'elemento specificato dalla chiave key
     * La ricerca per chiave restituisce soltanto il valore ad essa associato
     * Se la chiave non esiste viene lanciata MapItemNotFoundException
     */
    @Override
    public Object find(Comparable key) {
        return pairs[binarySearch(0, size - 1, key)].getPhone();
    }

    /*
     * Ricarca dicotomica all'interno della lista di coppie
     */
    private int binarySearch(int start, int end, Comparable key) {
        if (start > end)
            throw new MapItemNotFoundException();

        int middle = (start + end) / 2;
        Comparable middleKey = pairs[middle].getName();
        if (middleKey.compareTo(key) == 0) {
            return middle;
        }

        if (middleKey.compareTo(key) < 0) // cerco a destra dal mezzo
            return binarySearch(middle + 1, end, key);

        return binarySearch(start, middle - 1, key); // cerca a sinistra dal mezzo
    }

    public String toString() {
        String text = "";
        for (Pair pair : pairs) {
            text = text + pair + "\n";
        }

        return text;
    }

    // classe privata Pair: non modificare!!
    private class Pair {
        public Pair(String aName, long aPhone) {
            name = aName;
            phone = aPhone;
        }

        public String getName() {
            return name;
        }

        public long getPhone() {
            return phone;
        }

        /*
         * Restituisce una stringa contenente
         * - la nome, "name"
         * - un carattere di separazione ( : )
         * - il numero telefonico, "phone"
         */
        public String toString() {
            return name + " : " + phone;
        }

        // campi di esemplare
        private String name;
        private long phone;
    }
}

interface Map {
    /*
     * verifica se la mappa contiene almeno una coppia chiave/valore
     */
    boolean isEmpty();

    /*
     * Map
     * svuota la mappa
     */
    void makeEmpty();

    /*
     * Inserisce un elemento nella mappa. L'inserimento va sempre a buon fine.
     * Se la chiave non esiste la coppia key/value viene aggiunta alla mappa;
     * se la chiave esiste gia' il valore ad essa associato viene sovrascritto
     * con il nuovo valore; se key e` null viene lanciata IllegalArgumentException
     */
    void insert(Comparable key, Object value);

    /*
     * Rimuove dalla mappa l'elemento specificato dalla chiave key
     * Se la chiave non esiste viene lanciata MapItemNotFoundException
     */
    void remove(Comparable key);

    /*
     * Cerca nella mappa l'elemento specificato dalla chiave key
     * La ricerca per chiave restituisce soltanto il valore ad essa associato
     * Se la chiave non esiste viene lanciata MapItemNotFoundException
     */
    Object find(Comparable key);
}

class MapItemNotFoundException extends RuntimeException {
}
