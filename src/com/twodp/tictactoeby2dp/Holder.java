package com.twodp.tictactoeby2dp;

import android.R.string;
import android.graphics.Point;

public class Holder {
	
	public Point location; //21312307(21-29)
	public int value; //0 = blank, 1 = X, 2 = O
	
	public Holder(Point location, int value){
		this.location = location;
		this.value = value;
	}
	
	@Override
	public String toString(){
		return String.format("Holder location: (%d, %d),  Holder value: %d", location.x, location.y, value);
	}
	
}

