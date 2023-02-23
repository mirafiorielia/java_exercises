/**
 * 
 */
package test_3;

import java.util.Scanner;

/**
 * @author elmir
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		System.out.println("TEST 3");
		Scanner console = new Scanner(System.in);
		
		System.out.print("Insert name: ");
		String name = console.nextLine().toLowerCase();
		
		System.out.print("Insert surname: ");
		String surname = console.nextLine().toLowerCase();
		
		System.out.print("Insert age: ");
		int age = console.nextInt();
		
		String initials = name.substring(0, 1) + surname.substring(0, 1);
		String password = initials + age;
		
		System.out.print("Your new password is " + password);
		*/
		System.out.println("TEST 4");
		Scanner console = new Scanner(System.in);
		BankAccount bankAccount1 = new BankAccount(5000, "Elia Mirafiori");
		System.out.println(bankAccount1.toString());
		bankAccount1.deposit(1000);
		bankAccount1.withdraw(200);
		System.out.println(bankAccount1.toString());
		
		String test = true ? "CIAO" : "CIAO!"; 
	}

}
