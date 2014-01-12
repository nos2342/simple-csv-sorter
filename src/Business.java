import java.math.BigDecimal;

/**
 * @author Matthew Hausman
 */
public class Business implements Comparable<Business>{
	BigDecimal[] attributes;
	int active;
	String ticker;
	
	public Business(String ticker, BigDecimal[] attributes){
		this.ticker = ticker;
		this.attributes = attributes;
		this.active = 0;
	}
	
	public String toString(){
		return this.ticker + "\t\t" + this.getActive();
	}
	
	public void setActive(int n){
		this.active = n;
	}
	
	public BigDecimal getActive(){
		return this.attributes[this.active];
	}

	@Override
  public int compareTo(Business that) {
		if(this.getActive() == null)
			return -1;
		if(that.getActive() == null)
			return 1;
		return this.getActive().compareTo(that.getActive());
  }
	
}