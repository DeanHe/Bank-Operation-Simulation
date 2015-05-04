
package i604bank;


public class BankTeller implements Comparable<BankTeller>  {

	private String tellerName;
	private int    efficiencyRating;
	// more
	private int    efficiencyUsed;
	private int    totalProcessed;
	private int         lunchTime;
	private boolean      hadLunch;
	private String       position;
	// record the teller's work amount today
	private int    workAmount    ; 		
	private BankCustomer customer;

	
	public BankTeller(String pTellerName, int pEfficiencyRating){
		
		tellerName       = pTellerName;
		efficiencyRating = pEfficiencyRating;
		efficiencyUsed   = 0;
		totalProcessed   = 0;
		workAmount       = 0;
		customer         = null;
		lunchTime        = 0;
		hadLunch         = false;
		position         = "normal";
	}

// used for the drive-up position
public BankTeller(String pTellerName, int pEfficiencyRating, String Position){
		
	  tellerName       = pTellerName;
	  efficiencyRating = pEfficiencyRating;
	  efficiencyUsed   = 0;
	  totalProcessed   = 0;
	  workAmount       = 0;
	  customer         = null;
	  lunchTime        = 0;
	  hadLunch         = false;
	  position         = Position;
	}
	
	public String getTellerName () {
		return tellerName;
	}
	
	public int getEfficiencyRating () {
		return efficiencyRating;
	}
	
	public int getEfficiencyUsed () {
		return efficiencyUsed;
	}
	
	public int getTotalProcessed () {
		return totalProcessed;
	}
	
	public int getWorkAmount(){
		return workAmount;
	}
	
	public void setToLunch(int t){
		if(t >0){
			hadLunch = true;
		}
		lunchTime = t;
	}
	
	public boolean getHadLunch(){
		return hadLunch;
	}
	public int returnLunchTime(){
		
		int t = lunchTime ;
		 t = (t/100 + 1 )*100 + t % 100 ;   
		 return t;
		 
	}
	
	public boolean getAtLunch(){
		return lunchTime != 0;
	}
	
	public String getPosition () {
		return position;
	}
	
	public boolean checkAvailable () {
		return (customer == null && lunchTime == 0);
	}
	
	public void setCustomer(BankCustomer pCustomer){
		customer = pCustomer;
	}
	
	public void serveCustomer(){
		int payload = customer.getTxPayload();

		// unfinished
		if(payload > totalProcessed + efficiencyRating - efficiencyUsed){
			 
			totalProcessed +=  efficiencyRating - efficiencyUsed;
			 //reset to 0
			 efficiencyUsed = 0;
		}
		// finished
		else{
			
            System.out.printf("\t%s exited the bank.\n", customer.toString());
			workAmount += customer.getTxPayload();
			
            customer = null;			
			efficiencyUsed += payload - totalProcessed;
			// reset to 0
			totalProcessed = 0;	
		}
		return ;
	}
	
	
	
	@Override
	public int compareTo (BankTeller other){
   // sorted by the today's work amount, the teller worked least stays at the head of the list, vice versa 
		
		int retVal ;
		    retVal =     getWorkAmount() <  other.getWorkAmount() ? -1
				       : getWorkAmount() == other.getWorkAmount() ?  0
				       : 	                                         1
				       ;
	
		return retVal;
	}
	@Override
	public String toString () {
		return String.format("%s's (%3d efficiency rating)"
				              , getTellerName(), getEfficiencyRating() );
	}

}
