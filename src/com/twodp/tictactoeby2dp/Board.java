package com.twodp.tictactoeby2dp;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

public class Board {
	
	private Player one;
	private Player two;
	private Holder[] places;
	private final int blankBox;
	private final int xBox;
	private final int oBox;
	private int numMoves;
	private int draws;
	private int turn;
	private int showAd;
	private int theWinner;
	public MainActivity main;
	private String gameWinner; 
	private ImageView[] boxes;
	private boolean xStarts;
	
	
	public Board(MainActivity main){
		this.one = new Player();
		this.two = new Player();
		this.places = new Holder[9];
		blankBox = 0;
		xBox = 1;
		oBox = 2;
		this.main = main;
		this.draws = 0;
		this.turn = xBox;
		this.xStarts = true;
		this.theWinner = 0;
		
		boxes = new ImageView[9];
		boxes[0] = (ImageView)main.findViewById(R.id.imageView2);
		boxes[1] = (ImageView)main.findViewById(R.id.imageView3);
		boxes[2] = (ImageView)main.findViewById(R.id.imageView4);
		boxes[3] = (ImageView)main.findViewById(R.id.imageView5);
		boxes[4] = (ImageView)main.findViewById(R.id.imageView6);
		boxes[5] = (ImageView)main.findViewById(R.id.imageView7);
		boxes[6] = (ImageView)main.findViewById(R.id.imageView8);
		boxes[7] = (ImageView)main.findViewById(R.id.imageView9);
		boxes[8] = (ImageView)main.findViewById(R.id.imageView10);
	}
	
	public ImageView[] getBoxes(){
		return boxes;
	}
	
	public void newGame(){
		//1D = (y * 3) + x
		//2D = index/3 = y remainder x
		for(int x = 0; x < 3; x++){
			for(int y = 0; y < 3; y++){
				Point position = new Point(x, y);
				places[(y * 3) + x] = new Holder(position, blankBox); 
			}
		}
		if(xStarts == true){
			turn = xBox;
		}
		else{
			turn = oBox;
		}
		xStarts = !xStarts;
		
		this.numMoves = 0;
		
		if(AI.getUserLocations() != null){
			AI.getUserLocations().clear();
		}
		
		if(AI.getAiLocations() != null){
			AI.getAiLocations().clear();
		}
		
		for(ImageView image: this.boxes){
			image.setClickable(true);
			image.setImageResource(android.R.color.transparent);
		}
		
		if(turn == xBox){
			Toast.makeText(main, "Player 1's Turn", Toast.LENGTH_SHORT).show();
		}
		else{
			if(main.isPlayingAi()){
				Toast.makeText(main, "Computer's Turn", Toast.LENGTH_SHORT).show();
				Runnable runnable = new Runnable(){
					
					@Override
					public void run() {
						main.getAi().aiPlay();
					}
				};
				
				Handler handler = new Handler();
				handler.postDelayed(runnable, 600);
			}
			else{
				Toast.makeText(main, "Player 2's Turn", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	private int getGrid(int id){
		switch(id){
			case 2131230721:{return 0;}
			case 2131230722:{return 1;}
			case 2131230723:{return 2;}
			case 2131230724:{return 3;}
			case 2131230725:{return 4;}
			case 2131230726:{return 5;}
			case 2131230727:{return 6;}
			case 2131230728:{return 7;}
			case 2131230729:{return 8;}
			default:{return -1;}
		}
	}
	
	public void setAiLocation(Point aiPoint){
		if(AI.getAiLocations() != null){
			AI.getAiLocations().add(aiPoint);
		}
		
		int index = (aiPoint.y * 3) + aiPoint.x;
		setGridValue(index);
		this.boxes[index].setClickable(false);
	}
	
	public void setUserLocation(int id){
		int index = getGrid(id);
		
		Point userPoint = new Point();
		userPoint.x = index % 3;
		userPoint.y = (int)Math.floor(index/3);
		
		if(AI.getUserLocations() != null){
			AI.getUserLocations().add(userPoint);
		}
		
		setGridValue(index);
	}
	
	private void setGridValue(int index){
		if(this.turn == xBox){
			this.places[index].value = xBox;
			main.playx(this.boxes[index]);
			turn = oBox;
		}
		else if(this.turn == oBox){
			this.places[index].value = oBox;
			main.playo(this.boxes[index]);
			turn = xBox;
		}
		
		this.numMoves++;
		this.theWinner = this.checkWin();
		if(this.theWinner != 0){
			for(ImageView box: this.boxes){
				box.setClickable(false);
			}
			showAd++;
		}
		showWinner(this.theWinner);
	}
	
	//check for a winner, -1 for a draw, 0 to continue
	public int checkWin(){
		int winner = 0;
		
		//check horizontal boxes
		for(int y= 0; y < 3; y++){
			if((places[(y * 3) + 0].value == places[(y * 3) + 1].value) && (places[(y * 3) + 1].value == places[(y * 3) + 2].value)){
				winner = places[(y * 3) + 0].value;
				if(winner != 0){
					return winner;
				}
			}
		}
		
		//check vertical boxes
		for(int x = 0; x < 3; x++){
			if((places[x].value == places[(1 * 3) + x].value) && (places[(1 * 3) + x].value == places[(2*3) + x].value)){
				winner = places[x].value;
				if(winner != 0){
					return winner;
				}
			}
		}
		
		//check diagonal boxes
		if((places[0].value == places[4].value) && (places[4].value == places[8].value)){
			winner = places[0].value;
			if(winner != 0){
				return winner;
			}
		}
		if((places[2].value == places[4].value) && (places[4].value == places[6].value)){
			winner = places[2].value;
			if(winner != 0){
				return winner;
			}
		}
		if(this.numMoves == 9){
			return -1;
		}
		return winner;
	}
	
	public void showWinner(int winner){
		if(winner == 1){
			one.addWin();
			gameWinner = "Player 1 Won!";
		}
		else if(winner == 2){
			two.addWin();
			if(main.isPlayingAi()){
				gameWinner = "The Computer Won";
			}
			else{
				gameWinner = "Player 2 Won!";
			}
		}
		else if(winner == -1){
			this.addDraw();
			gameWinner = "It's a Draw!";
		}
		else{
			return;
		}
		displayDialogue();
	}
	
	private void addDraw() {
		this.draws++;		
	}
	
	private int getDraws(){
		return this.draws;
	}

	public void displayDialogue(){
		Runnable runnable = new Runnable(){

			@Override
			public void run() {
				showDialogue();
			}
		};
		
		Handler handler = new Handler();
		handler.postDelayed(runnable, 600);
	}
	
	public void showDialogue()
    {
		String second = "";
		if(main.isPlayingAi()){
			second = "The computer has ";
		}
		else{
			second = "Player 2 has ";
		}
        final CharSequence[] options = {"Play Again", "Exit", "Player 1 has " + one.wins()+ " Win(s)",
        		second + two.wins()+ " Win(s)", "Draws: " +getDraws()+" Draw(s)", "Show The Board"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder.setTitle(gameWinner);
        builder.setCancelable(false);

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int position)
            {
               
               if (position == 0)//play again
               {
            	   System.gc();
            	   if(showAd % 4 == 0){
            		   main.appnext.setAppID("86dc3f29-f002-445c-8f23-d6b3e57d105c");
                       main.appnext.showBubble();
            	   }
            	   newGame(); 
                   dialog.dismiss();
               }
               else if (position == 1)//Exit
               {
            	   System.gc();
            	   dialog.dismiss();
            	   main.onBackPressed();
               }
               else if (position == 5)//Show Board
               {
            	   System.gc();
            	   dialog.dismiss();
            	   Runnable runnable = new Runnable(){

           				@Override
           				public void run() {
           					showDialogue();
           				}
           			};
           		
           			Handler handler = new Handler();
           			handler.postDelayed(runnable, 2100);
               }
              
            } 
        });
        
        AlertDialog dlg = builder.create();
        dlg.show();
    }
	
}
