package com.twodp.tictactoeby2dp;

import com.appnext.appnextsdk.Appnext;
import com.twodp.tictactoeby2dp.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Board mGameBoard;
	private AI mAi;
	private boolean playComputer;
	private int level;
	private int aiContinue;
	Appnext appnext;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appnext = new Appnext(this);
        
        aiContinue = 0;
        mGameBoard = new Board(this);
        mGameBoard.newGame();
        
        Bundle bundle = getIntent().getExtras();
        playComputer = bundle.getBoolean("faceComputer");
        level = bundle.getInt("Difficulty");
        
        if(playComputer){
        	mAi = new AI(mGameBoard, level);
        }
    }

	public boolean isPlayingAi(){
		return playComputer;
	}
	
	public AI getAi(){
		return this.mAi;
	}
	
	public void onClick(View v) throws InterruptedException{
		v.setClickable(false);		
		mGameBoard.setUserLocation(v.getId());
		aiContinue = mGameBoard.checkWin();
		if(aiContinue == 0){
			if(playComputer){
				
				Runnable runnable = new Runnable(){
					
					@Override
					public void run() {
						mAi.aiPlay();
					}
				};
				
				Handler handler = new Handler();
				handler.postDelayed(runnable, 200);
			}
		}
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	@Override
	public void onBackPressed() {
		if(appnext.isBubbleVisible()){
			appnext.hideBubble();
		}
		else {
			super.onBackPressed();
		}
	};
	
    public void playx(View v){
    	((ImageView)v).setImageResource(R.drawable.cloudx);
    }
    
    public void playo(View v){
    	((ImageView)v).setImageResource(R.drawable.cloudo);
    }
}
