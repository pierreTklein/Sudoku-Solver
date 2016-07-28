import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class UI extends Application{

	@Override
	public void start(Stage primaryStage) {
		Time tick = new Time(0);
		try {
			//create environment:
			Group root = new Group();
			Group text = new Group();
			Scene scene = new Scene(root,700,500);
			primaryStage.setTitle("Sudoku visualizer");
			primaryStage.setScene(scene);

			//find files for unsolved and solved states:
			File unsolved = new File("Puzzles/2_Medium/Puzzle 1/unsolved.txt");
			File solved = new File("Puzzles/2_Medium/Puzzle 1/solved.txt");
			
			//load sudoku puzzle into 2D array:
			int[][] potentialSudoku = load(unsolved,solved,9);

			//create board object:
			Board b = new Board(potentialSudoku);
			
			//load current state:
			Label[][] sudokuBox = this.getSudokuBox(1, 9, potentialSudoku);
			Label cSLabel = new Label("Current State:");
			cSLabel.setLayoutX(10);
			cSLabel.setLayoutY(190);
			text.getChildren().add(cSLabel);
			GridPane currentState = this.getGridPane(10, 210, sudokuBox);
			root.getChildren().add(currentState);
			
			//load original state:
			Label[][] sudokuBox2 = this.getSudokuBox(1, 9, potentialSudoku);
			Label oSLabel = new Label("Original State:");
			oSLabel.setLayoutX(10);
			oSLabel.setLayoutY(0);
			text.getChildren().add(oSLabel);
			GridPane originalState = this.getGridPane(10, 20, sudokuBox2);
			root.getChildren().add(originalState);

			//load finished state:
			Label[][] sudokuBox3 = this.getSudokuBox(0, 9, potentialSudoku);
			Label fSLabel = new Label("Finished State:");
			fSLabel.setLayoutX(200);
			fSLabel.setLayoutY(0);
			text.getChildren().add(fSLabel);			
			GridPane finishedState = this.getGridPane(200, 20, sudokuBox3);
			root.getChildren().add(finishedState);
			
			//for history:
			ArrayList<String> boardStates = new ArrayList<String>();
			boardStates.add(b.getSerialized());
			
			Label[][] sudokuBox4 = this.getSudokuBox(1, 9, potentialSudoku);
			Label hSLabel = new Label("History of moves:");
			hSLabel.setLayoutX(390);
			hSLabel.setLayoutY(0);
			text.getChildren().add(hSLabel);			
			GridPane historyState = this.getGridPane(390, 20, sudokuBox4);
			root.getChildren().add(historyState);
			
			Group buttons = new Group();
			Button back = new Button("Backward");
			back.setLayoutX(389);
			back.setLayoutY(170);

			Button forward = new Button("Forward");
			forward.setLayoutX(475);
			forward.setLayoutY(170);
			buttons.getChildren().addAll(back,forward);
			root.getChildren().add(buttons);
			
			//add text boxes:
			root.getChildren().add(text);
			
			back.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	if(tick.value-1 >= 0){
	            		tick.add(-1);
		            	String serialized = boardStates.get((int) tick.value);
						String[][] ps = deSerializeS(serialized);
						for(int i = 0; i < sudokuBox4.length; i++){
							for(int j = 0; j < sudokuBox4[i].length; j++){
								sudokuBox4[i][j].setText(" "+ ps[i][j]+ " ");
							}
						}
	            	}
	            }
	        });
			
			forward.setOnAction(new EventHandler<ActionEvent>() {
				 
	            @Override
	            public void handle(ActionEvent event) {
	            	if(tick.value+1 < boardStates.size()){
	            		tick.add(1);
		            	String serialized = boardStates.get((int) tick.value);
						String[][] ps = deSerializeS(serialized);
						for(int i = 0; i < sudokuBox4.length; i++){
							for(int j = 0; j < sudokuBox4[i].length; j++){
								sudokuBox4[i][j].setText(" "+ ps[i][j]+ " ");
							}
						}

	            	}
	            }
	        });


			
			new AnimationTimer(){
				@Override
				public void handle(long now) {

					b.checkPossibilities();
					
					int size = boardStates.size();
					String s = b.getSerialized();
					if(!boardStates.get(size-1).equals(s)){
						boardStates.add(s);
					}
					
					b.checkSingleOptions();
					b.fillSingleCandidate();

					String[][] ps = b.toArray();
					for(int i = 0; i < sudokuBox.length; i++){
						for(int j = 0; j < sudokuBox[i].length; j++){
							sudokuBox[i][j].setText(" "+ ps[i][j]+ " ");
						}
					}
				}
			}.start();
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//NOTE: returns -1 if there is a blank value.
	public int[][] deSerializeI(String serialized){
		int[][] board = new int[9][9];
		int k = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				char c = serialized.charAt(k);
				if(c == 46){
					board[i][j] = -1;
				}
				else{
					board[i][j] =Integer.decode(String.valueOf(c));
				}
				k++;
			}
		}
		return board;

	}
	
	public String[][] deSerializeS(String serialized){
		String[][] board = new String[9][9];
		int k = 0;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				char c = serialized.charAt(k);
				if(c == 46){
					board[i][j] = " ";
					
				}
				else{
					board[i][j] =String.valueOf(c);
				}
				k++;
			}
		}
		return board;
	}
	
	
	//box type  0: filled-in state
	//box type  1: blank-space state
	public Label[][] getSudokuBox(int boxType, int dimensions, int[][] sudokuInt){
		Label[][] sudokuBox = new Label[dimensions][dimensions];
		for(int i = 0; i < sudokuBox.length;i++){
			for(int j = 0; j < sudokuBox[i].length; j++){
				sudokuBox[i][j] = new Label();
				if(sudokuInt[i][j] < 0 && boxType == 0){
					sudokuBox[i][j].setText(" " + Integer.toString(Math.abs(sudokuInt[i][j])) + " ");
				}
				else if(sudokuInt[i][j] < 0 && boxType == 1){
					sudokuBox[i][j].setText("   ");
				}
				else{
					sudokuBox[i][j].setText(" " + Integer.toString(sudokuInt[i][j]) + " ");
				}
			}
		}
		return sudokuBox;

	}
	
	public GridPane getGridPane(int x, int y, Label[][] sudoku){
		GridPane grid = new GridPane();
		grid.setLayoutX(x);
		grid.setLayoutY(y);

		grid.setGridLinesVisible(true);
		for(int i = 0; i < sudoku.length; i++){
			for(int j = 0; j < sudoku[i].length; j++){
				grid.add(sudoku[i][j],j,i);
			}
		}
		return grid;
	}
	
	public int[][] load(File unsolved, File solved, int dimensions) throws IOException{
		BufferedReader inSolved = new BufferedReader(new FileReader(solved));
		BufferedReader inUnSolved = new BufferedReader(new FileReader(unsolved));
		int[][] board = new int[dimensions][dimensions];
		for(int i = 0; i < dimensions; i++){
			for(int j = 0; j < dimensions; j++){
				int sChar = inSolved.read();
				int uChar = inUnSolved.read();
				if(uChar == 46){
					board[i][j] = (-1 * (sChar-48));
					
				}
				else{
					board[i][j] = (sChar-48);

				}
			}
		}
		inSolved.close();
		inUnSolved.close();
		return board;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


