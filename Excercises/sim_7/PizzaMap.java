//Cinzia Pizzi

public class PizzaMap extends FixedArrayMap {

	public Object put(Object key, Object value) {

		// controllo dei pre-requisiti
		if ((key == null) || (value == null) || !(key instanceof String) || !(value instanceof Double)) {
			throw new IllegalArgumentException();
		}

		// dichiaro la variabile che conterra' il valore da restituire
		Object result = null;

		// Provo a inserire la nuova associazione utilizzando il
		// metodo della superclasse che fa tutti i controlli di cui ho bisogno.
		// Se l'array e' pieno put della superclasse lancera' FullMapException
		// che catturo e gestisco ridimensionando l'array e inserendo l'elemento
		// (se nel try viene lanciata l'eccezione l'inserimento non va a buon fine
		// e devo riperterlo nel catch dopo aver fatto spazio)
		try {
			result = super.put(key, value);
		} catch (FullMapException e) {
			p = resize(p, 2 * pSize);
			result = super.put(key, value);
		}
		return result;
	}

	// metodo per ridimensionare un array di Pair
	protected Pair[] resize(Pair[] oldv, int newsize) {
		Pair[] newV = new Pair[newsize];
		System.arraycopy(oldv, 0, newV, 0, oldv.length);
		return newV;
	}

	public String printMenu() {

		// creo una copia dell'array p per non modificarlo con l'ordinamento,
		// come richiesto. La copia avra' dimensione fisica pari a quella
		// logica di p per gestirlo come array pieno.
		Pair[] newP = new Pair[pSize];
		System.arraycopy(p, 0, newP, 0, pSize);

		// qui riporto il selection sort che ha complessita' O(n^2) in tutti i casi
		// E' concettualmente il piu' semplice da ricordare e (almeno in teoria)
		// quello che dovrebbe creare meno problemi ad essere implementato.
		for (int i = 0; i < newP.length - 1; i++) {

			// assumo il minimo sia in posizione i
			int minPriceIndex = i;
			double minPrice = (Double) (newP[i].getValue());

			// cerco nelle posizioni successive se c'e' un elemento piu' piccolo
			for (int j = i + 1; j < newP.length; j++) {
				if ((Double) (newP[j].getValue()) < minPrice) {
					minPriceIndex = j;
					minPrice = (Double) (newP[j].getValue());
				}
			}

			// se c'e' un elemento piu' piccolo in posizione diversa da i
			// scambio il contenuto delle due posizioni
			if (minPriceIndex != i) {
				Pair temp = newP[minPriceIndex];
				newP[minPriceIndex] = newP[i];
				newP[i] = temp;
			}
		}

		// devo restituire una stringa con il contenuto di newP
		// scandisco tutte le posizioni e concateno il contenuto di ciascuna
		// sfruttando il metodo toString della classe Pair
		String s = "";
		for (int i = 0; i < pSize; i++) {
			s += newP[i].toString() + "\n";
		}
		return s;
	}
}