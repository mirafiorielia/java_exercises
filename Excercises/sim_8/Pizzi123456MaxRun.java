//NOTA IMPORTANTE: INDENTARE IL CODICE!

import java.util.*;

public class Pizzi123456MaxRun {

	public static void main(String[] args) {

		// acquisisco la stringa in input
		Scanner console = new Scanner(System.in);
		System.out.println("Introdurre una stringa");
		String s = console.nextLine();

		// ottengo l'indice di inizio dela run di lunghezza massima
		// e il carattere che la contraddistingue
		int index = maxRunFrom(s);
		char c = s.charAt(index);

		// Stampo il risultato
		// Attenzione: prima si controlla che l'indice sia dentro il range
		// e poi si accede al carattere per il confronto, non viceversa
		System.out.println("La run di lunghezza massima e':");
		while (index < s.length() && s.charAt(index) == c) {
			System.out.print(c);
			index++;
		}
		System.out.println();
	}

	public static int maxRunFrom(String str) {

		// utilizzo due variabili per memorizzare la lunghezza della
		// piu' lunga run vista finora, e il suo indice di partenza
		int maxLen = 0;
		int indexMaxLen = -1;

		for (int i = 0; i < str.length(); i++) {

			// per ogni possibile posizione di partenza di una run
			// estraggo il carattere in posizione i e lo confronto
			// con i successivi fino a che o raggiungo la fine della stringa
			// oppure trovo un carattere diverso
			char ch = str.charAt(i);
			int count = 0;
			while (i + count < str.length() && str.charAt(i + count) == ch) {
				count++;
			}

			// confronto la lunghezza della run corrente con quella massima
			// trovata finora e, in caso, aggiorno la lunghezza e la posizione iniziale
			// della run di lunghezza massima
			if (count > maxLen) {
				maxLen = count;
				indexMaxLen = i;
			}
		}
		// restituisco l'indice di partenza della run di lunghezza massima
		// attenzione: era esplicitamente richiesto nel testo, non andava restituita
		// la stringa
		return indexMaxLen;

	}

}