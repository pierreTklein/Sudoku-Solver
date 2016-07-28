import java.util.ArrayList;
import java.util.Collections;

public class Tile {
	
	private Square[][] tile = new Square[3][3];
	private boolean filled = false;
	private ArrayList<Integer> neededVals = new ArrayList<Integer>(); 
	
	
	public Tile(){
		int k = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				tile[i][j] = new Square();
				k++;
				this.neededVals.add(k);
			}
		}
	}
	
	
	//i = number 1-9
	public void setSquare(int i, int answer){
		this.tile[i/3][i%3].setFinalAnswer(answer);
		int j = this.neededVals.indexOf(answer);
		this.neededVals.remove(j);
	}
	public void setSquare(int i,int j, int answer){
		this.tile[i][j].setFinalAnswer(answer);
		int k = this.neededVals.indexOf(answer);
		this.neededVals.remove(k);
		
	}

	
	//adds potential values to a column, if it can
	public void addPotentialC(int j, int potential){
		for(int i = 0; i < 3; i++){
			if(!tile[i][j].hasPotential(potential) && !tile[i][j].getStatus()){
				tile[i][j].addPotentialAnswer(potential);
			}
		}
	}
	//adds potential values to a row, if it can
	public void addPotentialR(int i, int potential){
		for(int j = 0; j < 3; j++){
			if(!tile[i][j].hasPotential(potential) && !tile[i][j].getStatus()){
				tile[i][j].addPotentialAnswer(potential);
			}
		}
	}
	//removes potential values to a column, if it can
	public void remPotentialC(int j, int potential){
		for(int i = 0; i < 3; i++){
			if(tile[i][j].hasPotential(potential) && !tile[i][j].getStatus()){
				tile[i][j].removePotentialAnswer(potential);
			}
		}
	}
	//removes potential values to a row, if it can
	public void remPotentialR(int i, int potential){
		for(int j = 0; j < 3; j++){
			if(tile[i][j].hasPotential(potential) && !tile[i][j].getStatus()){
				tile[i][j].removePotentialAnswer(potential);
			}
		}
	}

	
	public int getAdjacentPotentialsR(int potential){
		if(!neededVals.contains(potential)){
			return -1;
		}
		else{
			int rowNum = -1;
			for(int i = 0; i < tile.length; i ++){
				for(int j = 0; j < tile[i].length; j++){
					if(tile[i][j].getPotentialAnswers().contains(potential)){
						if(rowNum != -1 && rowNum != i){
							return -1;
						}
						else{
							rowNum = i;
						}
					}
				}
			}
			return rowNum;
		}
	}

	public int getAdjacentPotentialsC(int potential){
		if(!neededVals.contains(potential)){
			return -1;
		}
		else{
			int columnNum = -1;
			for(int i = 0; i < tile.length; i ++){
				for(int j = 0; j < tile[i].length; j++){
					if(tile[i][j].getPotentialAnswers().contains(potential)){
						if(columnNum != -1 && columnNum != j){
							return  -1;
						}
						else{
							columnNum = j;
						}
					}
				}
			}
			return columnNum;
		}
	}

	
	public void addPotential(int i,int j, int potential){
		this.tile[i][j].addPotentialAnswer(potential);
	}
	
	public void checkSingleOptions(){
		ArrayList<Integer> potentials = new ArrayList<Integer>();
		for(int i = 0; i < tile.length; i ++){
			for(int j = 0; j < tile[i].length; j++){
				potentials.addAll(0, tile[i][j].getPotentialAnswers());
			}
		}
		for(int i = 1; i <= 9; i++){
			int freq = Collections.frequency(potentials, (Integer)i);
			if(freq == 1){
				for(int k = 0; k < tile.length; k ++){
					for(int j = 0; j < tile.length; j++){
						if(tile[k][j].getPotentialAnswers().contains((Integer)i)){
							this.setSquare(k, j, i);
						}
					}
				}
			}
		}
	}
	
	public Square[][] getTile(){
		return this.tile;
	}
	public boolean getStatus(){
		int numFilled = 0;
		for(int i = 0; i < this.tile.length; i++){
			for(int j = 0; j < this.tile[i].length; j++){
				if(this.tile[i][j].getStatus()){
					numFilled++;
				}
			}
		}
		if(numFilled==9){
			this.filled = true;
		}
		else{
			this.filled = false;
		}
		return this.filled;
	}
	public Square[] getRow(int i){
		return this.tile[i];

	}
	public Square[] getColumn(int i){
		Square[] temp = {tile[0][i],tile[1][i],tile[2][i]};
		return temp;
	}

	
	public int[] getCoordinates(int num){
		for(int i = 0; i < tile.length; i++){
			for(int j = 0; j < tile[i].length; j++){
				if(tile[i][j].getStatus()){
					if(tile[i][j].getFinalAnswer() == num){
						int[] coord = {i,j};
						return coord;
					}
				}
			}
		}
		int[] blank = {-1,-1};
		return blank;
	}
	
	public void fillSingleCandidate(){
		ArrayList<Integer> filled = new ArrayList<Integer>();
		for(int i = 0; i < tile.length; i++){
			for(int j = 0; j < tile[i].length; j++){
				if(tile[i][j].getPotentialAnswers().size() == 1){
					int answer = tile[i][j].getPotentialAnswers().get(0);
					filled.add(answer);
					this.setSquare(i, j, answer);
				}
			}
		}
		for(int i = 0; i < tile.length; i++){
			for(int j = 0; j < tile[i].length; j++){
				if(tile[i][j].getPotentialAnswers().size() > 0){
					for(int k = 0; k < filled.size(); k++){
						if(tile[i][j].getPotentialAnswers().contains(filled.get(k))){
							tile[i][j].removePotentialAnswer(filled.get(k));
						}
					}
				}
			}
		}
		

	}

	public ArrayList<Integer> getNeededVal(){
		return this.neededVals;
	}

	public String toString(){
		String r = "";
		for(int i = 0; i < this.tile.length; i++){
			r+="|";
			for(int j = 0; j < this.tile[i].length;j++){
				r += this.tile[i][j].toString() + " | ";
			}
			r+= '\n';
		}
		r+= '\n';
		return r;
	}
	

	
}
