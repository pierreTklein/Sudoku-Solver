import java.util.ArrayList;

public class Square {
	private int final_answer;
	private boolean filled = false;
	private ArrayList<Integer> potential_answer = new ArrayList<Integer>();
	
	public int getFinalAnswer(){
		return this.final_answer;
	}
	public void setFinalAnswer(int answer){
		if(this.filled != true){
			this.final_answer = answer;
			this.potential_answer.clear();
		}
		
		
		this.filled = true;
	}
	public ArrayList<Integer> getPotentialAnswers(){
		return this.potential_answer;
	}
	public void addPotentialAnswer(int potential){
		this.potential_answer.add(potential);
	}
	public void removePotentialAnswer(int potential){
		int i = this.potential_answer.indexOf(potential);
		this.potential_answer.remove(i);
	}
	public boolean hasPotential(int potential){
		if(this.potential_answer.contains(potential)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean getStatus(){
		return this.filled;
	}
	public String toString(){
		if(this.filled == false){
			if(this.potential_answer.size() == 0){
				return " ";
			}
			else{
				return this.potential_answer.toString();
			}
		}
		else{
			return Integer.toString(final_answer);
		}
	}
	
}
