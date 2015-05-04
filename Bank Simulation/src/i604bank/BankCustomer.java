package i604bank;

import java.util.Random;

public class BankCustomer {

	private static Random rand = new Random() ;
	private static int  serial = 1;
	
	private int customerNum;
	private int txPayload;
	
	public BankCustomer(int pMaxTxPayload){
		
		customerNum = serial ++;
		
		  txPayload = rand.nextInt(pMaxTxPayload - 1) + 1;
		  
		  return;
	}
	
	public int getCustomerNum(){
		return customerNum;
	}
	
	public int getTxPayload(){
		return txPayload;
	}
	
   @Override
	public String toString(){
		return String.format("Customer #%3d (tx payload %4d)", 
				              getCustomerNum(), getTxPayload());
	}

}
