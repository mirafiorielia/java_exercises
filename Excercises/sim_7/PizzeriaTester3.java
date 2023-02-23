//Cinzia Pizzi

import java.io.*;
import java.util.*;

//Questa soluzione e' piu' "elegante" rispetto a PizzaTester2 
//nella lettura dei dati perche' non usa un'eccezione per fare
//control-flow

public class PizzeriaTester3{

	public static void main(String[] args){
	
		PizzaMap spm = new PizzaMap();
		
		try(FileReader reader = new FileReader("pizzeseparate.txt"); Scanner filescanner = new Scanner(reader)){		
			while(filescanner.hasNextLine()){
				String line = filescanner.nextLine();
				Scanner linescanner = new Scanner(line);
			
				String pizza = "";
				Double prezzo = 0.0;
				while(linescanner.hasNext() && !linescanner.hasNextDouble()){
						pizza = pizza+linescanner.next()+" ";
				}
				
				//si verifica se i dati sono formattati male e non ho come primo valore il nome della pizza
				if(pizza.length()==0){
					throw new IllegalArgumentException();
				}
				//tolgo lo spazio alla fine che ho aggiunto nel while
				//leggo il prezzo e inserisco i dati nella mappa
				pizza = pizza.substring(0,pizza.length()-1);
				prezzo = linescanner.nextDouble();
				spm.put(pizza,prezzo);
			}			
			
			System.out.println("Contenuto della mappa");
			System.out.println(spm);
			
			System.out.println("Menu' ordinato per prezzo");
			System.out.println(spm.printMenu());
			
			
			double tot = 0;
			
			tot += (Double)spm.get("Margherita");
			tot += (Double)spm.get("Bufala");
			tot += (2*(Double)spm.get("Prosciutto"));
			
			System.out.printf("Prezzo per una margherita, una bufala e due pizze al prosciutto: %.2f \n",tot);
		}
		catch(IOException e){
			System.out.println("Problema con il file pizze "+e);
		}
		catch(NoSuchElementException e){
			System.out.println("Problema con la formattazione del file pizze "+e);
		}
		catch(NumberFormatException e){
			System.out.println("Problema con la formattazione del file pizze "+e);
		}
		catch(IllegalArgumentException e){
			System.out.println("Problema con la formattazione dei dati "+e);
		}
		catch(Exception e){
			System.out.println("Problema: "+e);
		}
		
	}


}