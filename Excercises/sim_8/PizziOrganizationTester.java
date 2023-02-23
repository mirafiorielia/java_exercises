//NOTA IMPORTANTE: INDENTARE IL CODICE!

import java.util.*;
import java.io.*;

public class PizziOrganizationTester {

	public static void main(String[] args) {

		System.out.println(args[0]);
		System.out.println(args[1]);
		
		// Creare un oggetto OrganizationsArraySet
		OrganizationsArraySet oas = new OrganizationsArraySet();

		// Leggere dal file org.txt i nomi delle organizzazioni e inserirli nella
		// struttura dati. Il file contiene i nomi in righe successive (NB: il nome di
		// una organizzazione può essere formato da più parole, anzi di solito lo è!).
		// Gestire tutte le possibile eccezioni
		try (FileReader reader = new FileReader("org.txt"); Scanner scan = new Scanner(reader)) {
			// NOTA molti hanno scomposto la riga in parole per poi concatenarle
			// riformando la riga di partenza...
			while (scan.hasNextLine()) {
				oas.add(scan.nextLine());
			}

		} catch (IllegalArgumentException e) {
			System.out.println("Il file non contiene solo stringhe: esco");
			System.exit(1);
		} catch (NoSuchElementException e) {
			System.out.println("Il file non e' formattato correttamente: esco");
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.out.println("File non trovato: esco");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Problema con l'IO: esco");
			System.exit(1);
		} catch (Exception e) {
			System.out.println("Problema " + e + " : esco");
			System.exit(1);
		}

		// ottengo l'array ordinato invocando il metodo sortOrganizations()
		String[] content = oas.sortOrganizations();
		// stampo un elemento alla volta
		for (int i = 0; i < content.length; i++) {
			System.out.println(content[i]);
		}

		// ottengo l'array di acronomi ordinati invocando il metodo acronyms()
		String[] acr = oas.acronyms();
		// esamino ogni elemento e ne confronto l'iniziale con 'E' o 'I'
		// io ho usato il confronto tra caratteri; andava bene anche il confronto
		// tra stringhe usando substring(0,1) e il metodo equals; non un mix dei due
		// possibili approcci
		for (int i = 0; i < acr.length; i++) {
			if (acr[i].charAt(0) == 'E' || acr[i].charAt(0) == 'I') {
				System.out.println(acr[i]);
			}
		}
	}
}