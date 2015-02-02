package com.twodp.tictactoeby2dp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Point;
import android.util.Log;

public class AI {
	private Point centerBox;
	private Holder[] allPoints;
	private Point[] cornerPoints;
	private Board gameBoard;
	private int difficulty;
	
	private final int UserWin = 10;
	private final int AiWin = 20;
	private final int LegitBox = 1;
	private final int UserBox = -1;
	private final int AiBox = -2;
	
	private static List<Point> userLocations;
	private static List<Point> aiLocations;
	
	
	public AI(Board board, int level){
		this.gameBoard = board;
		this.centerBox = new Point(1, 1);
		this.difficulty = level;
		init();
	}

	public static List<Point> getUserLocations(){
		return AI.userLocations;
	}
	
	public static List<Point> getAiLocations(){
		return AI.aiLocations;
	}
	
	public void aiPlay(){
		try {
			Thread.sleep(850);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		updateBoxes();
		
		Point[] options = new Point[allPoints.length];
		Point playPoint;
		int maxValue = allPoints[0].value;
		int numMaxes = 0;
		int index = 0;
		
		for(Holder point: allPoints){
			if(point.value > maxValue){
				maxValue = point.value;
			}
		}
		
		for(Holder point: allPoints){
			if(point.value == maxValue){
				numMaxes++;
				options[index] = point.location;
				index++;
			}
		}
		
		Random random = new Random();
		int playIndex = random.nextInt(numMaxes);
		playPoint = options[playIndex];
		
		gameBoard.setAiLocation(playPoint);
	}
	
	private void init() {
		AI.userLocations = new ArrayList<Point>();
		AI.aiLocations = new ArrayList<Point>();
		this.allPoints = new Holder[9];
		for(int x = 0; x < 3; x++){
			for(int y = 0; y < 3; y++){
				Point position = new Point(x, y);
				allPoints[(y * 3) + x] = new Holder(position, LegitBox); 
			}
		}
		
		this.cornerPoints = new Point[4];
		cornerPoints[0] = new Point(0, 0);
		cornerPoints[1] = new Point(0, 2);
		cornerPoints[2] = new Point(2, 0);
		cornerPoints[3] = new Point(2, 2);
		
		for(Holder point: allPoints){
			if(isCorner(point.location)){
				point.value++;
			}
		}
	}
	
	private void updateBoxes(){
		for(Holder box: allPoints){
			box.value = LegitBox; //set all points to one
		}
		
		for(Point userBox: AI.userLocations){
			allPoints[(userBox.y * 3) + userBox.x].value = UserBox; //-1
		}
		
		for(Point aiBox: AI.aiLocations){
			allPoints[(aiBox.y * 3) + aiBox.x].value = AiBox; //-2
			
			Point[] adjPoints = getAdjacentPoints(aiBox);
			for(Point adjPoint: adjPoints){
				if(allPoints[(adjPoint.y * 3) + adjPoint.x].value > 0){
					if(this.difficulty ==2){
						allPoints[(adjPoint.y * 3) + adjPoint.x].value += this.difficulty;
					}
					else if(this.difficulty == 3){
						if(!isCorner(adjPoint)){
							allPoints[(adjPoint.y * 3) + adjPoint.x].value += this.difficulty;
						}
					}
					else{
						allPoints[(adjPoint.y * 3) + adjPoint.x].value++;
					}
				}
			}
		}
		
		for(Point cornerBox: cornerPoints){
			if(allPoints[(cornerBox.y * 3) + cornerBox.x].value > 0){
				allPoints[(cornerBox.y * 3) + cornerBox.x].value += this.difficulty;
			}
		}
		
		if(this.difficulty > 1){
			if(allPoints[(centerBox.y * 3) + centerBox.x].value > 0){
				allPoints[(centerBox.y * 3) + centerBox.x].value += this.difficulty;
			}
		}
		
		detectWin();
	}
	
	private boolean isCorner(Point point){
		for(Point corner: cornerPoints){
			if((corner.x == point.x) && (corner.y == point.y)){
				return true;
			}
		}
		return false;
	}
	
	private Point[] getAdjacentPoints(Point boxPoint){
		Point firstPoint = new Point(boxPoint.x, boxPoint.y);
		Point secondPoint = new Point(boxPoint.x, boxPoint.y);
		
		if(isCorner(boxPoint)){
			firstPoint.x = boxPoint.x + 1;
			secondPoint.y = boxPoint.y + 1;
			
			if(firstPoint.x > 2){
				firstPoint.x = boxPoint.x - 1;
			}
			if(secondPoint.y > 2){
				secondPoint.y = boxPoint.y - 1;
			}
		}
		else{
			if(boxPoint != centerBox){
			firstPoint.x = boxPoint.x + 1;
			secondPoint.x = boxPoint.x - 1;
				
			if((firstPoint.x > 2) || (secondPoint.x < 0)){
				firstPoint.x = boxPoint.x;
				secondPoint.x = boxPoint.x;
				firstPoint.y = boxPoint.y - 1;
				secondPoint.y = boxPoint.y + 1;
				}
			}
		}
		
		Point[] adjacentPoints = {firstPoint, secondPoint, centerBox};
		
		if(boxPoint == centerBox){
			adjacentPoints = new Point[8];
			adjacentPoints[0] = new Point(0, 0);
			adjacentPoints[1] = new Point(0, 1);
			adjacentPoints[2] = new Point(0, 2);
			adjacentPoints[3] = new Point(1, 0);
			adjacentPoints[4] = new Point(1, 2);
			adjacentPoints[5] = new Point(2, 0);
			adjacentPoints[6] = new Point(2, 1);
			adjacentPoints[7] = new Point(2, 2);
		}
		
		return adjacentPoints;
	}
	
	private void detectWin(){
		Holder[] boxValues;
		
		//These are for checking the horizontal boxes
		boxValues = new Holder[]{allPoints[0], allPoints[1], allPoints[2]};
		assignWin(boxValues, UserBox, UserWin);
		assignWin(boxValues, AiBox, AiWin);
		
		boxValues = new Holder[]{allPoints[3], allPoints[4], allPoints[5]};
		assignWin(boxValues, UserBox, UserWin);
		assignWin(boxValues, AiBox, AiWin);
		
		boxValues = new Holder[]{allPoints[6], allPoints[7], allPoints[8]};
		assignWin(boxValues, UserBox, UserWin);
		assignWin(boxValues, AiBox, AiWin);
		
		//These are for checking the vertical boxes
		boxValues = new Holder[]{allPoints[0], allPoints[3], allPoints[6]};
		assignWin(boxValues, UserBox, UserWin);
		assignWin(boxValues, AiBox, AiWin);
		
		boxValues = new Holder[]{allPoints[1], allPoints[4], allPoints[7]};
		assignWin(boxValues, UserBox, UserWin);
		assignWin(boxValues, AiBox, AiWin);
		
		boxValues = new Holder[]{allPoints[2], allPoints[5], allPoints[8]};
		assignWin(boxValues, UserBox, UserWin);
		assignWin(boxValues, AiBox, AiWin);
		
		//These are for checking the diagonal boxes
		boxValues = new Holder[]{allPoints[0], allPoints[4], allPoints[8]};
		assignWin(boxValues, UserBox, UserWin);
		assignWin(boxValues, AiBox, AiWin);
		
		boxValues = new Holder[]{allPoints[2], allPoints[4], allPoints[6]};
		assignWin(boxValues, UserBox, UserWin);
		assignWin(boxValues, AiBox, AiWin);
	}
	
	private void assignWin(Holder[] boxValues, int player, int winValue){
		int freq = 0;
		for(Holder box: boxValues){
			if(box.value == player){
				freq++;
			}
		}
		
		if(freq == 2){
			int index = 0;
			for(int i = 0; i < boxValues.length; i++){
				if(boxValues[i].value != player){
					index = i;
					break;
				}
			}
			if(boxValues[index].value > 0){
				Point location = boxValues[index].location;
				int winIndex = (location.y * 3) + location.x;
				allPoints[winIndex].value += winValue;
			}
		}
		
	}
	
}
