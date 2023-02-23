import javax.xml.transform.sax.TemplatesHandler;

// nome e cognome del candidato, matricola, data, numero della postazione

public class TraduttoreTester {
    public static void main(String[] args) {
        Traduttore traduttore = new Traduttore();

        traduttore.insert("ciao", "traduttore");

    }
}

class Traduttore implements DictionaryUD {

    private final static int INIT_SIZE = 0;
    private WordPair[] wordPairs;
    private int pointer;

    public Traduttore() {
        this.wordPairs = new WordPair[INIT_SIZE];
        this.pointer = 0;
    }

    /*
     * verifica se il dizionario contiene almeno una coppia chiave/valore
     */
    public boolean isEmpty() {
        return this.pointer == 0;
    }

    /*
     * svuota il dizionario
     */
    public void makeEmpty() {
        this.pointer = 0;
    }

    /*
     * Inserisce un elemento nel dizionario. L'inserimento va sempre a buon fine.
     * Se la chiave non esiste la coppia key/value viene aggiunta al dizionario;
     * se la chiave esiste gia' il valore ad essa associato viene sovrascritto
     * con il nuovo valore; se key e` null viene lanciata IllegalArgumentException
     */
    public void insert(Comparable key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        try {
            remove(key);
        } catch (DictionaryItemNotFoundException e) {
        }

        if (this.pointer + 1 >= this.wordPairs.length) {
            resize();
        }

    }

    private void resize() {
        WordPair[] temp = new WordPair[this.wordPairs.length + 1];
        for (int index = 0; index < this.wordPairs.length; index++) {
            temp[index] = this.wordPairs[index];
        }
        this.wordPairs = temp;
    }

    private int dichotomicSearch(int start, int end, Comparable key) {
        if (isEmpty() || start > end) {
            throw new DictionaryItemNotFoundException();
        }

        int middleIndex = (start + end) / 2;
        Comparable middleKey = this.wordPairs[middleIndex].getWord();
        if (middleKey.compareTo(key) == 0) {
            return middleIndex;
        }

        if (middleKey.compareTo(key) > 0) {
            return dichotomicSearch(0, middleIndex - 1, key); // vado a sinistra
        }

        return dichotomicSearch(middleIndex + 1, end, key); // vado a destra

    }

    /*
     * Rimuove dal dizionario l'elemento specificato dalla chiave key
     * Se la chiave non esiste viene lanciata DictionaryItemNotFoundException
     */
    public void remove(Comparable key) {
        int itemIndex = dichotomicSearch(0, this.pointer, key);

        for (int index = itemIndex; index < this.pointer; index++) {
            this.wordPairs[index] = this.wordPairs[index + 1]; // trasporto il valore da eliminare alla fine
        }

        this.pointer--;
    }

    /*
     * Cerca nel dizionario l'elemento specificato dalla chiave key
     * La ricerca per chiave restituisce soltanto il valore ad essa associato
     * Se la chiave non esiste viene lanciata DictionaryItemNotFoundException
     */
    public Object find(Comparable key) {
        return null;
    }

    /*
     * Aggiorna il contenuto del dizionario (parametro implicito del metodo) con
     * il contenuto del dizionario newdict (parametro esplicito del metodo).
     * Piu' precisamente:
     * a) se newdict contiene una chiave key non presente nel dizionario, la
     * coppia corrispondente (key value) viene scritta nel dizionario
     * b) se newdict contiene una chiave key gia' presente nel dizionario, la
     * coppia (key value) presente nel dizionario viene sovrascritta da quella
     * di newdict
     */
    public void update(DictionaryUD newdict) {
    }

    public String toString() {
        String output = "";
        return output;
    } // ..... da completare .........

    // campi di esemplare ..... da completare ......

    // classe privata WordPair: non modificare!!
    private class WordPair {
        public WordPair(String word, String[] translations) {
            this.word = word;
            this.translations = translations;
        }

        public String getWord() {
            return word;
        }

        public String[] getTranslations() {
            return translations;
        }

        /*
         * Restituisce una stringa contenente
         * - la parola word
         * - un carattere di separazione ( : )
         * - gli elementi dell'array translations, separati da uno spazio
         */
        public String toString() {
            String retString = word + " :";
            for (int i = 0; i < translations.length; i++)
                retString += " " + translations[i];
            return retString;
        }

        // campi di esemplare
        private String word;
        private String[] translations;
    }
}

interface DictionaryUD // non modificare!!
{
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
     * se la chiave esiste gia' il valore ad essa associato viene sovrascritto
     * con il nuovo valore; se key e` null viene lanciata IllegalArgumentException
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

    /*
     * Aggiorna il contenuto del dizionario (parametro implicito del metodo) con
     * il contenuto del dizionario newdict (parametro esplicito del metodo).
     * Piu' precisamente:
     * a) se newdict contiene una chiave key non presente nel dizionario, la
     * coppia corrispondente (key value) viene scritta nel dizionario
     * b) se newdict contiene una chiave key gia' presente nel dizionario, la
     * coppia (key value) presente nel dizionario viene sovrascritta da quella
     * di newdict
     */
    void update(DictionaryUD newdict);
}

class DictionaryItemNotFoundException extends RuntimeException {
}
