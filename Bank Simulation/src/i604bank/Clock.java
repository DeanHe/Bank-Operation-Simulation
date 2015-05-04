package i604bank;

public class Clock {

	public int time;

	public void setTime(int pTime){
		time = pTime;
	}
	
	public int getTime() {
		return time;
	}
	
	public void incrementTime() {
		time = time + 1;
		if (time % 100 >= 60) { time = (time/100 + 1 )*100; }  
		return; 
	}

	@Override
	public String toString(){
		return String.format("%02d:%02d", time/100, time%100);
	}
	
	
	

}
