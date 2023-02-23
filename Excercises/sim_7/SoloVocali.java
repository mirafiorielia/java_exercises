//Cinzia Pizzi

import java.util.*;// necessario per utilizzare la classe Scanner

//NB: il codice DEVE essere indentanto, non perche' lo richieda la
//sintassi di Java, ma per renderlo leggibile da altri programmatori 
//nel mondo del lavoro e all'esame per renderlo comprensibile a chi 
//lo deve valutare!

public class SoloVocali{
	
	public static void main(String[] args){	
		String s = "";		
		
		//apro lo scanner, chiedo all'utente di inserire una stringa
		//leggo la stringa, chiudo lo scanner (potrei gestire eventuali eccezioni, ma non era richiesto)
		Scanner scan = new Scanner(System.in); 
		System.out.println("Inserire una stringa");
		s = scan.nextLine(); // andava bene anche scan.next(), in questo caso non si potrebbero leggere stringhe con spazi interni
		scan.close();
		
		//invoco i metodi e ne stampo il contenuto. 
		System.out.println("Stringa solo con vocali: "+stampaVocali(s));
		System.out.println("Stringa solo con vocali, in ordine inverso: "+stampaVocali2(s));	
	}

	
	public static String stampaVocali(String s){
	
		//prima cosa da fare verificare che l'argomento sia un riferimento
		//valido, altrimento qualsiasi metodo invocato su di esso lancera'
		//NullPointerException
		if(s==null){
			throw new IllegalArgumentException();
		}
		
		//caso base: se la stringa e' vuota termino restituendola,
		//infatti in una stringa vuota non ci sono vocali e non devo fare niente altro
		if(s.equals("")){
			return "";
		}
		
		//se la stringa non e' vuota scompongo il problema nell'analisi
		//del primo carattere e  nella risoluzione del problema per la
		//parte restante della stringa
		char c = s.charAt(0);
		
		//se c e' una vocale, sara' compresa nella stringa restituita dal metodo
		//quindi restituisco c a cui concatenero' il risultato della chiamata ricorsiva
		//sulla stringa privata del primo carattere
		//altrimento mi limito a invocare ricorsivamente il metodo sulla parte
		//rimanente della stringa
		if (c=='a' || c=='e' || c=='i' || c=='o' || c=='u' || c=='A' || c=='E' || c=='I' || c=='O' || c=='U'){
			return c+stampaVocali(s.substring(1));
		}
		else{
			return stampaVocali(s.substring(1));
		}	
	}
	
	
	//Per il metodo che deve stampare le vocali in ordine inverso posso
	//utilizzare lo stesso approccio, ma se il primo carattere e' una vocale 
	//questa deve essere concatenata dopo l'invocazione ricorsiva.
	//In alternativa potevo considerare l'ultimo carattere c=charAt(s.length()-1)
	//e invocare ricorsivamente sulla stringa privata di questo: s.substring(0,s.length()-1);
	public static String stampaVocali2(String s){
	
		if(s==null){
			throw new IllegalArgumentException();
		}
		
		if(s.equals("")){
			return "";
		}
		
		char c = s.charAt(0);
		if (c=='a' || c=='e' || c=='i' || c=='o' || c=='u' || c=='A' || c=='E' || c=='I' || c=='O' || c=='U'){
			return stampaVocali2(s.substring(1))+c;
		}
		else{
			return stampaVocali2(s.substring(1));
		}		
	}
}