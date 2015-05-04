package i604bank;

import i604util.List;
import i604util.Queue;

public class BankManager {
	
	boolean someTellerAtLunch;
	boolean someServerAtLunch;
	final String DRIVEUP = "drive-up";
	 	 
	 public void performTellerAssign(Queue<BankCustomer> pTellerQueue, List<BankTeller> PTellerList){
		 
		 // for the fairness of all tellers, resort the Teller list in order of work amount of each
		
			     for(int i = 0 ; i < PTellerList.length() ; i++ ){
			    	 
				          // check if the teller available
				          if(PTellerList.getAt(i).checkAvailable()){
					           BankTeller teller = PTellerList.getAt(i);
					           if( pTellerQueue.isEmpty() == false){
					           BankCustomer customer = pTellerQueue.dequeue();
					           System.out.printf("\t%s moved to %s teller window.\n", customer.toString(), teller.toString());
					           teller.setCustomer(customer);
					           teller.serveCustomer();
					               if(teller.checkAvailable() == true) 
                                     performTellerAssign( pTellerQueue,  PTellerList);
					           }
					           // queue is empty
					           else{
					        	   break;
					           }
				             
			              }
		           }
			     // if true, all tellers are busy
			     if (pTellerQueue.isEmpty() == false && PTellerList.getAt(PTellerList.length() -1 ).checkAvailable() == true) { 

			    	 performTellerAssign( pTellerQueue,  PTellerList);
			     }
			     else
			    	 return;
			     
		 // if return true, means no more Teller Assignment
	 }
	 
public void performCustomerServiceAssign(Queue<BankCustomer> pcustomerServiceQueue, List<BankCustomerService> PServerList){

	// for the fairness of all customer server, resort the Customer Service list in order of work amount of each

			 for(int i = 0 ; i < PServerList.length() ; i++ ){
				 
				 // check if the server available
				if(PServerList.getAt(i).checkAvailable()){
					       BankCustomerService server = PServerList.getAt(i);
					if( pcustomerServiceQueue.isEmpty() == false ){
					       BankCustomer customer = pcustomerServiceQueue.dequeue();
					       System.out.printf("\t%s moved to %s customer service window.\n", customer.toString(), server.toString());
					       server.setCustomer(customer);
					       server.serveCustomer();
					       if(server.checkAvailable() == true) 
					    	   performCustomerServiceAssign(pcustomerServiceQueue, PServerList);
				      }

			           else{

			        	   break;
			           }
			     }
		 }
		 if (pcustomerServiceQueue.isEmpty() == false && PServerList.getAt(PServerList.length() -1 ).checkAvailable() == true) {

			 performCustomerServiceAssign(pcustomerServiceQueue, PServerList);
		 }
		 else
	    	 return;

	 }

public void assignTellerToLunch(int time, List<BankTeller> PTellerList){


	// from 10:00, if one teller is not in service, he goes to lunch, then another
	if(time >= 1000 ){
		 for(int i = 0 ; i < PTellerList.length() ; i++ ){
		if(PTellerList.getAt(i).checkAvailable() && PTellerList.getAt(i).getHadLunch() == false){
			 BankTeller teller = PTellerList.getAt(i);
				  teller.setToLunch(time);
			 System.out.printf("\t%s leaves for lunch.\n", teller.toString());
			 someTellerAtLunch = true;
			 break; 			 
		}
	 }
	}
}

public void assignCustomeServerToLunch(int time, List<BankCustomerService> PServerList){


	// from 10:00, if one customer server is not in service, he goes to lunch, then another
	if( time >= 1000 ){
		 for(int i = 0 ; i < PServerList.length() ; i++ ){
		if(PServerList.getAt(i).checkAvailable() && PServerList.getAt(i).getHadLunch() == false){
			BankCustomerService server = PServerList.getAt(i);
			 server.setToLunch(time);
			 System.out.printf("\t%s leaves for lunch.\n", server.toString());
			 someServerAtLunch = true;
			 break;
			 			 
		}
	 }
	}
}

public void assignTellerBackFromLunch(int time, List<BankTeller> PTellerList){
	 for(int i = 0 ; i < PTellerList.length() ; i++ ){
		 if(time == PTellerList.getAt(i).returnLunchTime()){
			 BankTeller teller = PTellerList.getAt(i);
			 //reset to 0 
			 teller.setToLunch(0);
			 System.out.printf("\t%s get back from lunch.\n", teller.toString());
			 someTellerAtLunch = false;
		 }
	 }
}

public void assignServerBackFromLunch(int time, List<BankCustomerService> PServerList){
	 for(int i = 0 ; i < PServerList.length() ; i++ ){
		 if(time == PServerList.getAt(i).returnLunchTime()){
			 BankCustomerService server = PServerList.getAt(i);
			 //reset to 0 
			 server.setToLunch(0);
			 System.out.printf("\t%s get back from lunch.\n", server.toString());
			 someServerAtLunch = false;
		 }
	 }
}

public void helpOutTellerQueue(Queue<BankCustomer> pcustomerServiceQueue,
		                          Queue<BankCustomer> pTellerQueue, List<BankCustomerService> PServerList){
      
	// when all tellers are busy, and no customers in service queue, and there is custom in teller queue
	if ( pcustomerServiceQueue.isEmpty() ){
		
		for(int i = 0 ; i < PServerList.length() ; i++ ){
			 // check if the server available
			if(PServerList.getAt(i).checkAvailable()){
				       BankCustomerService server = PServerList.getAt(i);
				       if(pTellerQueue.isEmpty() == false ){
				    	   BankCustomer customer = pTellerQueue.dequeue();
				       System.out.printf("\t%s switched to %s customer service window.\n", customer.toString(), server.toString());
				       server.setCustomer(customer);
				       server.serveCustomer();
				       if(server.checkAvailable() == true)  
				        helpOutTellerQueue(pcustomerServiceQueue, pTellerQueue, PServerList);
				       }	       				       
			 }
			
	     }
		if(pcustomerServiceQueue.isEmpty() && pTellerQueue.isEmpty() == false 
				&& PServerList.getAt(PServerList.length()-1).checkAvailable())
			helpOutTellerQueue(pcustomerServiceQueue, pTellerQueue, PServerList);
  }
}

public void performDriveUpTeller(Queue<BankCustomer> pDriveUpQueue, List<BankTeller> PTellerList){
	
		for(int i = 0 ; i < PTellerList.length() ; i++ ){
			if(PTellerList.getAt(i).getPosition().equals(DRIVEUP)){
				if ( PTellerList.getAt(i).checkAvailable() ){
					BankTeller dTeller = PTellerList.getAt(i);
						if(pDriveUpQueue.isEmpty() == false){
					          BankCustomer customer = pDriveUpQueue.dequeue();
				              dTeller.setCustomer(customer);
				              System.out.printf("\t%s moved to %s drive-up teller window.\n", customer.toString(), dTeller.toString());
				              dTeller.serveCustomer();
                              if(dTeller.checkAvailable()) 
                            	  performDriveUpTeller( pDriveUpQueue, PTellerList);
				         }
						// customer queue empty
				         else
				          break;
		              }	
				 break;
	            }
			
	     }
return ;

}
	 
}
