package i604bank;

import java.util.Random;

import i604util.List;
import i604util.Queue;

public class BankSimulation {
     
	 private static int startTime = 855;
	 private static int openTime  = 900;
	 private static int closeTime = 1500;
	 private static Clock clock   = new Clock();
	 private static Random rand = new Random() ;

	Queue<BankCustomer> tellerQueue = new Queue<>();
	Queue<BankCustomer> customerSericeQueue = new Queue<>();
	Queue<BankCustomer> driveUpQueue = new Queue<>();
	
	List<BankTeller> tellerList = new List<>();
	List<BankCustomerService> customServiceList = new List<>();
	
	
	public void RunSimulation() {
		
		Boolean stillNeedProcessing = false;
		
		tellerList.insert(new BankTeller("Betty", 100));
		tellerList.insert(new BankTeller("Bev",   100));
		tellerList.insert(new BankTeller("Chris", 150));
		tellerList.insert(new BankTeller("Larry",  75));
		// the drive-up teller's first priority is on driver-up queue, also helping out other teller if he is available
		tellerList.insert(new BankTeller("Tom",  75, "drive-up"));
		
		customServiceList.insert(new BankCustomerService("Chuck", 200));
		customServiceList.insert(new BankCustomerService("Jenny", 250));
		
		BankManager bankManager = new BankManager();
		

		clock.setTime(startTime);
		
		//  total time of the bank at operation
		while(clock.getTime() <= closeTime ||
				(clock.getTime() > closeTime && stillNeedProcessing == true)){
			
			System.out.println(clock.toString());
			
			if(clock.getTime() == openTime){ System.out.println("\tTengda's bank opens for business"); }
			if(clock.getTime() == closeTime){ System.out.println("\tTengda's Bank closes its doors to new customers"); }
			
			// customers queue up before bank
            if (clock.getTime() < closeTime){
            	
			generateQueue(generateCustomer());
            }
            
			// bank start to work
			if (clock.getTime() >= openTime){
				
				// if the clerk has unfinished work, then do it
			    stillNeedProcessing = allPeopleWork();
			   
			 
			    // serve the drive-up queue
			     bankManager.performDriveUpTeller(driveUpQueue, tellerList);
			  
			  
			  // if the teller has no work, the manager tries to assign work to him
			    tellerList.resort();
			    bankManager.performTellerAssign(tellerQueue, tellerList);

			  
			// if the customer server has no work, the manager tries to assign work to him
			    customServiceList.resort();
			    bankManager.performCustomerServiceAssign(customerSericeQueue, customServiceList);
			    	
			    	 // assign the customer server to help out the teller queue
			    bankManager.helpOutTellerQueue(customerSericeQueue, tellerQueue, customServiceList);
			     
			     if(bankManager.someTellerAtLunch == false)
			     bankManager.assignTellerToLunch(clock.getTime(), tellerList);
			     
			     // check if some teller should get back from lunch
			     bankManager.assignTellerBackFromLunch(clock.getTime(), tellerList);
			     
			     
			     if(bankManager.someServerAtLunch == false)
			     bankManager.assignCustomeServerToLunch(clock.getTime(), customServiceList);
			     
			     // check if some customer server should get back from lunch
			     bankManager.assignServerBackFromLunch(clock.getTime(), customServiceList);
			     
			}

			clock.incrementTime();
		}
         System.out.println("\tTengda's Bank processing has completed for the day - simulation over");
	}
	
	public int generateCustomer(){
		
		int prob = rand.nextInt(100);
		// test if there are people come to bank
		boolean test;
		int num = 0;
		
		if(startTime <= clock.getTime() && clock.getTime() < 1145){
		    test = ( prob < 20 );
		}
		else if (1145 <= clock.getTime() && clock.getTime() < 1315){
			test = ( prob < 40 );
		}
		else if (1315 <= clock.getTime() && clock.getTime() < 1430){
			test = ( prob < 15 );
		}
		else if (1430 <= clock.getTime() && clock.getTime() < 1445){
			test = ( prob < 35 );
		}
		else if (1445 <= clock.getTime() && clock.getTime() < closeTime){
			test = ( prob < 50 );
		}
		else{
			test = false;
		}
		
		if(test == true){
			// check how many customers are generated
			int prob2 = rand.nextInt(100);
			if(prob2 < 25){
				num = 1;
			}
			else if (75 <= prob2){
				num = 3;
			}
			else{
				num = 2;
			}
		}
		else{
			num = 0;
		}
		
		return num;
	}
    
	public void generateQueue(int n){
		if(n > 0){
			// each customer decides whether the teller queue or the customer service queue to go
			for(int i = 1; i <= n; i++){
				
				int prob = rand.nextInt(100);
				
				if (prob < 15){
					BankCustomer temp = new BankCustomer(7500);
					customerSericeQueue.enqueue(temp);
					System.out.printf("\tCustomer #%3d entered the customer service queue.%n", temp.getCustomerNum());
				}
				else{
					BankCustomer temp = new BankCustomer(1000);
					tellerQueue.enqueue(temp);
					System.out.printf("\tCustomer #%3d entered the teller queue.%n", temp.getCustomerNum());
				}
			}
		}
		else{
			// suppose there will be a 10% chance that a driver-up client comes, 
		    // if there is no ordinary client comes to the bank.
			int prob = rand.nextInt(100);
			if (prob < 10){
			BankCustomer temp = new BankCustomer(100);
			driveUpQueue.enqueue(temp);
			System.out.printf("\tCustomer #%3d entered the drive-up queue.%n", temp.getCustomerNum());
			}
		}
	}
	
	public boolean allPeopleWork(){
		
		boolean retCode = false;
		
		 for(int i = 0 ; i < tellerList.length() ; i++ ){
			 
			 // check if the teller has unfinished work
			if(tellerList.getAt(i).checkAvailable() == false){
				BankTeller teller = tellerList.getAt(i);
				
				if(teller.getAtLunch() == false)
				teller.serveCustomer();
				
				// check whether the teller finishes his work
				if(teller.checkAvailable() == false){
					retCode = true;
				}
				
			}
		 }
		 for(int i = 0 ; i < customServiceList.length() ; i++ ){
			 
			 // check if the customer server has unfinished work
			if(customServiceList.getAt(i).checkAvailable() == false){
				BankCustomerService customerServer = customServiceList.getAt(i);
				
				if(customerServer.getAtLunch() == false)
				customerServer.serveCustomer();
				
				// check whether the server finishes his work
				if(customerServer.checkAvailable() == false){
					retCode = true;
				}
				
			}
		 }
		 // true if some teller or server still need to work next time
		 return retCode;
		
	}
	
}
