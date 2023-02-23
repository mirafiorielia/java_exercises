import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Scanner;

// nome e cognome del candidato, matricola, data,       numero postazione

public class StudentiTester {
    public static void main(String[] args) {
        /*
         * Studenti studenti = new Studenti();
         * studenti.insert(Long.parseLong("2008772"), (String) "Elia Mirafiori");
         * studenti.insert(Long.parseLong("3021545"), (String) "Lisa Mirafiori");
         * studenti.insert(Long.parseLong("9853321"), (String) "Pietro Mirafiori");
         * studenti.insert(Long.parseLong("9095013"), (String) "Davide Mirafiori");
         * 
         * System.out.println(studenti);
         */

        if (args.length != 2 || args[0].equals(args[1])) {
            System.exit(1);
        }

        String fileName1 = args[0];
        String fileName2 = args[1];

        Studenti iscritti = new Studenti();
        Studenti promossi = new Studenti();

        Scanner file1 = null;
        try {
            file1 = new Scanner(new FileReader(fileName1)); // apro ed analizzo il file
        } catch (FileNotFoundException e) {
            System.out.println("Problema in apertura File1! Termino");
            System.exit(1);
        }

        // fintantochè ci sono righe di testo
        while (file1.hasNextLine()) {
            String line = file1.nextLine(); // prendo la riga successiva
            Scanner lineScan = new Scanner(line); // analizzo la riga

            try {
                long matr = Long.parseLong(lineScan.next());

                if (!lineScan.next().equalsIgnoreCase(":") || !lineScan.hasNext()) {
                    lineScan.close();
                    throw new NoSuchElementException();
                }

                String username = lineScan.nextLine().trim();

                iscritti.insert(matr, username);
            } catch (NoSuchElementException e) {
                System.out.println("Formato inserimento sbagliato");
            } catch (NumberFormatException e) {
                System.out.println("Formato inserimento sbagliato");
            }
            lineScan.close();
        }
        System.out.println("Studenti iscritti");
        System.out.println(iscritti);
        file1.close();

        Scanner console = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Matr. in iscritti da spostare in promossi?");
            System.out.println("(\"Q\" per terminare)");
            String line = console.nextLine();

            if (line.equalsIgnoreCase("Q")) {
                done = true;
            } else {
                try {
                    Long matr = Long.parseLong(line);
                    promossi.insert(matr, iscritti.find(matr));
                    iscritti.remove(matr);
                } catch (DictionaryItemNotFoundException e) {
                    System.out.println("Matricola non presente");
                } catch (NumberFormatException e) {
                    System.out.println("Formato inserimento sbagliato");
                }
                System.out.println("Iscritti:\n" + iscritti); // controllo i
                System.out.println("Promossi:\n" + promossi); // contenuti
            }
        }

        PrintWriter file2 = null;
        try {
            file2 = new PrintWriter(fileName2);
        } catch (FileNotFoundException e) {
            System.out.println("Problema in apertura File2! Termino");
            System.exit(1);
        }

        file2.print(promossi);
        file2.close();
        System.out.println("Arrivederci");
        System.exit(0);
    }
}

class Studenti implements Dictionary {
    private static int INIT_SIZE = 1;
    private int size;
    private Pair[] array;

    public Studenti() {
        this.array = new Pair[INIT_SIZE];
        makeEmpty();
    }

    /*
     * verifica se il dizionario contiene almeno una coppia chiave/valore
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /*
     * svuota il dizionario
     */
    public void makeEmpty() {
        this.size = 0;
    }

    /*
     * Inserisce un elemento nel dizionario. L'inserimento va sempre a buon fine.
     * Se la chiave non esiste la coppia key/value viene aggiunta al dizionario;
     * se la chiave esiste gia', il valore ad essa associato viene sovrascritto
     * col nuovo valore; se key e` null viene lanciata IllegalArgumentException
     */
    public void insert(Comparable key, Object value) {
        if (!(key instanceof Long) || !(value instanceof String)) {
            throw new IllegalArgumentException();
        }

        try {
            remove(key);
        } catch (DictionaryItemNotFoundException e) {
        }

        if (this.size == this.array.length) {
            resize();
        }

        this.array[this.size] = new Pair((Long) key, (String) value);
        this.size++;

        sort();
    }

    /*
     * Rimuove dal dizionario l'elemento specificato dalla chiave key
     * Se la chiave non esiste viene lanciata DictionaryItemNotFoundException
     */
    public void remove(Comparable key) {
        int itemIndex = binSearch(0, this.size - 1, key);
        for (int index = itemIndex; index < this.size - 1; index++) {
            this.array[index] = this.array[index + 1];
        }
        this.size--;
    }

    private int binSearch(int start, int end, Comparable key) {
        if (start > end) {
            throw new DictionaryItemNotFoundException();
        }

        int middleIndex = (start + end) / 2;
        Comparable matr = this.array[middleIndex].getMatr();

        if (matr.compareTo(key) == 0) {
            return middleIndex;
        }

        if (matr.compareTo(key) > 0) {
            return binSearch(start, middleIndex - 1, key);
        }

        return binSearch(middleIndex + 1, end, key);

    }

    private void resize() {
        Pair[] newArray = new Pair[this.size * 2];
        for (int index = 0; index < this.size; index++) {
            newArray[index] = this.array[index];
        }
        this.array = newArray;
    }

    private void sort() {
        for (int i = 0; i < this.size; i++) {
            for (int j = i + 1; j < this.size; j++) {
                Pair tmp;

                if (array[i].getMatr() > array[j].getMatr()) {
                    tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }

    /*
     * Cerca nel dizionario l'elemento specificato dalla chiave key
     * La ricerca per chiave restituisce soltanto il valore ad essa associato
     * Se la chiave non esiste viene lanciata DictionaryItemNotFoundException
     */
    public Object find(Comparable key) {
        return this.array[binSearch(0, this.size - 1, key)].getName();
    }

    public String toString() {
        String output = "";
        for (int index = 0; index < this.size; index++) {
            output += this.array[index].toString() + "\n";
        }
        return output;
    }

    // classe privata Pair: non modificare!!
    private class Pair {
        public Pair(long matr, String name) {
            this.matr = matr;
            this.name = name;
        }

        public long getMatr() {
            return matr;
        }

        public String getName() {
            return name;
        }

        /*
         * Restituisce una stringa contenente
         * - il numero di matricola, (numero long contenuto in "matr")
         * - un carattere di separazione ( : )
         * - il nome (stringa di una o piu` parole contenuta in "name")
         */
        public String toString() {
            return matr + " : " + name;
        }

        // campi di esemplare
        private long matr;
        private String name;
    }
}

interface Dictionary {
    /*
     * verifica se il dizionario contiene almeno una coppia chiave/valore
     */
    boolean isEmpty();

    /*
     * svuota il dizionario
     */
    void makeEmpty();

    /*
     * Inserisce un elemento nel dizionario. L'inserimento va sempre a buon fine.
     * Se la chiave non esiste la coppia key/value viene aggiunta al dizionario;
     * se la chiave esiste gia', il valore ad essa associato viene sovrascritto
     * col nuovo valore; se key e` null viene lanciata IllegalArgumentException
     */
    void insert(Comparable key, Object value);

    /*
     * Rimuove dal dizionario l'elemento specificato dalla chiave key
     * Se la chiave non esiste viene lanciata DictionaryItemNotFoundException
     */
    void remove(Comparable key);

    /*
     * Cerca nel dizionario l'elemento specificato dalla chiave key
     * La ricerca per chiave restituisce soltanto il valore ad essa associato
     * Se la chiave non esiste viene lanciata DictionaryItemNotFoundException
     */
    Object find(Comparable key);
}

class DictionaryItemNotFoundException extends RuntimeException {
}
