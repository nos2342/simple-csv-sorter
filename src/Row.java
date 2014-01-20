import java.math.BigDecimal;

/**
 * @author Matthew Hausman
 */
public class Row implements Comparable<Row>{
	BigDecimal[] values;
	private int activeColumn;
	String key;
	
	public Row(String key, BigDecimal[] values){
		this.key = key;
		this.values = values;
		this.activeColumn = 0;
	}
	
	public String toString(){
		return this.key + "\t\t" + this.activeValue();
	}
	
	public void selectColumn(int n){
		this.activeColumn = n;
	}
	
	public int getActiveColumn(){ return this.activeColumn;}
	
	public BigDecimal activeValue(){
		return this.values[this.activeColumn];
	}

	@Override
  public int compareTo(Row that) {
		if(this.activeValue() == null)
			return -1;
		if(that.activeValue() == null)
			return 1;
		return this.activeValue().compareTo(that.activeValue());
  }
	
}
