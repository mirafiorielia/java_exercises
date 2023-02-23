//NOTA IMPORTANTE: INDENTARE IL CODICE!

import java.util.*;

public class OrganizationsArraySet extends ArraySet {

	// per sovrascrivere il metodo devo lasciare inalterata la firma
	// se dichiaro public void add(String x) non sovrascrivo il metodo
	// e quando passo un oggetto che non e' una stringa viene invocato
	// il metodo della superclasse, che non ha controlli sul tipo
	public void add(Object x) {
		if (x == null || !(x instanceof String)) {
			throw new IllegalArgumentException();
		}
		// sfrutto l'ereditarieta' e invoco il metodo della superclasse
		super.add(x);
	}

	public String[] sortOrganizations() {
		// creo un array di stringhe che possa contenere tutti gli elementi del set
		// copio i valori da v facendo casting su ciascuno di essi
		// String[] copyV = (String[])v; NON funziona
		String[] copyV = new String[vSize];
		for (int i = 0; i < vSize; i++) {
			copyV[i] = (String) v[i];
		}

		// implemento un algoritmo di sorting qualunque...
		// per semplicita' qua ho messo il selectionsort
		// NOTA: molti hanno fatto il mergesort ma o hanno dimenticato o sbagliato
		// la condizione di uscita, o hanno sbagliato a popolare gli array left e right
		// o hanno sbagliato qualche indice. Se non siete sicuri, e non e' richiesto
		// l'algoritmo piu' efficiente, implementatene uno piu' semplice e facile da
		// ricordare
		// NOTA: era richiesto di implementare uno degli algoritmi visti a lezione.
		// Per chi non ha seguito... Bubblesort non e' stato visto a lezione.
		// Trovate il programma sul sito del corso.
		for (int i = 0; i < vSize; i++) {
			for (int j = i + 1; j < vSize; j++) {
				String tmp;

				if (copyV[i].compareTo(copyV[j]) > 0) {
					tmp = copyV[i];
					copyV[i] = copyV[j];
					copyV[j] = tmp;
				}
			}
		}

		return copyV;

	}

	// Per ottenere gli acronimi faccio una scansione dell'array ordinato
	// che ottengo con il metodo precedente. Uso lo scanner per considerare
	// una parola alla volta, ne estraggo l'iniziale e la concateno per ottenere
	// l'acronimo
	public String[] acronyms() {
		String[] s = sortOrganizations();
		String[] acr = new String[vSize];

		for (int i = 0; i < s.length; i++) {

			Scanner words = new Scanner(s[i]);
			acr[i] = ""; // va inizializzato, altrimenti e' a null
			while (words.hasNext()) {
				// piu' di qualcuno qua non ha concatenato ma semplicemente assegnato
				// cosi' facendo si assegna a acr[i] solo l'iniziale dell'ultima parola
				acr[i] += ((words.next()).toUpperCase()).charAt(0);
			}
		}
		return acr;
	}
}