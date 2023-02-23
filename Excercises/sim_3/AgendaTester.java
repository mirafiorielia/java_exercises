import java.util.Scanner;

// nome e cognome del candidato, matricola, data, numero della postazione

public class AgendaTester {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();

        Scanner console = new Scanner(System.in);
        while (true) {
            System.out.println("Comando? I=inserisci,R=rimuovi,L=leggi,Q=quit");
            String command = console.nextLine();

            if (command.equalsIgnoreCase("Q")) {
                console.close();

                System.out.println("Leaving program");
                System.exit(0);
                return;
            }

            if (command.equalsIgnoreCase("I")) {
                System.out.println("Aggiungi impegno");
                Scanner line = new Scanner(console.nextLine());

                int key = Integer.parseInt(line.next());
                String value = "";

                while (line.hasNext()) {
                    value += line.next() + " ";
                }

                agenda.insert(key, value);
            } else if (command.equalsIgnoreCase("R")) {
                System.out.println("Rimuovo impegno: " + agenda.removeMin());

            } else if (command.equalsIgnoreCase("L")) {
                System.out.println("Impegno urgente: " + agenda.getMin());
            }

            System.out.println("Contenuto dell'agenda:");
            System.out.println(agenda.toString());
        }

        /*
         * System.out.println(agenda.toString());
         * 
         * agenda.insert(0, "Ay memo");
         * agenda.insert(0, "By memo2");
         * agenda.insert(0, "Ey memo3");
         * agenda.insert(0, "Dy memo4");
         * agenda.insert(1, "Cy memo5");
         * agenda.insert(2, "My second memo2");
         * agenda.insert(0, "My second memo4");
         * agenda.insert(0, "My second memo5");
         * agenda.insert(1, "My second memo");
         * agenda.insert(3, "My second memo3");
         * 
         * System.out.println(agenda.toString());
         * 
         * System.out.println("REMOVING: " + agenda.removeMin() + "\n");
         * System.out.println("REMOVING: " + agenda.removeMin() + "\n");
         * System.out.println("REMOVING: " + agenda.removeMin() + "\n");
         * 
         * System.out.println(agenda.toString());
         * 
         * System.out.println("INSPECTING: " + agenda.getMin() + "\n");
         * 
         * 
         * System.exit(0);
         */
    }
}

class Agenda implements PriorityQueue {
    // campi di esemplare ..... da completare ......
    // costruttori e metodi pubblici ......da completare ......
    private final static int INIT_SIZE = 0;
    private int pointer;
    private Impegno[] impegni;

    public Agenda() {
        this.impegni = new Impegno[INIT_SIZE];
        this.pointer = 0;
    }

    /*
     * svuota la coda di priorita`
     */
    public void makeEmpty() {
        this.pointer = 0;
    }

    /*
     * restituisce true se la coda e' vuota, false altrimenti
     */
    public boolean isEmpty() {
        return this.pointer == 0;
    }

    /*
     * Metodo di inserimento
     * Inserisce la coppia "chiave valore" nella coda di priorita`. Notare che
     * la coda di priorita` puo` contenere piu` coppie con la stessa chiave.
     * Questo perche` il campo chiave non serve ad identificare univocamente
     * un elemento (come nel caso di un dizionario), ma serve invece a definire
     * la priorita` di un elemento. E` ovvio che piu` elementi possono avere
     * la stessa priorita`.
     */
    public void insert(int key, Object value) {
        if (this.pointer == this.impegni.length) {
            this.impegni = resize();
        }

        this.impegni[this.pointer] = new Impegno(key, (String) value);
        this.pointer++;
        sort();
    }

    private Impegno[] resize() {
        int size = this.impegni.length + 1;

        Impegno[] newImpegni = new Impegno[size];
        for (int index = 0; index < this.impegni.length; index++) {
            newImpegni[index] = this.impegni[index];
        }

        return newImpegni;
    }

    private void sort() {
        for (int i = 0; i < this.pointer; i++) {
            for (int j = i + 1; j < this.pointer; j++) {
                Impegno tmp = new Impegno(0, "");

                if (impegni[i].getPriority() < impegni[j].getPriority()) {
                    tmp = impegni[i];
                    impegni[i] = impegni[j];
                    impegni[j] = tmp;
                }
            }
        }
    }

    /*
     * Metodo di rimozione
     * Rimuove dalla coda la coppia con chiave minima, e restituisce un
     * riferimento al suo campo value. Se sono presenti piu` coppie con chiave
     * minima, effettua la rimozione di una qualsiasi delle coppie con chiave
     * minima (ad es. la prima coppia con chiave minima che e` stata trovata)
     * Lancia EmptyQueueException se la coda di priorita` e` vuota
     */
    public Object removeMin() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        this.pointer--;
        return this.impegni[this.pointer].getMemo();
    }

    /*
     * Metodo di ispezione
     * Restituisce un riferimento al campo value della coppia con chiave minima
     * (ma *non* rimuove la coppia dalla coda). Se sono presenti piu` coppie
     * con chiave minima, restituisce il campo value di una qualsiasi delle
     * coppie con chiave minima (ad esempio la prima coppia con chiave minima
     * che e` stata trovata). Lancia EmptyQueueException se la coda e` vuota
     */
    public Object getMin() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        return this.impegni[this.pointer].getMemo();
    }

    public String toString() {
        String output = "";
        for (int index = 0; index < this.pointer; index++) {
            output += this.impegni[index].getPriority() + " " + this.impegni[index].getMemo() + "\n";
        }

        return output;
    }

    /*
     * classe privata Impegno: rappresenta gli elementi della classe Agenda ed
     * e` costituita da coppie "chiave valore" in cui il campo chiave e` di
     * tipo int e rappresenta la priorita` dell'impegno, e il campo valore e`
     * una stringa contenente un promemoria dell'impegno. Si considerano 4
     * livelli di priorita`, numerati da 0 a 3. Conseguentemente il campo
     * chiave puo` assumere valori solo in questo intervallo, dove il valore 0
     * significa "massima priorita`" e il valore 3 significa "minima priorita`"
     */
    private class Impegno // non modificare!!
    {
        public Impegno(int priority, String memo) {
            if (priority > 3 || priority < 0)
                throw new IllegalArgumentException();
            this.priority = priority;
            this.memo = memo;
        }

        // metodi (pubblici) di accesso
        public int getPriority() {
            return priority;
        }

        public Object getMemo() {
            return memo;
        }

        // metodo toString sovrascritto
        public String toString() {
            return priority + " " + memo;
        }

        // campi di esemplare (privati) della classe Impegno
        private int priority; // priorita` dell'impegno (da 0 a 3)
        private String memo; // promemoria dell'impegno
    }
}

/*
 * Interfaccia PriorityQueue (non modificare!!).
 * - Questo tipo di dato astratto definisce un contenitore di coppie
 * "chiave valore", dove il campo chiave e` un numero in formato int che
 * specifica il livello di priorita` della coppia mentre il campo valore
 * rappresenta il dato della coppia.
 * - Si assume che date due chiavi k1 e k2 tali che k1 < k2, allora k1 ha
 * priorita` piu` alta di k2.
 * - Naturalmente possono essere presenti nel contenitore coppie diverse con
 * chiavi uguali, cioe` con uguale priorita`
 */
interface PriorityQueue // non modificare!!
{ /*
   * svuota la coda di priorita`
   */
    void makeEmpty();

    /*
     * restituisce true se la coda e' vuota, false altrimenti
     */
    boolean isEmpty();

    /*
     * Metodo di inserimento
     * Inserisce la coppia "chiave valore" nella coda di priorita`. Notare che
     * la coda di priorita` puo` contenere piu` coppie con la stessa chiave.
     * Questo perche` il campo chiave non serve ad identificare univocamente
     * un elemento (come nel caso di un dizionario), ma serve invece a definire
     * la priorita` di un elemento. E` ovvio che piu` elementi possono avere
     * la stessa priorita`.
     */
    void insert(int key, Object value);

    /*
     * Metodo di rimozione
     * Rimuove dalla coda la coppia con chiave minima, e restituisce un
     * riferimento al suo campo value. Se sono presenti piu` coppie con chiave
     * minima, effettua la rimozione di una qualsiasi delle coppie con chiave
     * minima (ad es. la prima coppia con chiave minima che e` stata trovata)
     * Lancia EmptyQueueException se la coda di priorita` e` vuota
     */
    Object removeMin() throws EmptyQueueException;

    /*
     * Metodo di ispezione
     * Restituisce un riferimento al campo value della coppia con chiave minima
     * (ma *non* rimuove la coppia dalla coda). Se sono presenti piu` coppie
     * con chiave minima, restituisce il campo value di una qualsiasi delle
     * coppie con chiave minima (ad esempio la prima coppia con chiave minima
     * che e` stata trovata). Lancia EmptyQueueException se la coda e` vuota
     */
    Object getMin() throws EmptyQueueException;
}

/*
 * Eccezione lanciata dai metodi removeMin e getMin di PriorityQueue quando
 * la coda di priorita` e` vuota
 */
class EmptyQueueException extends RuntimeException {
}
