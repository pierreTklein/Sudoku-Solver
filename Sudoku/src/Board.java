import java.util.ArrayList;

public class Board {
	private Tile[][] board = new Tile[3][3];
	private boolean filled = false;
	
	public Board(String[][] inputs){		
		if(inputs.length != 9){
			System.out.println("Length error 1");
		}
		for(int i = 0; i < inputs.length; i++){
			if(inputs[i].length != 9){
				System.out.println("Lenth error 2");
			}
			for(int j = 0; j < inputs[i].length; j++){
				if(i%3 == 0 && j%3 == 0){
					this.board[i/3][j/3] = new Tile();
				}
				if(!inputs[i][j].equals(" ")){
					int k = (i%3 * 3) + j % 3;
					board[i/3][j/3].setSquare(k, Integer.parseInt(inputs[i][j]));
				}
			}
		}
	}
	
	public void checkPossibilities(){
		int numFilled = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				checkOneTile(i,j);
				if(board[i][j].getStatus()){
					numFilled++;
				}
			}
		}
		if(numFilled == 9){
			this.filled = true;
		}
	}
	
	//i and j are tile coordinates
	public void checkOneTile(int i, int j){
		addPotentials(i,j);
		//check to see if there is only one spot a number can be placed inside a box.
	}
	
	
	
	public void addPotentials(int i, int j){
		
		//check columns:
		ArrayList<Integer> neededVal = board[i][j].getNeededVal();
		//adjacent tiles:
		for(int k = (i+1)%3; k!=i; k=(k+1)%3){
			for(int l = 0; l < neededVal.size(); l++){
				
				int coord = board[k][j].getCoordinates(neededVal.get(l))[1];
				if(coord != 0){
					board[i][j].addPotentialC(0, neededVal.get(l));
				}
				if(coord != 1){
					board[i][j].addPotentialC(1, neededVal.get(l));
				}
				if(coord != 2){
					board[i][j].addPotentialC(2, neededVal.get(l));
				}
			}
		}
		//check rows:
		for(int k = (j+1)%3; k!=j; k=(k+1)%3){
			for(int l = 0; l < neededVal.size(); l++){
				
				int coord = board[i][k].getCoordinates(neededVal.get(l))[0];
				if(coord != 0){
					board[i][j].addPotentialR(0, neededVal.get(l));
				}
				if(coord != 1){
					board[i][j].addPotentialR(1, neededVal.get(l));
				}
				if(coord != 2){
					board[i][j].addPotentialR(2, neededVal.get(l));
				}
			}
		}
		
		//remove impossibilities:
		// 1. check columns:
		for(int k = (i+1)%3; k!=i; k=(k+1)%3){
			for(int l = 0; l < neededVal.size(); l++){
				//y coordinate of final answer of needed value in specified tile
				int coord = board[k][j].getCoordinates(neededVal.get(l))[1];
				
				//y coordinate of eventual answer of needed value in specified tile
				int columnNum = board[k][j].getAdjacentPotentialsC(neededVal.get(l));
				if(coord == 0 || columnNum == 0){
					board[i][j].remPotentialC(0, neededVal.get(l));
				}
				if(coord == 1 || columnNum == 1){
					board[i][j].remPotentialC(1, neededVal.get(l));
				}
				if(coord == 2 || columnNum == 2){
					board[i][j].remPotentialC(2, neededVal.get(l));
				}
			}
		}
		// 2. check rows:
		for(int k = (j+1)%3; k!=j; k=(k+1)%3){
			for(int l = 0; l < neededVal.size(); l++){
				//x coordinate of final answer of needed value in specified tile
				int coord = board[i][k].getCoordinates(neededVal.get(l))[0];
				//y coordinate of eventual answer of needed value in specified tile
				int rowNum = board[i][k].getAdjacentPotentialsR(neededVal.get(l));

				if(coord == 0 || rowNum == 0){
					board[i][j].remPotentialR(0, neededVal.get(l));
				}
				if(coord == 1 || rowNum == 1){
					board[i][j].remPotentialR(1, neededVal.get(l));
				}
				if(coord == 2 || rowNum == 2){
					board[i][j].remPotentialR(2, neededVal.get(l));
				}
				
			}
		}

	}
	
	public void checkSingleOptions(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				board[i][j].checkSingleOptions();
			}
		}

	}
	public void fillSingleCandidate(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				board[i][j].fillSingleCandidate();
			}
		}
	}
	
	public boolean getStatus(){
		return this.filled;
	}
	public String[][] toArray(){
		String[][] a = new String[9][9];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				for(int k = 0; k < board[i][j].getTile().length; k++){
					for(int l = 0; l < board[i][j].getTile()[k].length; l++){
						a[k+(i*3)][l+(j*3)] = board[i][j].getTile()[k][l].toString();
					}
				}
			}
		}
		return a;
	}
	
	
	public String toString(){
		String s = "";
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				s+=board[i][j].toString();
			}
		}
		s+= "------";
		return s;
	}
	

}
