public class Time {
	public long value;
	public Time(long value){
		this.value = value;
	}
	public void add(long value){
		this.value+=value;
	}
	public void reset(){
		this.value = 0;
	}
}
