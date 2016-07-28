
public class Main {
	
	
	
	public static void main(String[] args){
		int[][] potentialSoduku = 
			{	{7,2,-9,-3,1,-4,-5,-6,-8},
					{-3,1,-8,-5,7,-6,-2,-9,-4,},
					{6,-4,-5,-8,-9,-2,3,7,-1},
					{-5,-7,-4,2,8,-1,9,-3,-6},
					{9,3,-2,-4,-6,-5,-1,8,7},
					{-8,-6,1,-9,3,7,-4,-2,-5},
					{-4,9,6,-7,-5,-3,-8,-1,2},
					{-1,-5,3,6,-2,8,-7,4,-9},
					{-2,-8,-7,-1,4,-9,-6,5,3}
			};
				/*
			{ 	{-5,3,4,6,7,8,9,-1,2},
				{-6,7,2,1,9,-5,3,4,8},
				{-1,9,8,3,4,2,5,6,-7},
				{-8,5,-9,-7,-6,1,4,-2,-3},
				{-4,2,6,8,-5,3,7,9,1},
				{-7,1,3,9,2,4,8,5,6},
				{9,-6,1,5,-3,-7,2,8,4},
				{2,8,7,-4,-1,-9,6,3,-5},
				{-3,4,5,-2,-8,-6,1,7,9}};*/
		String[][] ps = new String[9][9];
		for(int i = 0; i < 9;i++){
			for(int j = 0; j < 9; j++){
				if(potentialSoduku[i][j] < 0){
					ps[i][j] = " ";
				}
				else{
					ps[i][j] = Integer.toString(potentialSoduku[i][j]);
				}
			}
		}
		Board b1 = new Board(ps);
		int i = 0;
		while(!b1.getStatus()){
			b1.checkPossibilities();
			b1.fillSingleCandidate();
			if(i % 10 == 0){
				System.out.println(b1.toString());
			}
			i++;
		}
		System.out.println(b1.toString());
	}

}
