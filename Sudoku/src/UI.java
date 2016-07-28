import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class UI extends Application{

	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			Label[][] sudokuBox = new Label[9][9];
			
			File unsolved = new File("Puzzles/3_Hard/Puzzle 1/unsolved.txt");
			File solved = new File("Puzzles/3_Hard/Puzzle 1/solved.txt");

			int[][] potentialSoduku = load(unsolved,solved,9);
			  /*
					{	{-1,4,2,5,7,-6,-3,8,9},
						{-7,-9,6,-3,-8,-4,-2,-5,-1},
						{5,3,-8,9,-1,-2,6,-7,-4},
						{-9,7,-4,-2,3,-5,1,-6,-8},
						{-2,-5,-1,-6,-9,-8,-7,-4,-3},
						{-8,-6,3,-7,4,-1,-9,2,-5},
						{-4,-2,7,-1,-5,3,-8,9,6},
						{-3,-8,-9,-4,-6,-7,5,-1,-2},
						{6,1,-5,-8,2,9,4,3,-7}};
					
				/*{ 	{-5,3,4,6,7,8,9,-1,2},
						{-6,7,2,1,9,-5,3,4,8},
						{-1,9,8,3,4,2,5,6,-7},
						{-8,5,-9,-7,-6,1,4,-2,-3},
						{-4,2,6,8,-5,3,7,9,1},
						{-7,1,3,9,2,4,8,5,6},
						{-9,-6,1,5,-3,-7,2,8,-4},
						{-2,8,7,-4,-1,-9,6,3,-5},
						{3,4,5,-2,-8,-6,1,7,9}}; //*/
				/*
				  {		{7,2,-9,-3,1,-4,-5,-6,-8},
						{-3,1,-8,-5,7,-6,-2,-9,-4,},
						{6,-4,-5,-8,-9,-2,3,7,-1},
						{-5,-7,-4,2,8,-1,9,-3,-6},
						{9,3,-2,-4,-6,-5,-1,8,7},
						{-8,-6,1,-9,3,7,-4,-2,-5},
						{-4,9,6,-7,-5,-3,-8,-1,2},
						{-1,-5,3,6,-2,8,-7,4,-9},
						{-2,-8,-7,-1,4,-9,-6,5,3}
				};//*/

			String[][] ps = new String[9][9];
			for(int i = 0; i < 9;i++){
				for(int j = 0; j < 9; j++){
					sudokuBox[i][j] = new Label();
					if(potentialSoduku[i][j] < 0){
						ps[i][j] = " ";
						sudokuBox[i][j].setText("	");
					}
					else{
						ps[i][j] = Integer.toString(potentialSoduku[i][j]);
						sudokuBox[i][j].setText(" " + Integer.toString(potentialSoduku[i][j]) + " ");
					}
					
				}
			}
			Board b = new Board(ps);

			Group root = new Group();
			Scene scene = new Scene(root,500,500);//154,145);
			primaryStage.setTitle("Sudoku visualizer");
			primaryStage.setScene(scene);
						
			GridPane grid = new GridPane();
			grid.setLayoutX(10);
			grid.setLayoutY(200);

			grid.setGridLinesVisible(true);
			for(int i = 0; i < sudokuBox.length; i++){
				for(int j = 0; j < sudokuBox[i].length; j++){
					grid.add(sudokuBox[i][j],j,i);
				}
			}
			
			root.getChildren().add(grid);

		///*
			Label[][] sudokuBox2 = new Label[9][9];

			for(int i = 0; i < 9;i++){
				for(int j = 0; j < 9; j++){
					sudokuBox2[i][j] = new Label();
					if(potentialSoduku[i][j] < 0){
						sudokuBox2[i][j].setText("   ");
					}
					else{
						sudokuBox2[i][j].setText(" " + Integer.toString(potentialSoduku[i][j]) + " ");
					}
					
				}
			}

			
			GridPane original = new GridPane();
			original.setLayoutX(10);
			original.setLayoutY(10);

			original.setGridLinesVisible(true);
			for(int i = 0; i < sudokuBox2.length; i++){
				for(int j = 0; j < sudokuBox2[i].length; j++){
					original.add(sudokuBox2[i][j],j,i);
				}
			}
			root.getChildren().add(original);//*/

			Label[][] sudokuBox3 = new Label[9][9];

			for(int i = 0; i < 9;i++){
				for(int j = 0; j < 9; j++){
					sudokuBox3[i][j] = new Label();
					if(potentialSoduku[i][j] < 0){
						sudokuBox3[i][j].setText(" " + Integer.toString(Math.abs(potentialSoduku[i][j])) + " ");
					}
					else{
						sudokuBox3[i][j].setText(" " + Integer.toString(potentialSoduku[i][j]) + " ");
					}
					
				}
			}

			
			GridPane finished = new GridPane();
			finished.setLayoutX(200);
			finished.setLayoutY(10);

			finished.setGridLinesVisible(true);
			for(int i = 0; i < sudokuBox3.length; i++){
				for(int j = 0; j < sudokuBox3[i].length; j++){
					finished.add(sudokuBox3[i][j],j,i);
				}
			}
			root.getChildren().add(finished);//*/

			
			
			Image arrow = new Image("file://arrow pic.png");
			ImageView imv = new ImageView();
			imv.setImage(arrow);
			imv.setX(150);
			imv.setY(50);
			root.getChildren().add(imv);
			final Time time = new Time(System.nanoTime());
			final Time timer = new Time(0);
			
			new AnimationTimer(){
				@Override
				public void handle(long now) {
					double elapsedTime = (now-time.value)/1000000000.0;
					timer.add((long) elapsedTime);
				/*	if(timer.value == 1){
						b.checkPossibilities();
					}
					if(timer.value == 30){
						b.checkSingleOptions();
						b.fillSingleCandidate();
						timer.reset();
					}*/
					b.checkPossibilities();
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


