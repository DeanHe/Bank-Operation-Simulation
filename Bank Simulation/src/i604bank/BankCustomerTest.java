package i604bank;

public class BankCustomerTest {

  public static void main(String[] args){
	  System.out.println(new BankCustomer( 1000)) ;
	  System.out.println(new BankCustomer( 2000)) ;
	  System.out.println(new BankCustomer( 3000)) ;
	  System.out.println(new BankCustomer( 4000)) ;
	  System.out.println(new BankCustomer( 5000)) ;
	  System.out.println(new BankCustomer( 6000)) ;
	  System.out.println(new BankCustomer( 7000)) ;
	  System.out.println(new BankCustomer( 8000)) ;
	  System.out.println(new BankCustomer( 9000)) ;
	  System.out.println(new BankCustomer(10000)) ;
	  
	  for (int i = 0; i < 99; i++){
		  if ( 0 == (i%3)){
			  System.out.println();
		  }
		  
		  System.out.print(new BankCustomer(10000) + "    ") ;
	  }
	  
  }
}
