import java.util.Scanner;

// nome e cognome del candidato, matricola, data, numero della postazione

public class MultiQueueTester {
    public static void main(String[] args) {
        /*
         * System.out.println("SINGLE");
         * 
         * ArrayQueue arrayQueue = new ArrayQueue();
         * 
         * arrayQueue.enqueue("Elia Mirafiori");
         * arrayQueue.enqueue("Lisa Mirafiori");
         * arrayQueue.enqueue("Pietro Mirafiori");
         * 
         * System.out.println(arrayQueue.toString());
         * 
         * arrayQueue.dequeue();
         * 
         * System.out.println(arrayQueue.toString());
         * 
         * System.out.println(arrayQueue.getFront());
         * 
         * System.out.println("MULTI");
         * 
         * MultiQueue multiQueue = new ArrayMultiQueue(3);
         * multiQueue.add("Davide Mirafiori");
         * multiQueue.add("Laura Mirafiori");
         * multiQueue.add("Lisa Mirafiori");
         * multiQueue.add("Elia Mirafiori");
         * multiQueue.add("Pietro Mirafiori");
         * multiQueue.add("Renzo Mirafiori");
         * multiQueue.add("Angela Mirafiori");
         * multiQueue.add("Teresa Mirafiori");
         * 
         * System.out.println(multiQueue.toString());
         * 
         * multiQueue.remove(0);
         * multiQueue.remove(2);
         * 
         * System.out.println(multiQueue.toString());
         */

        if (args.length != 1 || !args[0].matches("[0-9]+") ||
                Integer.parseInt(args[0]) <= 0) {
            System.out.println("Argomento non valido");
            System.exit(1);
        }

        Scanner console = new Scanner(System.in);
        MultiQueue mQueue = new ArrayMultiQueue(Integer.parseInt(args[0]));
        while (true) {
            System.out.println("Comando? A=aggiungi, R=rimuovi, P=stampa, Q=quit");
            String cmd = console.nextLine();

            if (cmd.equalsIgnoreCase("Q")) {
                break;
            }

            if (cmd.equalsIgnoreCase("A")) {
                System.out.println("Nome persona da aggiungere");
                String name = console.nextLine().trim().toLowerCase();
                mQueue.add(name);
                System.out.println("Aggiunto " + name + " alla coda");

            } else if (cmd.equalsIgnoreCase("R")) {
                System.out.println("Coda da cui rimuovere");
                int queue = console.nextInt();
                try {
                    String name = (String) mQueue.remove(queue);
                    System.out.println("Rimosso " + name + " dalla coda");
                } catch (IllegalArgumentException e) {
                    System.out.println("Coda non valida");
                } catch (EmptyQueueException e) {
                    System.out.println("La coda è vuota");
                }

            } else if (cmd.equalsIgnoreCase("P")) {
                System.out.println("Situazione biglietteria");
                System.out.println(mQueue);

            } else {
                System.out.println("Input non valido");

            }
        }

        System.out.println("Arrivederci");
        console.close();
        System.exit(0);
    }
}

// -----------------------------------------------------------------------------

// Classe che implementa l'interfaccia Queue usando un array (array riempito
// solo in parte, oppure array riempito solo nella parte centrale, oppure
// array circolare), con o senza ridimensionamento. La classe sovrascrive
// il metodo toString
// ....... da completare ............

class ArrayQueue implements Queue {
    private static int INIT_SIZE = 10;
    private int size;
    private Object[] array;

    public ArrayQueue() {
        this.array = new Object[INIT_SIZE];
        makeEmpty();
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    // Svuota la coda
    public void makeEmpty() {
        this.size = 0;
    }

    // Restituisce il numero di elementi presenti nella coda
    public int size() {
        return this.size;
    }

    private void resize() {
        Object[] newArray = new Object[this.array.length * 2];
        for (int index = 0; index < this.size; index++) {
            newArray[index] = this.array[index];
        }
        this.array = newArray;
    }

    // Inserisce l'oggetto obj nella coda
    public void enqueue(Object obj) {
        if (isEmpty() || this.size == this.array.length) {
            resize();
        }

        this.array[this.size] = obj;
        this.size++;
    }

    // Elimina dalla coda il primo oggetto, e lo restituisce.
    // Lancia EmptyQueueException se la coda e` vuota
    public Object dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        for (int index = 0; index < this.size; index++) {
            this.array[index] = this.array[index + 1];
        }

        this.size--;
        return this.array[this.size + 1];
    }

    // Restituisce il primo oggetto della coda, senza estrarlo
    // Lancia EmptyQueueException se la coda e` vuota
    public Object getFront() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        return this.array[0];
    }

    // metodo toString ..... da completare .........
    public String toString() {
        String output = "";

        for (int index = 0; index < this.size; index++) {
            output += this.array[index] + "\n";
        }

        return output;
    }
}

// -----------------------------------------------------------------------------

// Classe che implementa l'interfaccia MultiQueue usando un array di N code.
// La classe sovrascrive il metodo toString
// ....... da completare ............

class ArrayMultiQueue implements MultiQueue {
    private int N; // size
    private ArrayQueue[] multiQueue;

    // costruttore ......da completare ......
    // crea una multicoda vuota, costituita da una sequenza di N code vuote,
    // con N > 0
    public ArrayMultiQueue(int N) {
        this.N = N;
        this.multiQueue = new ArrayQueue[this.N];
        initMultiQueue();
    }

    private void initMultiQueue() {
        for (int index = 0; index < this.N; index++) {
            this.multiQueue[index] = new ArrayQueue();
        }
    }

    // Restituisce true se la multicoda e` vuota, cioe` se tutte le N
    // code della multicoda sono vuote. Restituisce false se la multicoda
    // contiene almeno un elemento, cioe` se almeno una delle N code della
    // multicoda contiene almeno un elemento
    public boolean isEmpty() {
        for (int index = 0; index < this.N; index++) {
            if (!this.multiQueue[index].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Svuota la multicoda, cioe` svuota tutte le N code della multicoda
    public void makeEmpty() {
        for (int index = 0; index < this.N; index++) {
            this.multiQueue[index].makeEmpty();
        }
    }

    private int minQueue() {
        int minIndex = 0;
        int minSize = this.multiQueue[minIndex].size();
        for (int index = minIndex + 1; index < this.N; index++) {
            if (minSize > this.multiQueue[index].size()) {
                minIndex = index;
                minSize = this.multiQueue[index].size();
            }
        }

        return minIndex;
    }

    // Inserisce l'oggetto obj nella multicoda. Tra tutte le N code della
    // multicoda, l'oggetto viene accodato a quella che contiene il minor
    // numero di elementi. Nel caso in cui piu` code contengano un numero
    // di elementi pari al minimo, la scelta è indifferente
    public void add(Object obj) {
        this.multiQueue[minQueue()].enqueue(obj);
    }

    // Disaccoda dalla i-esima coda il suo primo elemento e lo restituisce.
    // L'indice intero i specifica quale e` la coda da cui disaccodare il
    // primo elemento. Di conseguenza i deve essere tale che 0 <= i < N.
    // Lancia EmptyQueueException se la la i-esima coda e` vuota
    public Object remove(int i) throws EmptyQueueException {
        if (i < 0 || i > this.N) {
            throw new IllegalArgumentException();
        }

        if (this.multiQueue[i].isEmpty()) {
            throw new EmptyQueueException();
        }

        return this.multiQueue[i].dequeue();
    }

    // metodo toString ..... da completare .........
    public String toString() {
        String output = "";

        for (int index = 0; index < this.N; index++) {
            output += "CODA " + index + ":\n" + this.multiQueue[index].toString() + "\n\n";
        }

        return output;
    }
}

// -----------------------------------------------------------------------------

// NON MODIFICARE!
// Interfaccia che rappresenta il tipo di dati astratto coda

interface Queue { // Restituisce true se la coda e` vuota. Restituisce false se la coda
                  // contiene almeno un elemento
    boolean isEmpty();

    // Svuota la coda
    void makeEmpty();

    // Restituisce il numero di elementi presenti nella coda
    int size();

    // Inserisce l'oggetto obj nella coda
    void enqueue(Object obj);

    // Elimina dalla coda il primo oggetto, e lo restituisce.
    // Lancia EmptyQueueException se la coda e` vuota
    Object dequeue() throws EmptyQueueException;

    // Restituisce il primo oggetto della coda, senza estrarlo
    // Lancia EmptyQueueException se la coda e` vuota
    Object getFront() throws EmptyQueueException;
}

// -----------------------------------------------------------------------------

// -----------------------------------------------------------------------------

// NON MODIFICARE!
// Interfaccia che rappresenta il tipo di dati astratto "multicoda".
// Una multicoda e` una sequenza di N code. Ciascuna delle N code e`
// identificata da un indice intero i, dove 0 <= i < N.

interface MultiQueue {
    // Restituisce true se la multicoda e` vuota, cioe` se tutte le N
    // code della multicoda sono vuote. Restituisce false se la multicoda
    // contiene almeno un elemento, cioe` se almeno una delle N code della
    // multicoda contiene almeno un elemento
    boolean isEmpty();

    // Svuota la multicoda, cioe` svuota tutte le N code della multicoda
    void makeEmpty();

    // Inserisce l'oggetto obj nella multicoda. Tra tutte le N code della
    // multicoda, l'oggetto viene accodato a quella che contiene il minor
    // numero di elementi. Nel caso in cui piu` code contengano un numero
    // di elementi pari al minimo, la scelta è indifferente
    void add(Object obj);

    // Disaccoda dalla i-esima coda il suo primo elemento e lo restituisce.
    // L'indice intero i specifica quale e` la coda da cui disaccodare il
    // primo elemento. Di conseguenza i deve essere tale che 0 <= i < N.
    // Lancia EmptyQueueException se la la i-esima coda e` vuota
    Object remove(int i) throws EmptyQueueException;
}

// NON MODIFICARE!
// Eccezione lanciata da dequeue e getFront quando la coda e` vuota

class EmptyQueueException extends RuntimeException {
}