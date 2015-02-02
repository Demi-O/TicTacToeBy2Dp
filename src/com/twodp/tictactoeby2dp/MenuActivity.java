package com.twodp.tictactoeby2dp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.appnext.appnextsdk.Appnext;

public class MenuActivity extends Activity {
	
	Appnext appnext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		appnext = new Appnext(this);
		appnext.addMoreGamesRight("b6ca2d45-fa0b-46ab-9ae9-08b28e4a990a");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if(appnext.isBubbleVisible()){
			appnext.hideBubble();
		}
		else{
			quitDialogue();
		}
	};
	
	public void playClick(View v){
		Intent intent = new Intent(MenuActivity.this, MainActivity.class);
		intent.putExtra("faceComputer", false);
		startActivity(intent);
	}
	
	public void playComputerClick(View v){
		showDialogueForDif();
	}
	
	public void howToPlayClick(View v){
		AlertDialog.Builder playBox  = new AlertDialog.Builder(this);
		playBox.setMessage("The objective of the game is to be the first player to get " +
				"three Xs or three Os in a row, either horizontally , vertically or diagonally. " +
				"The game ends in a draw if all boxes are filled and no player has three Xs or three Os " +
				"in a row.");
		playBox.setTitle("How To Play");
		playBox.setPositiveButton("OK", null);
		playBox.setCancelable(true);
		playBox.create().show();
	}
	
	public void quitClick(View v){
		quitDialogue();
	}
	
	public void showDialogueForDif()
    {
        final CharSequence[] options = {"Easy", "Normal", "Hard", "Expert", "Cancel"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Difficulty");

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int position)
            {
            	
            	if (position == 0)//Easy
               {
            	   System.gc();
            	   Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            	   intent.putExtra("faceComputer", true);
            	   intent.putExtra("Difficulty", 0);
            	   startActivity(intent); 
                   dialog.dismiss();
               }
               else if (position == 1)//Normal
               {
            	   System.gc();
            	   Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            	   intent.putExtra("faceComputer", true);
            	   intent.putExtra("Difficulty", 1);
            	   startActivity(intent);
            	   dialog.dismiss();
               }
               else if (position == 2)//Hard
               {
            	   System.gc();
            	   Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            	   intent.putExtra("faceComputer", true);
            	   intent.putExtra("Difficulty", 2);
            	   startActivity(intent);
            	   dialog.dismiss();
               }
               else if (position == 3)//Expert
               {
            	   System.gc();
            	   Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            	   intent.putExtra("faceComputer", true);
            	   intent.putExtra("Difficulty", 3);
            	   startActivity(intent);
            	   dialog.dismiss();
               }
               else if (position == 4)//Cancel
               {
            	   System.gc();
            	   dialog.dismiss();
               }
              
            } 
        });
        
        AlertDialog dlg = builder.create();
        dlg.show();
    }
	
	public void quitDialogue()
    {
        final CharSequence[] options = {"Yes", "No"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are You Sure?");

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int position)
            {
            	
            	if (position == 0)//Yes
               {
            	   System.gc();
                   dialog.dismiss();
                   System.exit(0);
               }
               else if (position == 1)//No
               {
            	   System.gc();
            	   dialog.dismiss();
               }
            
            } 
        });
        
        AlertDialog dlg = builder.create();
        dlg.show();
    }

}
