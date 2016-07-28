public class Sudoku {
	public static void main(String[] args){		
		int[][] potentialSudoku = 
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
		for(int i = 0; i < potentialSudoku.length; i++){
			for(int j = 0; j < potentialSudoku.length; j++){
				if(potentialSudoku[i][j] < 0){
					potentialSudoku[i][j] = Math.abs(potentialSudoku[i][j]);
				}
			}
		}
		
		
		
		System.out.println(isSudoku(potentialSudoku));
	}
	
	public static boolean isSudoku(int[][]potentialSudoku){
		if(potentialSudoku.length != 9){ //checks to make sure number of rows = 9
//			System.out.println("This array is false because it doesn't have 9 rows");
			return false;
		}

		for(int i = 0; i < potentialSudoku.length; i++){
//			System.out.println(i);
			
			if(potentialSudoku[i].length != 9){ //checks to make sure each row has 9 elements
//				System.out.println("This array is false because it doesn't have 9 elements in a row");
				return false;
			}
			
			int[] sortedRow = sort(potentialSudoku[i]); 
			if(!uniqueEntries(sortedRow)){ //checks to make sure each element is unique
//				System.out.println("This array is false because not every element in the row is unique");
				return false;
			}
			
			for(int j = 1; j <= sortedRow.length; j ++){ //checks to make sure each element is between 1 and 9
				if(sortedRow[j-1] != j){
//					System.out.println("This array is false because each element is not between 1 and 9");
					return false;
				}
			}
			
			int[] sortedColumn = sort(getColumn(potentialSudoku, i));

			if(!uniqueEntries(sortedColumn)){ //checks to make sure each element is unique
//				System.out.println("This array is false because not every element in the column is unique");
				return false;
			}
			
			for(int j = 1; j <= sortedColumn.length; j ++){ //checks to make sure each element is between 1 and 9
				if(sortedColumn[j-1] != j){
//					System.out.println("This array is false because not every element in the column is between 1 and 9");
					return false;
				}
			}
		}
		
		for(int i = 0; i < potentialSudoku.length; i+=3){ //makes sure the squares are unique
			for(int j = 0; j < potentialSudoku.length; j +=3){
				int [][] littleTDArray = subGrid(potentialSudoku, i,j,3);				
				int[] littleArray = flatten(littleTDArray);
	
				int[] sorted = sort(littleArray);
				if(!uniqueEntries(sorted)){ //checks to make sure each element is unique
//					System.out.println("This array is false because not every element in the box " + i + ", "+ j + " is unique");
					return false;
				}
				for(int k = 1; k <= sorted.length; k ++){ //checks to make sure each element is between 1 and 9
					if(sorted[k-1] != k){
//						System.out.println("This array is false because not every element in the box is between 1 and 9");
						return false;
					}
				}
			}
		}
		return true;
		
	}
	
	
	public static int[] sort(int[] unSorted){
		int[]sorted = new int[unSorted.length];
		for(int i = 0; i <  unSorted.length; i++){
			sorted[i] = unSorted[i];
		}
		
		for(int i = 0; i < sorted.length; i++){
			for(int j = i+1; j < sorted.length; j++){
				if(sorted[i] > sorted[j]){
					int temp = sorted[i];
					sorted[i] = sorted[j];
					sorted[j] = temp;
				}
			}
		}
		return sorted;
	}
	
	public static boolean uniqueEntries(int[] nineNums){
		for(int i = 0; i < nineNums.length-1; i ++){
			if(nineNums[i] == nineNums[i+1]){
				return false;
			}
			else{
				continue;
			}
		}
		return true;
	}
	
	public static int[] getColumn(int[][] sudokuBoard, int j){
		int[] columnArray = new int[sudokuBoard.length];
		if( j > sudokuBoard[0].length){
			System.out.println("Your index " + j + " is out of the bounds");
			return null;
		}
		for(int i = 0; i < sudokuBoard.length; i++){
			columnArray[i] = sudokuBoard[i][j];
		}
		return columnArray;
	}
	
	public static int[] flatten(int[][] twoDimensionalArray){
		int[] oneDimensionalArray = new int[twoDimensionalArray.length*twoDimensionalArray.length];
		int k = 0;

		for(int i = 0; i < twoDimensionalArray.length; i ++){
			for(int j = 0; j < twoDimensionalArray.length; j++){
				oneDimensionalArray[k] = twoDimensionalArray[i][j];
				k++;
			}
		}
		return oneDimensionalArray;
	}
	
	public static int[][] subGrid(int[][] fullGrid, int i, int j, int m){
		int[][] completeSG = new int[m][m];
		int rememberI = i;
		int rememberJ = j;
		for(int counterOne = 0; i < (rememberI + m) ; counterOne++, i++){
			for(int counterTwo = 0; j < (rememberJ + m); counterTwo++, j++){
				completeSG[counterOne][counterTwo] = fullGrid[i][j];
			}
			j = rememberJ;
		}
		return completeSG;
	}
}
