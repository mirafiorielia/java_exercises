//Cinzia Pizzi

import java.io.*;
import java.util.*;

//Gestisce la lettura di nomi di pizza con piu' parole. La soluzione proposta qui
//e' stata la piu' gettonata da chi ha provato a risolvere questo problema.
//L'utilizzo di eccezioni per gestire il flusso del progamma (control-flow)
//funziona (almeno in Java) ma non e' molto elegante. Ho comunque considerato
//corrette le soluzioni proposte in questo modo.

public class PizzeriaTester2{

	public static void main(String[] args){
	
		//creo un oggetto PizzaMap
		PizzaMap spm = new PizzaMap();
		
		//gestisco la lettura con un try-with-resources, cosi' non ho il problema
		//della chiusura dei flussi
		try(FileReader reader = new FileReader("pizzeseparate.txt"); Scanner filescanner = new Scanner(reader)){		
			
			//finche' nel file ci sono righe da leggere
			while(filescanner.hasNextLine()){
				
				//leggo la prima riga e apro uno scanner su di essa
				String line = filescanner.nextLine();
				Scanner linescanner = new Scanner(line);
				
				//leggo i dati dell'associazione, il nome della pizza e il prezzo associato
				//se non c'e' almeno una parola lancera' noSuchElementException che gestisco dopo
				String pizza = linescanner.next(); 
				Double prezzo = 0.0;
				
				//finche' ho parole nella riga che sto analizzando
				while(linescanner.hasNext()){
					//se riesco a leggere un double significa che sono
					//arrivata al prezzo, altrimenti concateno al nome della pizza
					try{
						prezzo = linescanner.nextDouble();
					}
					catch(InputMismatchException e){
						pizza = pizza+" "+linescanner.next();
					}
				}				
				spm.put(pizza,prezzo);
			}			
			
			System.out.println("Contenuto della mappa");
			System.out.println(spm);
			
			System.out.println("Menu' ordinato per prezzo");
			System.out.println(spm.printMenu());
			
			//calcolo lo scontrino
			double tot = 0;			
			tot += (Double)spm.get("Margherita");
			tot += (Double)spm.get("Bufala");
			tot += (2*(Double)spm.get("Prosciutto"));			
			
			//per stampare con 2 cifre decimali posso usare la formattazione di printf
			System.out.printf("Prezzo per una margherita, una bufala e due pizze al prosciutto: %3.2f \n",tot);
		}
		catch(IOException e){
			System.out.println("Problema con apertura/chiusura del file pizze "+e);
		}
		catch(NoSuchElementException e){//importantissima perche' se il file non e' formattato bene i next() la lanciano
			System.out.println("Problema con la formattazione del file pizze "+e);
		}
		catch(NumberFormatException e){
			System.out.println("Problema con la formattazione del prezzo nel file pizze "+e);
		}
		catch(Exception e){
			System.out.println("Problema: "+e);
		}		
	}
}