package com.patriciamarissa.game;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class TimerRunner {
	
	int accumulated ;
	
	public TimerRunner (int timedelay) {
		accumulated = 0 ;
		System.out.println (timedelay) ;
		Timer.schedule (new Task () { 
			@Override public void run () {
				System.out.println ("SPEEDUP") ;
				accumulated += 1 ;
				}
		} , timedelay, timedelay) ;
	}
	
	public int getSpeed () {
		return accumulated ;
	}
}
