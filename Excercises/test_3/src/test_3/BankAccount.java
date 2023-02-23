/**
 * 
 */
package test_3;

/**
 * @author elmir
 *
 */
public class BankAccount {
	private double balance;
	private String accountHolder;
	
	/**
	 * @param accountHolder
	 */
	public BankAccount(String accountHolder) {
		super();
		this.balance = 0;
		this.accountHolder = accountHolder;
	}
	
	/**
	 * @param balance
	 * @param accountHolder
	 */
	public BankAccount(double balance, String accountHolder) {
		super();
		this.balance = balance;
		this.accountHolder = accountHolder;
	}
	
	/**
	 * @param amount
	 */
	public void deposit(double amount) {
		this.balance += amount;
	}
	
	/**
	 * @param amount
	 */
	public void withdraw(double amount) {
		this.balance -= amount;
	}
	
	/**
	 *
	 */
	public String toString() {
		return "Account holder: " + this.accountHolder + '\n' + "Balance: " + this.balance;
	}
	
	/**
	 * @return
	 */
	public double getBalance() {
		return balance;
	}
	
	/**
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	/**
	 * @return
	 */
	public String getAccountHolder() {
		return accountHolder;
	}
	
	/**
	 * @param accountHolder
	 */
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
}
